package com.wini.owle.ksp.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger


/**
 * A named class per type at each layer — delegate, repository, service, DLL — plus the Koin
 * module that binds them.
 *
 * These exist because **Koin keys definitions by `KClass`, and Kotlin generics erase.** Binding
 * `single<OwleRepo<ULong, WorkflowEntity>>` and `single<OwleRepo<ULong, TagEntity>>` registers
 * both under `OwleRepo::class`: the second silently replaces the first, and every consumer gets
 * whichever happened to register last. Before this, asking for the Workflow repository, the
 * WorkflowFolder repository, or the BenchmarkReport repository all returned one object pointed at
 * `tagged_workflows`, and the same held for services, DLLs and API endpoints. Nothing in the
 * graph resolved to what it asked for.
 *
 * A distinct class per type is the fix, because a distinct class is the only thing Koin can tell
 * apart without stringly-typed qualifiers. The alternative — `named("Workflow")` on every
 * definition and every lookup — trades a compile-time guarantee for a runtime string.
 *
 * Nothing here has behaviour. Each class is its generic base with the type arguments pinned, so
 * the logic remains in one place and stays testable in one place.
 */
private fun String.quoted(): String = "\"" + this + "\""

class ConcreteLayerWriter(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    companion object {
        const val PACKAGE = "com.wini.owle.generated"
    }

    /** One type's coordinates, resolved by the caller. */
    data class Layer(
        val dto: String,
        val dtoFq: String,
        val entity: String,
        val entityFq: String,
        val idType: String,
        /** For composite keys, the hand-written delegate object; null when one must be generated. */
        val existingDelegateFq: String?,
        /** Route segment from `@ApiEndpointDefinition`; null when the type exposes no endpoint. */
        val endpointPath: String?,
        val validatorFq: String?
    ) {
        val delegate: String get() = existingDelegateFq?.substringAfterLast('.') ?: "${dto}DaoDelegate"
        val repo: String get() = "${dto}Repo"
        val service: String get() = "${dto}Service"
        val dll: String get() = "${dto}Dll"
        val mapper: String get() = "${dto}Mapper"
        val api: String get() = "${dto}Api"
        val validator: String get() = validatorFq?.substringAfterLast('.') ?: ""
    }

    fun generate(layers: List<Layer>) {
        val usable = layers.filter { it.idType != "CompositeID" || it.existingDelegateFq != null }
        val skipped = layers - usable.toSet()

        skipped.forEach {
            logger.warn(
                "[layers] ${it.dto}: composite-keyed with no DaoDelegate, so no service or DLL " +
                    "can be built for it. Write a CompositeIdDaoDelegate for it to join the graph."
            )
        }

        usable.forEach { writeTypeFile(it) }
        writeModule(usable)
        logger.warn("[layers] ${usable.size} types wired; ${skipped.size} skipped")
    }

    private fun writeTypeFile(l: Layer) {
        val b = StringBuilder()
        b.appendLine("package $PACKAGE").appendLine()
        b.appendLine("import ${l.dtoFq}")
        b.appendLine("import ${l.entityFq}")
        b.appendLine("import com.wini.owle.dll.GenericDllImpl")
        b.appendLine("import com.wini.owle.mapper.generated.${l.mapper}")
        b.appendLine("import com.wini.owle.repo.GenericRepoImpl")
        b.appendLine("import com.wini.owle.services.GenericServiceImpl")
        if (l.endpointPath != null && l.validatorFq != null) {
            b.appendLine("import " + l.validatorFq)
            b.appendLine(
                when (l.idType) {
                    "String" -> "import com.wini.owle.api.StringIdEndpointImpl"
                    "CompositeID" -> "import com.wini.owle.api.CompositeIdEndpointImpl"
                    else -> "import com.wini.owle.api.ULongIdEndpointImpl"
                }
            )
        }
        l.existingDelegateFq?.let { b.appendLine("import $it") }
        if (l.existingDelegateFq == null) {
            b.appendLine(
                when (l.idType) {
                    "String" -> "import com.wini.owle.entity.delegate.StringIdDaoDelegate"
                    else -> "import com.wini.owle.entity.delegate.ULongIdDaoDelegate"
                }
            )
        }
        if (l.idType == "CompositeID") {
            b.appendLine("import org.jetbrains.exposed.v1.core.dao.id.CompositeID")
        }
        b.appendLine()
        b.appendLine("/**")
        b.appendLine(" * Generated. ${l.dto}'s layer classes, each its generic base with the type")
        b.appendLine(" * arguments pinned so Koin can tell it apart from every other type's. Do not edit.")
        b.appendLine(" */")
        b.appendLine()

        if (l.existingDelegateFq == null) {
            val base = if (l.idType == "String") "StringIdDaoDelegate" else "ULongIdDaoDelegate"
            b.appendLine("class ${l.delegate} : $base<${l.dto}, ${l.entity}>(${l.entity})")
            b.appendLine()
        }

        b.appendLine("class ${l.repo} : GenericRepoImpl<${l.idType}, ${l.entity}>(${l.entity})")
        b.appendLine()
        b.appendLine("class ${l.service}(")
        b.appendLine("    repo: ${l.repo},")
        b.appendLine("    mapper: ${l.mapper},")
        b.appendLine("    daoDelegate: ${l.delegate}")
        b.appendLine(") : GenericServiceImpl<${l.idType}, ${l.dto}, ${l.entity}>(repo, mapper, daoDelegate)")
        b.appendLine()
        b.appendLine("class ${l.dll}(service: ${l.service}) : GenericDllImpl<${l.idType}, ${l.dto}>(service)")

        if (l.endpointPath != null && l.validatorFq != null) {
            val endpointBase = when (l.idType) {
                "String" -> "StringIdEndpointImpl"
                "CompositeID" -> "CompositeIdEndpointImpl"
                else -> "ULongIdEndpointImpl"
            }
            b.appendLine()
            b.appendLine("class ${l.api}(")
            b.appendLine("    dll: ${l.dll},")
            b.appendLine("    validator: ${l.validator}")
            if (l.idType == "CompositeID") {
                b.appendLine("    , daoDelegate: ${l.delegate}")
                b.appendLine(") : $endpointBase<${l.dto}, ${l.entity}>(dll, validator, daoDelegate, ${l.endpointPath.quoted()}, ${l.dto}.serializer())")
            } else {
                b.appendLine(") : $endpointBase<${l.dto}>(dll, validator, ${l.endpointPath.quoted()}, ${l.dto}.serializer())")
            }
        }

        codeGenerator.createNewFile(Dependencies(aggregating = true), PACKAGE, "${l.dto}Layer")
            .bufferedWriter().use { it.write(b.toString()) }
    }

    private fun writeModule(layers: List<Layer>) {
        val b = StringBuilder()
        b.appendLine("package $PACKAGE").appendLine()
        layers.mapNotNull { it.existingDelegateFq }.distinct().sorted()
            .forEach { b.appendLine("import $it") }
        b.appendLine("import org.koin.dsl.module").appendLine()
        b.appendLine("/**")
        b.appendLine(" * Generated bindings for the ${layers.size} wired types. Do not edit.")
        b.appendLine(" *")
        b.appendLine(" * Every definition is keyed by a concrete class. Binding the generic interfaces")
        b.appendLine(" * instead registered every type under one key and left the graph resolving to")
        b.appendLine(" * whichever definition happened to load last.")
        b.appendLine(" */")
        b.appendLine("val generatedLayerModule = module {")
        layers.sortedBy { it.dto }.forEach { l ->
            if (l.existingDelegateFq == null) {
                b.appendLine("    single { ${l.delegate}() }")
            } else {
                b.appendLine("    single { ${l.delegate} }")
            }
            b.appendLine("    single { ${l.repo}() }")
            b.appendLine("    single { ${l.service}(get(), get(), get()) }")
            b.appendLine("    single { ${l.dll}(get()) }")
            if (l.endpointPath != null && l.validatorFq != null) {
                val args = if (l.idType == "CompositeID") "get(), get(), get()" else "get(), get()"
                b.appendLine("    single { ${l.api}($args) }")
            }
        }
        b.appendLine("}")

        codeGenerator.createNewFile(Dependencies(aggregating = true), PACKAGE, "GeneratedLayerModule")
            .bufferedWriter().use { it.write(b.toString()) }
    }
}
