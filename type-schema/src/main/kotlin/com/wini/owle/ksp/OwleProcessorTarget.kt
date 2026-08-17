package com.wini.owle.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated

/**
 * Which Gradle module a processor is currently running against.
 *
 * `type-schema` is applied as a KSP processor to more than one module, and the processors are
 * *not* interchangeable between them: generated code lands in whichever module the processor
 * ran in and can only see that module's classpath.
 *
 *  - [COMMON] — DTO-derived output (validators, model docs, API docs). Needs DTOs only.
 *  - [SERVER] — output that needs Exposed entities, Ktor routing, or the DLL, none of which
 *    `common` can see.
 *
 * Without this gate every processor would run in every module: `common`'s validators would be
 * regenerated into `server` against a classpath that resolves them differently, which surfaces
 * as a wall of unresolved references rather than a clear error. Wiring the module direction
 * wrong has already cost this project two long debugging sessions.
 */
enum class OwleTarget {
    COMMON,
    SERVER;

    companion object {
        /** Gradle: `ksp { arg("owle.target", "server") }` */
        const val OPTION = "owle.target"

        fun parse(raw: String?): OwleTarget =
            entries.firstOrNull { it.name.equals(raw?.trim(), ignoreCase = true) } ?: COMMON
    }
}

val SymbolProcessorEnvironment.owleTarget: OwleTarget
    get() = OwleTarget.parse(options[OwleTarget.OPTION])

/**
 * Returns the processor only when the current module is [target]; otherwise a processor that
 * does nothing. Cheaper and less surprising than registering different service files per module.
 */
fun SymbolProcessorEnvironment.processorFor(
    target: OwleTarget,
    create: () -> SymbolProcessor
): SymbolProcessor =
    if (owleTarget == target) create() else NoOpSymbolProcessor

object NoOpSymbolProcessor : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> = emptyList()
}
