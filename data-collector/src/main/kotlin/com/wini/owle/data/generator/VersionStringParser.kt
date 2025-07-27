package com.wini.owle.data.generator

import com.wini.owle.dto.environs.PythonVersion
import com.wini.owle.dto.environs.enums.OverflowComponentType
import com.wini.owle.dto.environs.enums.PythonPhase

/**
 * Standalone parser for Python version strings (PEP 440 + year+letter).
 */
object VersionStringParser {
    fun parsePythonVersionString(
        id: ULong = 0UL,
        versionStringRaw: String
    ): PythonVersion? {
        val (coreVersion, epoch) = extractEpoch(versionStringRaw.trim().removePrefix("v"))
        return when {
            isPep440Version(coreVersion) -> parsePep440Version(id, coreVersion, epoch)
            isYearLetterVersion(coreVersion) -> parseYearLetterVersion(id, coreVersion)
            else -> null
        }
    }

    internal fun extractEpoch(version: String): Pair<String, String?> {
        val split = version.split('!')
        return when (split.size) {
            2 -> Pair(split[1], split[0])
            1 -> Pair(version, null)
            else -> Pair(version, null) // malformed, will fail later
        }
    }

    internal fun isYearLetterVersion(version: String): Boolean {
        return Regex("^(\\d{4})([a-zA-Z])$").matches(version)
    }

    internal fun isPep440Version(version: String): Boolean {
        // Accepts arbitrary number of release segments, optional pre/dev/post, dash or dot as separator
        val pep440Flexible = Regex(
            pattern = "^(\\d+(?:\\.\\d+)*)(?:(a|b|rc)(\\d+)|[-\\.](a|b|rc)(\\d+))?(?:\\.post(\\d+))?(?:\\.dev(\\d+))?$",
            options = setOf(RegexOption.IGNORE_CASE)
        )
        return pep440Flexible.matches(version)
    }

    internal fun parsePep440Version(
        id: ULong,
        versionString: String,
        epoch: String?
    ): PythonVersion? {
        // Split off pre/dev/post suffixes (allow dash or dot as separator)
        val suffixRegex = Regex("^([0-9]+(?:\\.[0-9]+)*)(?:(a|b|rc)([0-9]+)|[-\\.](a|b|rc)([0-9]+))?(?:\\.post([0-9]+))?(?:\\.dev([0-9]+))?$", RegexOption.IGNORE_CASE)
        val match = suffixRegex.matchEntire(versionString) ?: return null
        val releasePart = match.groupValues[1]
        // Pre-release can be in group 2/3 (direct), or 4/5 (with separator)
        val preLtr = match.groupValues.getOrNull(2)?.ifBlank { match.groupValues.getOrNull(4) ?: "" } ?: ""
        val preNumStr = match.groupValues.getOrNull(3)?.ifBlank { match.groupValues.getOrNull(5) ?: "" } ?: ""
        val postNumStr = match.groupValues.getOrNull(6) ?: ""
        val devNumStr = match.groupValues.getOrNull(7) ?: ""

        val releaseSegments = releasePart.split('.')
        val overflowList = mutableListOf<OverflowComponentType>()
        if (epoch != null) overflowList.add(OverflowComponentType.EPOCH)
        if (releaseSegments.size > 3) overflowList.add(OverflowComponentType.MICRO)

        fun strToULongNullable(s: String?): ULong? = if (s.isNullOrBlank()) null else runCatching { s.toULong() }.getOrNull()
        val majorNum = runCatching { releaseSegments.getOrNull(0)?.toULong() }.getOrNull() ?: return null
        val minorNum = releaseSegments.getOrNull(1)?.let { strToULongNullable(it) }
        val microNum = releaseSegments.getOrNull(2)?.let { strToULongNullable(it) }
        // Any extra release segments are overflow
        // (We could store them in VersionOverflow if needed)

        val phase = when (preLtr?.lowercase()) {
            "a" -> PythonPhase.ALPHA
            "b" -> PythonPhase.BETA
            "rc" -> PythonPhase.CANDIDATE
            "" -> PythonPhase.FINAL
            else -> PythonPhase.FINAL
        }
        val phaseNumber = strToULongNullable(preNumStr)
        val isPostRelease = postNumStr.isNotBlank()
        val postNumber = strToULongNullable(postNumStr)
        val isDevRelease = devNumStr.isNotBlank()
        val devNumber = strToULongNullable(devNumStr)
        fun <T> T?.nullIf(component: OverflowComponentType): T? = if (overflowList.contains(component)) null else this
        return PythonVersion(
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
            versionString = if (epoch != null) "$epoch!$versionString" else versionString,
            hasOverflow = overflowList.isNotEmpty(),
            overflowComponents = emptyList()
        )
    }

    internal fun parseYearLetterVersion(
        id: ULong,
        versionString: String
    ): PythonVersion? {
        val m = Regex("^(\\d{4})([a-zA-Z])$").matchEntire(versionString) ?: return null
        val overflowList = mutableListOf(OverflowComponentType.PHASE_NUMBER)
        val majorNum = runCatching { m.groupValues[1].toULong() }.getOrNull() ?: return null
        return PythonVersion(
            id = id.toLong(),
            major = majorNum,
            minor = null,
            micro = null,
            phase = PythonPhase.FINAL,
            phaseNumber = null,
            isPostRelease = false,
            postNumber = null,
            isDevRelease = false,
            devNumber = null,
            versionString = versionString,
            hasOverflow = true,
            overflowComponents = emptyList()
        )
    }
} 