package com.wini.owle.ksp.utils


/**
 * Operation summaries and descriptions, written once and filled in per type.
 *
 * These used to be stated per type per operation: forty-four types times sixteen strings, a
 * thousand-odd properties saying the same eight sentences with a different noun in them. Nothing
 * could see that they had stopped agreeing, and they had — the file carried a double-pluralised
 * "Deletes multiple {plural}s", several descriptions with the plural dropped into the singular
 * slot, entries for types that no longer existed, no plural at all for `Workflow`, and two
 * properties run together on one line by a lost newline.
 *
 * Now there are eight sentences, each holding a `{dtoNameSingle}` or `{dtoNamePlural}` slot.
 *
 * The nouns are **not** in the bundle. A DTO's name and package are already known to the processor
 * from the declaration it is reading, and restating them in a properties file only creates
 * somewhere for them to drift from the class - which is exactly what happened: the ids in
 * `@ApiEndpointDefinition` and the names in the bundle disagreed for sixty-eight types. Deriving
 * them from the declaration means the two cannot disagree, because there is only one of them.
 *
 * The one thing that genuinely cannot be derived is the English plural, so [pluralOf] adds an `s`
 * and the bundle carries an override only for the words where that is wrong.
 */
object ApiDocStrings {

    /** `Get All`, `Create Batch`, and so on - one per (method, cardinality), not per type. */
    fun summary(httpMethod: String, cardinality: String): String =
        LocalizedStringResolver.resolve("api.op.$httpMethod.$cardinality.summary")

    /** The sentence for an operation, with the type's name filled in. */
    fun description(
        httpMethod: String,
        cardinality: String,
        dtoNameSingle: String,
        dtoNamePlural: String
    ): String = LocalizedStringResolver.resolve("api.op.$httpMethod.$cardinality.description")
        .replace("{dtoNameSingle}", dtoNameSingle)
        .replace("{dtoNamePlural}", dtoNamePlural)

    /**
     * The plural of a DTO's name.
     *
     * `s` is right for all but a handful, so the bundle states only the exceptions - `ErrorWatch`
     * becomes `ErrorWatches`, `TypeAlias` becomes `TypeAliases`. Keyed by the type's simple name
     * rather than by its endpoint id, because the plural is a fact about the word and has nothing
     * to do with where the endpoint is filed.
     */
    fun pluralOf(dtoNameSingle: String): String {
        val override = "dto.plural.$dtoNameSingle"
        return if (LocalizedStringResolver.isBound(override)) {
            LocalizedStringResolver.resolve(override)
        } else {
            "${dtoNameSingle}s"
        }
    }
}
