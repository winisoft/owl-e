package com.wini.owle.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueParameter

/**
 * Generates a `DbMapper` per (DTO, Entity) pair.
 *
 * ### Why it is driven from the entity side
 * This processor runs against **`server`** (see [OwleTarget]) because its output must reference
 * Exposed entities, which `common` cannot see. KSP's `getSymbolsWithAnnotation` only scans the
 * module being compiled, so the `@ModelDefinition` DTOs — a *binary dependency* here — are
 * invisible to it. Entities are server sources, so the walk starts at every `*Entity`
 * declaration and resolves its DTO through [Resolver.getClassDeclarationByName], which does
 * see dependencies.
 *
 * ### What it matches on
 * `DataPointInjection` ⇄ `DataPointInjectionEntity`, and a DTO constructor parameter binds to
 * the entity property of the **same name** — a convention that is now uniform. But name
 * agreement is not enough: the two sides frequently disagree on *type*, and those disagreements
 * are the interesting part. [classify] resolves each pair to a concrete strategy and refuses
 * anything it cannot account for.
 *
 * ### Skipping is a feature
 * A mapper is emitted only when every parameter resolves *and* every collaborator it needs is
 * itself being emitted. Everything else is skipped with a reason. The skip list is therefore a
 * precise, always-current inventory of DTO/entity drift.
 */
class DbMapperGeneratorProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var done = false

    private companion object {
        const val TARGET_PACKAGE = "com.wini.owle.mapper.generated"
        const val MODEL_DEFINITION = "com.wini.owle.schema.ModelDefinition"
        const val INSTANT = "kotlinx.datetime.Instant"
        const val STRING = "kotlin.String"

        val DTO_PACKAGES = listOf(
            "com.wini.owle.dto",
            "com.wini.owle.dto.environs", "com.wini.owle.dto.environs.marker",
            "com.wini.owle.dto.environs.pkg", "com.wini.owle.dto.environs.version",
            "com.wini.owle.dto.runs", "com.wini.owle.dto.runs.step",
            "com.wini.owle.dto.runs.test", "com.wini.owle.dto.runs.workflow",
            "com.wini.owle.dto.steps", "com.wini.owle.dto.tags",
            "com.wini.owle.dto.tags.targets", "com.wini.owle.dto.transforms",
            "com.wini.owle.dto.typing", "com.wini.owle.dto.user",
            "com.wini.owle.dto.workflows",
        )

        val ID_TYPES = mapOf(
            "com.wini.owle.dto.ULongDto" to "ULong",
            "com.wini.owle.dto.StringDto" to "String",
            "com.wini.owle.dto.StandardDto" to "Long",
            "com.wini.owle.dto.CompositeDto" to "CompositeID",
        )
    }

    /**
     * How one DTO constructor parameter crosses the boundary. [writable] is false when the
     * entity side is a `val` (typically derived from the primary key), which can be read for
     * `toDto` but never assigned in `toEntity`.
     */
    private sealed interface Binding {
        val param: String
        val writable: Boolean get() = true

        data class Id(override val param: String) : Binding {
            override val writable get() = false   // the DaoDelegate owns the key
        }

        data class Direct(override val param: String, override val writable: Boolean) : Binding

        /** DTO holds an ISO-8601 `String`; the entity column is an `Instant`. */
        data class Timestamp(override val param: String, val nullable: Boolean) : Binding

        /** DTO holds the enum, entity stores its name as text. */
        data class EnumToText(override val param: String, val nullable: Boolean) : Binding

        /** DTO holds text, entity column is a typed enum. */
        data class TextToEnum(
            override val param: String, val nullable: Boolean, val enumFq: String
        ) : Binding

        data class Relation(
            override val param: String, val dtoSimpleName: String,
            val nullable: Boolean, val selfReferential: Boolean,
            override val writable: Boolean
        ) : Binding

        /** Entity collections are owned by the child rows, so this is read-only. */
        data class RelationList(
            override val param: String, val dtoSimpleName: String, val selfReferential: Boolean
        ) : Binding {
            override val writable get() = false
        }
    }

    private data class Plan(
        val dto: KSClassDeclaration,
        val entity: KSClassDeclaration,
        val idType: String,
        val bindings: List<Binding>
    )

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (done) return emptyList()
        done = true

        val entities = resolver.getAllFiles()
            .flatMap { it.declarations }
            .filterIsInstance<KSClassDeclaration>()
            .filter { it.classKind == ClassKind.CLASS && it.simpleName.asString().endsWith("Entity") }
            .toList()

        if (entities.isEmpty()) {
            logger.warn("[mapper-gen] no *Entity declarations found; nothing to generate")
            return emptyList()
        }

        val plans = mutableListOf<Plan>()
        val skipped = mutableListOf<Pair<String, String>>()

        for (entity in entities) {
            val dtoName = entity.simpleName.asString().removeSuffix("Entity")
            val dto = findDto(resolver, dtoName)
            if (dto == null) {
                skipped += dtoName to "no DTO named '$dtoName' on the classpath"; continue
            }
            if (dto.annotations.none { it.fq() == MODEL_DEFINITION }) {
                skipped += dtoName to "DTO is not annotated @ModelDefinition"; continue
            }
            val ctor = dto.primaryConstructor
            if (ctor == null || ctor.parameters.isEmpty()) {
                skipped += dtoName to "DTO has no primary constructor parameters"; continue
            }
            val idType = dto.superTypes.mapNotNull { it.resolve().declaration.qualifiedName?.asString() }
                .firstNotNullOfOrNull { ID_TYPES[it] }
            if (idType == null) {
                skipped += dtoName to "no known id supertype"; continue
            }

            val props = entity.getAllProperties().associateBy { it.simpleName.asString() }
            val bindings = mutableListOf<Binding>()
            var problem: String? = null

            for (p in ctor.parameters) {
                val b = classify(p, dtoName, props, resolver)
                if (b == null) {
                    val n = p.name?.asString()
                    val dt = p.type.resolve()
                    val et = props[n]?.type?.resolve()
                    problem = if (et == null) "'$n: ${dt.render()}' has no matching entity property"
                    else "'$n' type mismatch: DTO ${dt.render()} vs entity ${et.render()}"
                    break
                }
                bindings += b
            }

            if (problem != null) skipped += dtoName to problem else plans += Plan(dto, entity, idType, bindings)
        }

        // Close the set: a mapper injects one collaborator per related DTO, so if a collaborator
        // was skipped this one cannot compile either. Repeat until stable, otherwise a single
        // drifted DTO surfaces as unresolved references instead of an explained skip.
        val byName = plans.associateBy { it.dto.simpleName.asString() }.toMutableMap()
        var settling = true
        while (settling) {
            settling = false
            for ((name, plan) in byName.entries.toList()) {
                val missing = plan.bindings.mapNotNull {
                    when (it) {
                        is Binding.Relation -> it.dtoSimpleName.takeUnless { _ -> it.selfReferential }
                        is Binding.RelationList -> it.dtoSimpleName.takeUnless { _ -> it.selfReferential }
                        else -> null
                    }
                }.distinct().filter { it !in byName }
                if (missing.isNotEmpty()) {
                    byName.remove(name)
                    skipped += name to "needs skipped mapper(s): ${missing.sorted().joinToString()}"
                    settling = true
                }
            }
        }

        val emitted = byName.values.sortedBy { it.dto.simpleName.asString() }
        emitted.forEach { p ->
            runCatching { write(p) }
                .onFailure { logger.warn("[mapper-gen] ${p.dto.simpleName.asString()}: ${it.message}") }
        }

        if (emitted.isNotEmpty()) {
            runCatching { writeKoinModule(emitted) }
                .onFailure { logger.warn("[mapper-gen] koin module: ${it.message}") }
        }

        logger.warn("[mapper-gen] generated ${emitted.size} mappers; skipped ${skipped.size}")
        skipped.sortedBy { it.first }.forEach { (n, why) -> logger.warn("[mapper-gen]   SKIP $n : $why") }
        return emptyList()
    }

    // ---------------------------------------------------------------- resolution

    private fun com.google.devtools.ksp.symbol.KSAnnotation.fq() =
        annotationType.resolve().declaration.qualifiedName?.asString()

    private fun KSType.fq() = declaration.qualifiedName?.asString()
    private fun KSType.render() = declaration.simpleName.asString() + if (isMarkedNullable) "?" else ""
    private fun KSType.isEnum() = (declaration as? KSClassDeclaration)?.classKind == ClassKind.ENUM_CLASS

    private fun findDto(resolver: Resolver, simpleName: String): KSClassDeclaration? =
        DTO_PACKAGES.firstNotNullOfOrNull {
            resolver.getClassDeclarationByName(resolver.getKSNameFromString("$it.$simpleName"))
        }

    /**
     * Resolves one parameter to a [Binding], or null when the two sides cannot be reconciled.
     * Returning null is the point: it is what keeps a broken mapper from being emitted.
     */
    private fun classify(
        p: KSValueParameter,
        ownerDtoName: String,
        entityProps: Map<String, KSPropertyDeclaration>,
        resolver: Resolver
    ): Binding? {
        val name = p.name?.asString() ?: return null
        if (name == "id") return Binding.Id(name)

        val dtoType = p.type.resolve()
        val nullable = dtoType.isMarkedNullable

        // List<X>: read from the entity's back-reference, never written back.
        if (dtoType.fq() == "kotlin.collections.List") {
            val arg = dtoType.arguments.firstOrNull()?.type?.resolve()?.declaration as? KSClassDeclaration
                ?: return null
            if (name !in entityProps) return null
            val n = arg.simpleName.asString()
            return Binding.RelationList(name, n, n == ownerDtoName)
        }

        val entityProp = entityProps[name] ?: return null
        val entType = entityProp.type.resolve()
        val writable = entityProp.isMutable

        // Nullability is part of the type. `Boolean` and `Boolean?` share a qualified name but
        // are not interchangeable in either direction, so a disagreement here is real drift and
        // must be reported rather than papered over with a silent `!!`.
        if (dtoType.fq() == entType.fq()) {
            if (nullable != entType.isMarkedNullable) return null
            return Binding.Direct(name, writable)
        }

        // Conversions below still have to agree on nullability — an `Instant?` column cannot
        // feed a non-null `String`, and quietly forcing it would hide the mismatch.
        val sameNullability = nullable == entType.isMarkedNullable

        // ISO-8601 text on the DTO, Instant column on the entity
        if (dtoType.fq() == STRING && entType.fq() == INSTANT) {
            return if (sameNullability) Binding.Timestamp(name, nullable) else null
        }

        // enum on one side, text on the other
        if (dtoType.isEnum() && entType.fq() == STRING) {
            return if (sameNullability) Binding.EnumToText(name, nullable) else null
        }
        if (dtoType.fq() == STRING && entType.isEnum()) {
            val fq = entType.fq() ?: return null
            return if (sameNullability) Binding.TextToEnum(name, nullable, fq) else null
        }

        // a related DTO whose entity counterpart is the entity property's type
        val related = dtoType.declaration as? KSClassDeclaration ?: return null
        val relName = related.simpleName.asString()
        val looksLikeEntity = entType.declaration.simpleName.asString() == "${relName}Entity"
        if (!looksLikeEntity) return null
        if (related.annotations.none { it.fq() == MODEL_DEFINITION } &&
            findDto(resolver, relName) == null) return null

        return Binding.Relation(name, relName, nullable, relName == ownerDtoName, writable)
    }

    // ---------------------------------------------------------------- emission

    private fun mapper(dto: String) = "${dto}Mapper"
    private fun ref(dto: String) = dto.replaceFirstChar(Char::lowercaseChar) + "Mapper"

    private fun write(plan: Plan) {
        val dtoName = plan.dto.simpleName.asString()
        val entityName = plan.entity.simpleName.asString()

        val collaborators = plan.bindings.mapNotNull {
            when (it) {
                is Binding.Relation -> it.dtoSimpleName.takeUnless { _ -> it.selfReferential }
                is Binding.RelationList -> it.dtoSimpleName.takeUnless { _ -> it.selfReferential }
                else -> null
            }
        }.distinct().sorted()

        val needsConverter = plan.bindings.any { it is Binding.Timestamp }
        val imports = buildList {
            add(plan.dto.qualifiedName!!.asString())
            add(plan.entity.qualifiedName!!.asString())
            add("com.wini.owle.mapper.DbMapper")
            add("com.wini.owle.entity.delegate.DaoDelegate")
            if (needsConverter) add("com.wini.owle.mapper.DateTimeConverter")
            if (plan.idType == "CompositeID") add("org.jetbrains.exposed.v1.core.dao.id.CompositeID")
        }.distinct().sorted()

        val b = StringBuilder()
        b.appendLine("package $TARGET_PACKAGE").appendLine()
        imports.forEach { b.appendLine("import $it") }
        b.appendLine()
        b.appendLine("/**")
        b.appendLine(" * Generated from [$dtoName] and [$entityName]. Do not edit - regenerate instead.")
        b.appendLine(" */")
        b.appendLine("class ${mapper(dtoName)}(")
        b.appendLine("    private val daoDelegate: DaoDelegate<${plan.idType}, $dtoName, $entityName>,")
        collaborators.forEach { b.appendLine("    private val ${ref(it)}: ${mapper(it)},") }
        b.appendLine(") : DbMapper<${plan.idType}, $dtoName, $entityName> {")
        b.appendLine()

        b.appendLine("    override fun toEntity(dto: $dtoName): $entityName =")
        b.appendLine("        daoDelegate.findOrCreate(dto) {")
        plan.bindings.filter { it.writable }.forEach { bind ->
            val rhs = when (bind) {
                is Binding.Direct -> "dto.${bind.param}"
                is Binding.Timestamp ->
                    if (bind.nullable) "dto.${bind.param}?.let { DateTimeConverter.fromString(it) }"
                    else "DateTimeConverter.fromString(dto.${bind.param})"
                is Binding.EnumToText ->
                    if (bind.nullable) "dto.${bind.param}?.name" else "dto.${bind.param}.name"
                is Binding.TextToEnum ->
                    if (bind.nullable) "dto.${bind.param}?.let { ${bind.enumFq}.valueOf(it) }"
                    else "${bind.enumFq}.valueOf(dto.${bind.param})"
                is Binding.Relation -> {
                    val call = if (bind.selfReferential) "toEntity" else "${ref(bind.dtoSimpleName)}.toEntity"
                    if (bind.nullable) "dto.${bind.param}?.let { $call(it) }" else "$call(dto.${bind.param})"
                }
                else -> return@forEach
            }
            b.appendLine("            ${bind.param} = $rhs")
        }
        b.appendLine("        }").appendLine()

        b.appendLine("    override fun toDto(entity: $entityName): $dtoName =")
        b.appendLine("        $dtoName(")
        plan.bindings.forEach { bind ->
            val rhs = when (bind) {
                is Binding.Id -> "entity.id.value"
                is Binding.Direct -> "entity.${bind.param}"
                is Binding.Timestamp ->
                    if (bind.nullable) "entity.${bind.param}?.let { DateTimeConverter.toString(it) }"
                    else "DateTimeConverter.toString(entity.${bind.param})"
                is Binding.EnumToText -> {
                    val enumFq = (plan.dto.primaryConstructor!!.parameters
                        .first { it.name?.asString() == bind.param }
                        .type.resolve().declaration.qualifiedName!!.asString())
                    if (bind.nullable) "entity.${bind.param}?.let { $enumFq.valueOf(it) }"
                    else "$enumFq.valueOf(entity.${bind.param})"
                }
                is Binding.TextToEnum ->
                    if (bind.nullable) "entity.${bind.param}?.name" else "entity.${bind.param}.name"
                is Binding.Relation -> {
                    val call = if (bind.selfReferential) "toDto" else "${ref(bind.dtoSimpleName)}.toDto"
                    if (bind.nullable) "entity.${bind.param}?.let { $call(it) }" else "$call(entity.${bind.param})"
                }
                is Binding.RelationList -> {
                    val call = if (bind.selfReferential) "toDto" else "${ref(bind.dtoSimpleName)}.toDto"
                    "entity.${bind.param}.map { $call(it) }"
                }
            }
            b.appendLine("            ${bind.param} = $rhs,")
        }
        b.appendLine("        )")
        b.appendLine("}")

        codeGenerator.createNewFile(Dependencies(aggregating = false), TARGET_PACKAGE, mapper(dtoName))
            .bufferedWriter().use { it.write(b.toString()) }
    }

    /**
     * The Koin bindings for everything that was emitted.
     *
     * Written from the same dependency information used to build the constructors, so the module
     * cannot drift out of step with the mappers: if a mapper is skipped, no binding is produced
     * for it, and nothing references one that does not exist.
     */
    private fun writeKoinModule(plans: List<Plan>) {
        val b = StringBuilder()
        b.appendLine("package $TARGET_PACKAGE").appendLine()
        b.appendLine("import org.koin.dsl.module").appendLine()
        b.appendLine("/**")
        b.appendLine(" * Generated Koin bindings for the ${plans.size} derivable DbMappers. Do not edit.")
        b.appendLine(" *")
        b.appendLine(" * Mappers whose DTO and entity disagree are absent by construction - see the")
        b.appendLine(" * `[mapper-gen] SKIP` lines in the build log for what is missing and why.")
        b.appendLine(" */")
        b.appendLine("val generatedMapperModule = module {")
        plans.forEach { p ->
            val dtoName = p.dto.simpleName.asString()
            val deps = p.bindings.mapNotNull {
                when (it) {
                    is Binding.Relation -> it.dtoSimpleName.takeUnless { _ -> it.selfReferential }
                    is Binding.RelationList -> it.dtoSimpleName.takeUnless { _ -> it.selfReferential }
                    else -> null
                }
            }.distinct().sorted()
            val args = (listOf("get()") + deps.map { "get()" }).joinToString(", ")
            b.appendLine("    single { ${mapper(dtoName)}($args) }")
        }
        b.appendLine("}")

        codeGenerator.createNewFile(
            Dependencies(aggregating = true),
            TARGET_PACKAGE,
            "GeneratedMapperModule"
        ).bufferedWriter().use { it.write(b.toString()) }
    }
}

class DbMapperGeneratorProcessorProvider : SymbolProcessorProvider {
    /** Needs Exposed entities and the DaoDelegate - server only. */
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        environment.processorFor(OwleTarget.SERVER) {
            DbMapperGeneratorProcessor(environment.codeGenerator, environment.logger)
        }
}
