package com.wini.owle.data.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.data.Constants
import com.wini.owle.data.Table
import com.wini.owle.data.ext.createNewFile
import com.wini.owle.data.ext.format
import com.wini.owle.data.ext.insertText
import com.wini.owle.data.ext.maybeComma
import com.wini.owle.data.sources.FileReader
import com.wini.owle.dto.environs.PythonVersion
import com.wini.owle.dto.environs.VersionOverflow
import com.wini.owle.dto.environs.enums.OverflowComponentType
import java.io.File
import java.io.OutputStreamWriter
import kotlin.collections.chunked

/**
 * Manages the generation of SQL for the 'python_versions' table
 */
class PythonVersions(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger
) {

    fun generateInitial(iteration: Int): Int {

        val versionStringChunkLists: List<List<String>> = readAllVersions()

        versionStringChunkLists.forEachIndexed { i, it ->
            buildVersions(iteration, it)
        }

        return iteration + versionStringChunkLists.size
    }

    fun buildVersions(iteration: Int, strings: List<String>): Int {
        val (pyVersions, overflowVersions, failures) = parseVersions(strings, 0UL, logger)
        var iterTracker = 0

        // Write failure log if any
        if (failures.isNotEmpty()) {
            codeGenerator.writeAllFailures(failures)
        }

        // Sort by id to preserve deterministic ordering
        val sortedVersions = pyVersions.sortedBy { it.id }
        val sortedOverflows = overflowVersions.sortedBy { it.id }

        // Persist python_versions in chunks
        sortedVersions.chunked(CHUNK_SIZE).forEachIndexed { phase, chunk ->
            codeGenerator.writeAllVersions(iteration, chunk, phase)
            iterTracker += 1
        }

        // Persist overflow_version_components in the same chunking strategy
        sortedOverflows.chunked(CHUNK_SIZE).forEachIndexed { phase, chunk ->
            codeGenerator.writeAllOverflows(chunk, iteration, phase)
            iterTracker += 1
        }

        return iteration + iterTracker

    }

    fun CodeGenerator.writeAllFailures(failures: Set<String>) {
        val outFile = createNewFile(Dependencies(false), Constants.OUTPUT_DIR_LOGS, "version_parse_failure_log", "txt")

        OutputStreamWriter(outFile, Charsets.UTF_8).use {
            it.write(
                buildString {
                    failures.forEach { failed -> appendLine(failed) }
                }
            )
        }
    }

    fun CodeGenerator.writeAllVersions(execIteration: Int, versions: List<PythonVersion>, phase: Int) {
        val outFile = createNewFile("python_versions", execIteration, phase)

        OutputStreamWriter(outFile, Charsets.UTF_8).use {
            it.write(
                buildString {
                    appendLine(PythonVersion::class.insertText)
                    versions.forEachIndexed { i, v ->
                        appendLine("${v.format()}${if (i == versions.size-1) "" else ","}")
                    }
                }
            )
        }
    }

    fun CodeGenerator.writeAllOverflows(overflows: List<VersionOverflow>, iteration: Int, phase: Int) {
        val outFile = createNewFile(Table.overflow_version_components, iteration, phase)

        OutputStreamWriter(outFile, Charsets.UTF_8).use {
            it.write(
                buildString {
                    appendLine(VersionOverflow::class.insertText)
                    overflows.forEachIndexed { i, overflow ->
                        appendLine(overflow.format().maybeComma(i, overflows))
                    }
                }
            )
        }
    }

    fun readAllVersions(): List<List<String>> {
        return FileReader.allVersionStrings().chunked(CHUNK_SIZE)
    }


    fun parseVersions(
        versionStrings: List<String>,
        start: ULong,
        logger: KSPLogger
    ): Triple<Set<PythonVersion>, Set<VersionOverflow>, Set<String>> {

        val successes: MutableSet<PythonVersion> = mutableSetOf()
        val overflowed: MutableSet<VersionOverflow> = mutableSetOf()
        val failures: MutableSet<String> = mutableSetOf()

        versionStrings.forEach { it ->

            val overflowedElements: List<OverflowComponentType> = triggersOverflow(it)

            if (overflowedElements.isNotEmpty()) {
                val pythonVersion = tryParsePythonVersion(
                    id = (start + successes.size.toULong()),
                    versionStringRaw = it,
                    overflows = overflowedElements
                )
                if (pythonVersion == null) {
                    // failure case: no need to parse versionOverflow even though we would have
                    failures.add(it)
                    logger.error("Python Version parse failure: $it")
                } else {
                    val versionOverflows: List<VersionOverflow> = buildVersionOverflows(
                        overflowed.size.toLong(),
                        it,
                        pythonVersion,
                        overflowedElements
                    )
                    overflowed.addAll(versionOverflows)
                    successes.add(pythonVersion)
                }
            }

            val version = tryParsePythonVersion((start + successes.size.toULong()), it, emptyList())

            if (version == null) {
                failures.add(it)
                logger.error("Python Version parse failure: $it")
            } else {
                successes.add(version)
            }
        }

        return Triple(successes, overflowed, failures)
    }

    fun tryParsePythonVersion(
        id: ULong,
        versionStringRaw: String,
        overflows: List<OverflowComponentType>
    ): PythonVersion? {
        // 0) Preprocess: strip leading 'v', trailing punctuation, and extract epoch if present
        var versionString = versionStringRaw.trim().removePrefix("v")
        var epoch: String? = null
        val overflowList = overflows.toMutableList()
        val epochSplit = versionString.split('!')
        if (epochSplit.size == 2) {
            epoch = epochSplit[0]
            versionString = epochSplit[1]
            overflowList.add(com.wini.owle.dto.environs.enums.OverflowComponentType.EPOCH)
        } else if (epochSplit.size > 2) {
            // Malformed, treat as failure
            return null
        }

        // 1) Fast-path: attempt strict PEP-440-ish regex up to 3 segments
        val regex = Regex(
            pattern = "^(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?(?:(a|b|rc)(\\d+))?(?:\\.post(\\d+))?(?:\\.dev(\\d+))?$",
            options = setOf(RegexOption.IGNORE_CASE)
        )

        var majStr: String? = null
        var minStr: String? = null
        var micStr: String? = null
        var preLtr: String? = null
        var preNumStr: String? = null
        var postNumStr: String? = null
        var devNumStr: String? = null

        val strictMatch = regex.matchEntire(versionString)

        if (strictMatch != null) {
            val groups = strictMatch.destructured
            majStr = groups.component1()
            minStr = groups.component2()
            micStr = groups.component3()
            preLtr = groups.component4()
            preNumStr = groups.component5()
            postNumStr = groups.component6()
            devNumStr = groups.component7()
        } else {
            // Only allow year+letter (e.g. 2012c, 2004a) as a special overflow case
            val yearLetterRegex = Regex("^(\\d{4})([a-zA-Z])$")
            val m = yearLetterRegex.matchEntire(versionString)
            if (m != null) {
                majStr = m.groupValues[1]
                // The letter is a revision, not a phase; treat as overflow
                (overflowList as? MutableList)?.add(OverflowComponentType.PHASE_NUMBER)
                // All other fields remain null/empty
            } else {
                // Not PEP 440, not year+letter: reject
                return null
            }
        }

        fun strToULongNullable(s: String): ULong? = if (s.isBlank()) null else runCatching { s.toULong() }.getOrNull()

        // Accept missing minor/micro segments (PEP 440 allows e.g. 3, 3.10, 3.10.1)
        val majorNum = runCatching { majStr?.toULong() }.getOrNull() ?: return null
        val minorNum = if (minStr == null || minStr.isBlank()) null else strToULongNullable(minStr)
        val microNum = if (micStr == null || micStr.isBlank()) null else strToULongNullable(micStr)

        val phase = when (preLtr?.lowercase()) {
            "a" -> com.wini.owle.dto.environs.enums.PythonPhase.ALPHA
            "b" -> com.wini.owle.dto.environs.enums.PythonPhase.BETA
            "rc" -> com.wini.owle.dto.environs.enums.PythonPhase.CANDIDATE
            "" -> com.wini.owle.dto.environs.enums.PythonPhase.FINAL
            else -> com.wini.owle.dto.environs.enums.PythonPhase.FINAL
        }

        val phaseNumber = strToULongNullable(preNumStr ?: "")

        val isPostRelease = !postNumStr.isNullOrBlank()
        val postNumber = strToULongNullable(postNumStr ?: "")

        val isDevRelease = !devNumStr.isNullOrBlank()
        val devNumber = strToULongNullable(devNumStr ?: "")

        // If any overflows present, null-out the associated fields
        fun <T> T?.nullIf(component: OverflowComponentType): T? = if (overflowList.contains(component)) null else this

        // If epoch is present, record as overflow (unless schema supports it)
        val hasEpochOverflow = epoch != null

        val pv = PythonVersion(
            id = id.toLong(),
            major = majorNum.nullIf(OverflowComponentType.MAJOR) ?: 0UL,
            minor = minorNum.nullIf(OverflowComponentType.MINOR),
            micro = microNum.nullIf(OverflowComponentType.MICRO),
            phase = phase,
            phaseNumber = phaseNumber.nullIf(OverflowComponentType.PHASE_NUMBER),
            isPostRelease = isPostRelease,
            postNumber = postNumber.nullIf(OverflowComponentType.POST_NUMBER),
            isDevRelease = isDevRelease,
            devNumber = devNumber.nullIf(OverflowComponentType.DEV_NUMBER),
            versionString = versionStringRaw,
            hasOverflow = overflowList.isNotEmpty(),
            // this is an ORM-implication of the FK relationship; it doesn't matter until populated into the db,
            // where VersionOverflow's python_version_id column is an FK relation to python_versions
            overflowComponents = emptyList()
        )

        return pv
    }

    fun triggersOverflow(versionString: String): List<OverflowComponentType> {
        val result = mutableListOf<OverflowComponentType>()

        // Detect extra release components beyond major.minor.micro
        val releaseParts = versionString.split('+', '-', limit = 2)[0].split('.')
        if (releaseParts.size > 3) {
            result.add(OverflowComponentType.MICRO)
        }

        val regexNumbers = Regex("\\d+")

        val matches = regexNumbers.findAll(versionString)

        val componentsOrder = listOf(
            OverflowComponentType.MAJOR,
            OverflowComponentType.MINOR,
            OverflowComponentType.MICRO,
            OverflowComponentType.PHASE_NUMBER,
            OverflowComponentType.POST_NUMBER,
            OverflowComponentType.DEV_NUMBER
        )

        matches.forEachIndexed { index, matchResult ->
            val numStr = matchResult.value
            if (numStr.length > Constants.BIGINT_UNSIGNED_MAX_VAL_LEN) {
                if (index < componentsOrder.size) result.add(componentsOrder[index])
            } else if (numStr.length == Constants.BIGINT_UNSIGNED_MAX_VAL_LEN) {
                // Compare to max value
                runCatching {
                    val num = numStr.toULong()
                    if (num > Constants.BIGINT_UNSIGNED_MAX_VAL) {
                        if (index < componentsOrder.size) result.add(componentsOrder[index])
                    }
                }.onFailure {
                    if (index < componentsOrder.size) result.add(componentsOrder[index])
                }
            }
        }

        return result
    }

    fun buildVersionOverflows(
        id: Long,
        versionString: String,
        pythonVersion: PythonVersion,
        overflowComponents: List<OverflowComponentType>
    ): List<VersionOverflow> {
        return overflowComponents.mapIndexed { i, component ->
            VersionOverflow(
                id = id + i,
                pythonVersion = pythonVersion,
                componentType = component,
                stringValue = versionString
            )
        }
    }

    companion object {
        const val CHUNK_SIZE = 200_000
        const val SOURCE_FILE = "resources/reference/all_dist_versions.csv"
        const val PYVER_TABLE_FMT = "(id, major, minor, micro, phase, phase_number, is_post_release, post_number, is_dev_release, dev_number, has_overflow)"
        const val OVERF_TABLE_FMT = "(id, python_version_id, component_type, string_value)"
        const val PARSE_FAILURES_LOG = ""

    }


}