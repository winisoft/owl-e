# type-schema — the KSP System

*Rewritten 2026-08-15. Previous version of this file documented one aspirational feature ("Owl-e API Documentation System") in prose dense enough to hide the actually-load-bearing parts. This version covers everything: what exists, what it's called, what generates it, and what's quietly broken. Grounded in reading the real source and running real builds, not the intent behind it — see [owl-e-build-and-validators memory] for the session that did the verification.*

---

## 1. The shape of the system, in one picture

You write **annotations** on a DTO in `common/`. **`type-schema`** is a KSP module — at compile time it reads those annotations off your source and writes new `.kt` files that get compiled alongside yours. Three independent passes run over the same collected symbols and produce three different outputs:

```
common/dto/**/*.kt  (you write these, annotated)
        │
        ▼
   OwleSymbolCollector          — walks the compilation once, caches every
   (type-schema/schema/utils)     @ModelDefinition class, @ErrorDefinition class,
                                   and API-endpoint interface it finds
        │
        ├──▶ ValidatorGeneratorProcessor ──▶ common/validators/*Validator.kt + Module.kt
        │        (ACTIVE, WORKS)              (Koin bindings)
        │
        ├──▶ ModelSchemaProcessor ──────────▶ error/ErrorResourceExtensions.kt
        │        (ACTIVE, PARTIALLY BROKEN)    + <Model>Doc.md per DTO
        │
        └──▶ OwleApiDocProcessor ───────────▶ an OpenAPI spec file
                 (ACTIVE, UNVERIFIED OUTPUT)
```

A fourth family of processors (`ApiDocProcessor`, `SimpleApiDocProcessor`, `EnhancedApiDocProcessor`, and the now-quarantined `DirectApiDocProcessor.kt.disabled`) exists in source but **is not registered** — see §5.

## 2. The annotations you actually write

| Name | Category | Target | Lives in |
|---|---|---|---|
| `@ModelDefinition(skipValidation: Boolean = false)` | model marker | class | `type-schema` — `schema/ModelDefinition.kt` |
| `@ConstraintSimple(errors: Array<KClass<*>>)` | field constraint | property | `type-schema` — `schema/error/FieldValidations.kt` |
| `@ConstraintStringLenMax(error: KClass<*>, maxLength: Int)` | field constraint | property | same file |
| `@ConstraintStringLenExact(error: KClass<*>, length: Int)` | field constraint | property | same file |
| `@ConstraintDoubleRange(error: KClass<*>, minValue: Double, maxValue: Double)` | field constraint | property | same file |
| `@PreventsTimeParadox(happensFirstField, happensSecondField, error)` | class-level constraint | class | `type-schema` — `schema/error/ClassValidations.kt` |
| `@InconsistentOAuthId`, `@TagWithNonSourceUserIdError`, `@TagWithUserSourceUserIdError` | class-level constraint | class | same file — **defined but not wired to any generator, see §6** |
| `@ErrorDefinition(errorResKey, ignoredInDocs = false, includedProperties = [...])` | error marker | class | `type-schema` — `schema/error/ErrorDefinition.kt` |

`@ModelDefinition` is what makes `OwleSymbolCollector` notice a class at all. `@ErrorDefinition` does the same for the `ConstraintError` sealed hierarchy in `common/error/`. Everything downstream is driven by these two markers plus whichever `@Constraint*` annotations sit on individual properties.

## 3. `ValidatorGeneratorProcessor` — the one that matters, and it works

**What it does:** for every `@ModelDefinition`-annotated class, emits `<Name>Validator.kt` implementing `DtoValidator<Name>` — one `add(SomeError(...))` line per constraint annotation on a property, resolved against the matching `@ErrorDefinition` class. Then emits one `Module.kt`: a Koin module binding `single<DtoValidator<X>> { XValidator(...) }` for every generated validator, with constructor `get()` calls wired automatically for validators whose model has a nested-model property.

**Real output**, for the `Distribution` DTO you pasted a few turns back:

```kotlin
class DistributionValidator : DtoValidator<Distribution> {
    override fun fieldValidations(dto: Distribution): List<ConstraintError> = buildList {
        add(StringNotEmptyError(Distribution::name, dto))
        add(StringNotBlankError(Distribution::name, dto))
        add(StringMaxLengthError(Distribution::name, dto, 255))
    }
}
```

**Files involved:**

| Name | Category | Role |
|---|---|---|
| `ValidatorGeneratorProcessor` | `SymbolProcessor` | orchestrates the pass |
| `ValidatorGeneratorProcessorProvider` | `SymbolProcessorProvider` | KSP entry point (registered) |
| `ValidatorFileWriter` (pkg `ksp.writer`) | code writer | emits one `*Validator.kt` per model |
| `ValidatorModuleFileWriter` (pkg `ksp.writer`) | code writer | emits the Koin `Module.kt` |
| `ConstraintExtractor` (pkg `ksp.utils`) | translation table | turns one constraint annotation into one `add(...)` source line |
| `OwleSymbolCollector` | shared collector | supplies the model/error lists every processor consumes |

**How a constraint annotation becomes a line of code** (`ConstraintExtractor.propertyExtract`): for each `@Constraint*` on a property, look up a formatter by the annotation's simple class name (`ConstraintArgFormatters` map); each formatter knows how to spell that annotation's specific extra arguments (e.g. `ConstraintStringLenMax` → `, dto, $maxLength`); anything without a specific formatter falls back to the bare `ClassName::prop, dto` shape (this is what `@ConstraintSimple` uses). The error class instantiated is resolved by matching the constraint's declared error `KClass`es against the collected `@ErrorDefinition` classes by fully-qualified name.

**Two silent gaps, confirmed by reading the extractor, not guessed:**

- **Nullable properties are skipped entirely.** `propertyExtract` returns `emptyList()` the moment `propertyType.isMarkedNullable` is true — a `@ConstraintStringLenMax`-annotated `String?` field compiles fine and generates *zero* checks for it. No warning either at compile time or from the generator's own logging beyond an `info`-level line.
- **Class-level constraints beyond `PreventsTimeParadox` do nothing.** `multiFieldExtract` is a hardcoded `when` on the annotation's simple name string, with exactly one branch. `@InconsistentOAuthId`, `@TagWithNonSourceUserIdError`, `@TagWithUserSourceUserIdError` all exist, all compile, all generate nothing if applied. Either wire them or stop declaring them — right now a reader has no way to tell they're inert without reading `ConstraintExtractor.kt` directly.

## 4. `ModelSchemaProcessor` — active, and confirmed buggy

**What it's for:** two outputs per KSP run. First, `error/ErrorResourceExtensions.kt` — for every `@ErrorDefinition` class, an extension function pair:
```kotlin
fun StringNotEmptyError<out BaseDto>.errorResCode(): String = "..."
fun StringNotEmptyError<out BaseDto>.errorResMsg(): String = "..."
```
with the string literals baked in *at generation time* by resolving `"$errorResKey.code"` / `"$errorResKey.message"` against the resource bundles (§7). Second, one `<Model>Doc.md` markdown file per `@ModelDefinition` class, written into that model's own package via `KnowledgeBaseModelFileWriter` — a generated knowledge-base entry per DTO.

| Name | Category | Role |
|---|---|---|
| `ModelSchemaProcessor` | `SymbolProcessor` | orchestrates the pass |
| `ModelSchemaProcessorProvider` | `SymbolProcessorProvider` | KSP entry point (registered) |
| `ErrorResFileWriter` (pkg `ksp.writer`) | code writer | emits `ErrorResourceExtensions.kt` |
| `KnowledgeBaseModelFileWriter` (pkg `ksp.writer`) | code writer | emits `<Model>Doc.md` |

**Confirmed bug:** `ModelSchemaProcessor.buildDtoDoc` (private fn, lines ~54–99) builds its `DtoModelDoc` inside `modelClass.constrainedFields.map { (prop, constraints) -> constraints.map { ... return DtoModelDoc(...) } }` — that `return` is a **non-local return out of `buildDtoDoc` itself**, fired from inside the innermost `.map{}`. It fires on the very first constraint of the very first constrained property, so every generated `<Model>Doc.md` describes at most one property, however many the model actually has. A large commented-out alternate implementation sits directly below it (lines ~101–155), suggesting this was mid-rewrite when abandoned. Not fixed as part of this doc pass — flagged here so it isn't rediscovered from scratch.

## 5. `OwleApiDocProcessor` — active, output not independently verified

**What it's for:** OpenAPI 3.0 spec generation from API-endpoint interfaces. Delegates to `ApiInterfaceProcessor` (pkg `ksp.processor`) to walk endpoint declarations into `content/oas/*Doc` model objects (`ApiEndpointDoc`, `ApiOperationDoc`, `ApiRequestBodyDoc`, etc. — see §8), then `OpenApiSpecFileWriter` serializes the assembled `OpenApiSpec` to a file. This is the pass that needed the OAS types written from scratch this session (`OasTag`, `ApiRequestBodyDoc`, `Info`/`Contact`/`License`) — they compile now, but this processor's output was not exercised end-to-end as part of getting the build green, only `:type-schema:compileKotlin` and the validator path were run to completion.

| Name | Category | Role |
|---|---|---|
| `OwleApiDocProcessor` | `SymbolProcessor` | orchestrates the pass |
| `OwleApiDocProcessorProvider` | `SymbolProcessorProvider` | KSP entry point (registered) |
| `ApiInterfaceProcessor`, `ApiMethodProcessor` (pkg `ksp.processor`) | walkers | endpoint interface → `content/oas` doc objects |
| `OpenApiSpecBuilder` (pkg `ksp.processor`) | assembler | doc objects → one `OpenApiSpec` |
| `OpenApiSpecFileWriter` (pkg `ksp.writer`) | code writer | `OpenApiSpec` → file on disk |

**Registered but inert or dead** — present in source, absent from (or commented out of) `META-INF/services/com.google.devtools.ksp.processing.SymbolProcessorProvider`, so KSP never loads them:

| Name | State |
|---|---|
| `ApiDocProcessor` / `ApiDocProcessorProvider` | defines a provider class, but that class is not listed in the services file — inert |
| `SimpleApiDocProcessor` / `SimpleApiDocProcessorProvider` | same — inert |
| `EnhancedApiDocProcessor` | provider commented out in its own source — dead |
| `DirectApiDocProcessor` | provider commented out; quarantined to `.kt.disabled` 2026-08-15 to unblock the build (imports `common` annotations `type-schema` has no dependency on) |

Read as four successive attempts at the same doc-generation goal, only the last-standing one (`OwleApiDocProcessor`) actually wired up. If reviving one of the others is ever wanted, `ApiDocProcessor` is the most complete-looking of the abandoned three.

## 6. Where models, errors, and endpoints get collected — `OwleSymbolCollector`

All three active processors call the same `OwleSymbolCollector.collect(resolver, logger) { (models, errors, endpoints) -> ... }` once per KSP round. It walks every file in the compilation exactly once, classifying declarations by which marker annotation they carry (`@ModelDefinition` → `ModelDeclaration`, `@ErrorDefinition` → `ErrorDeclaration`, API-endpoint interfaces → `ApiEndpointDeclaration`), and hands the same three lists to whichever processor asks. This is why adding a fourth processor is cheap — it doesn't need its own symbol-walking logic, just a consumer of the shared lists.

`ModelDeclaration`/`ErrorDeclaration`/`ApiEndpointDeclaration` are thin wrapper interfaces (pkg `schema.wrapper.declaration`) around raw `KSClassDeclaration`s, each with an `*Impl` sitting beside it — the pattern used throughout `type-schema` for anything that needs to expose KSP-annotation data through a typed, testable surface instead of raw reflection calls scattered through the processors.

## 7. The error / localization system — the part nobody had documented

This is the piece flagged as worst-understood, so here it is plainly, mechanism first.

**The pieces:**

- **`@ErrorDefinition(errorResKey, ignoredInDocs, includedProperties)`** — put this on a `ConstraintError` subtype in `common/error/`. `errorResKey` is a *prefix*, not a full key.
- **Resource bundles** — `.properties` files under `type-schema/src/main/resources/`, one base (`docres.properties`) plus locale variants (`docres_en.properties`, `docres_en_US.properties`, `docres_fr.properties`, `docres_de.properties`). Standard Java `ResourceBundle` naming and fallback rules apply — `Locale.getDefault()` picks the file, falling back toward the base name as locale-specific files run out.
- **`LocalizedStringResolver.resolve(key: String?)`** (pkg `ksp.utils`) — the one function every string lookup goes through. `ResourceBundle.getBundle("docres", Locale.getDefault()).getString(key).trim('"')`. **If the key is missing, this returns the key itself, silently** — a `MissingResourceException` is caught and swallowed, not surfaced. A typo in a resource key does not fail a build or a test; it just shows up as a raw dotted string where a message should be, later, in whatever consumed it.
- **`ErrorResFileWriter`** reads `"$errorResKey.code"` and `"$errorResKey.message"` off the bundle for every `@ErrorDefinition` class and bakes the resolved strings directly into generated `errorResCode()`/`errorResMsg()` extension functions at **KSP-generation time** — meaning the resource files are only ever read during a build, never at runtime. Changing a `.properties` file requires recompiling `common` before the new text takes effect anywhere.
- **API-facing resource keys** follow a separate, structured convention, visible directly in `docres_en_US.properties`: `api.<module>.<entity>.<operation>.<field>`, e.g. `api.environs.distribution.get.many.summary="Get All"`. These back the OpenAPI doc-gen path (§5), not the validator-error path — two different key families sharing one bundle.

**Worked example**, tracing `@ErrorDefinition("errors.constraint.stringNotEmptyError")` on `StringNotEmptyError`:
1. `ModelSchemaProcessor` collects it via `OwleSymbolCollector`.
2. `ErrorResFileWriter` resolves `LocalizedStringResolver.resolve("errors.constraint.stringNotEmptyError.code")` and `...message` against `docres_en_US.properties` (or whichever locale bundle is active at generation time).
3. It writes `fun StringNotEmptyError<out BaseDto>.errorResCode(): String = "<resolved code>"` and the `errorResMsg()` twin into `error/ErrorResourceExtensions.kt`.
4. Anything holding a `StringNotEmptyError` instance can now call `.errorResCode()` / `.errorResMsg()` directly — but the *value* was frozen at the last KSP run, not read live.

**Known rough edge, unrelated to the mechanism above but sitting in the same file:** `LocalizedStringResolver.kt` imports `jdk.internal.module.Resources`, a JDK-internal class that is never actually referenced in the file body. Harmless today, but it's the kind of import that can start failing under stricter module configurations. Worth deleting whenever this file is next touched.

## 8. The `content/` documentation model — plain data, no logic

Everything the doc-gen processors (§4, §5) assemble *into* is plain `@Serializable` data classes under `type-schema/src/main/kotlin/com/wini/owle/content/`, split by concern:

| Package | Holds | Category |
|---|---|---|
| `content` (root) | `EnumsDoc` | misc |
| `content/doc` | `ApiDoc`, `DtoModelDoc`, `ModelPropertyDoc`, `OwleErrorDoc` | knowledge-base doc model (§4's consumer) |
| `content/oas` | `OpenApiSpec`, `ApiEndpointDoc`, `ApiOperationDoc`, `ApiRequestBodyDoc`, `ApiResponseDoc`, `ApiMediaTypeDoc`, `ApiParameterDoc`, `ApiRouteDoc`, `OasTag`, `ParameterLocation` | OpenAPI spec model (§5's consumer) |
| `content/oas/info` | `Info`, `Contact`, `License` | OpenAPI `info` object |
| `content/oas/schema` | `ApiSchemaDoc`, `ArraySchemaDoc`, `ObjectSchemaDoc`, `PrimitiveSchemaDoc`, `RefSchemaDoc` | OpenAPI JSON-schema model |

None of these classes contain logic — they're pure carriers, written to by the processors in §4–§5 and serialized straight out with `kotlinx.serialization.json.Json`. `OasTag` is named to avoid colliding with the domain's own `Tag` DTO (`common/dto/tags/Tag.kt`), an unrelated user-facing concept — if you're looking for one and find the other, that's why there are two.

## 9. Practical notes for extending this

- **A `package` declaration is authoritative; its containing folder is a hint, not a contract.** Several files this session were found declaring a package that didn't match their folder — Kotlin resolves by the former, IntelliJ's auto-import sometimes guesses from the latter. If an import breaks right after an IDE-driven refactor, check the actual `package` line before trusting the folder structure.
- **KSP output lands in `<module>/build/generated/ksp/main/kotlin/`** — inspect it directly after a build to see exactly what a processor produced, rather than reading the writer source and imagining the output.
- **`hasGenerated` guards on every processor** (`ValidatorGeneratorProcessor`, `ModelSchemaProcessor`, `OwleApiDocProcessor` all have one) make each processor run its full pass exactly once per KSP invocation, on the first round — standard for processors that don't need multi-round symbol resolution.
- **`docs/Annotations/type-schema/`**, if still present, is a skeleton of ~98 empty (0–2 byte) files from an aborted doc-generation run mirroring this module's structure. It holds no recoverable content; safe to delete.
