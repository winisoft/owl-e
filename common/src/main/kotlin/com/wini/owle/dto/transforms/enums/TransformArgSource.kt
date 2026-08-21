package com.wini.owle.dto.transforms.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

/**
 * Where the value bound to a transform's argument comes from.
 *
 * An argument used to be a literal and nothing else - `transform_occurence_args.value` was a
 * `NOT NULL` text column - which made the interesting half of the operation set pointless: adding
 * a literal to a value is a curiosity, adding a tool's result to a running total is the actual
 * work.
 *
 * The five things a user would reach for - an untransformed read, an untransformed tool result, a
 * transformed read, a transformed tool result, the end of a chain - collapse to three sources,
 * because "transformed" and "chained" are both simply *the result of a transform occurrence*. Those
 * three are the same set a transform's own subject may draw from, which is why this mirrors the
 * nullable-pair idiom already used by `transform_occurrences`.
 */
@Serializable
enum class TransformArgSource(override val dbValue: String) : DbToken {

    /** A value typed in at authoring time. Held in `value`; every source column is null. */
    LITERAL("LITERAL"),

    /** A data point read as it stands, untransformed. */
    DATA_POINT("DATA_POINT"),

    /** The result of the tool a step runs, untransformed. */
    TOOL_RESULT("TOOL_RESULT"),

    /**
     * The result of another transform occurrence.
     *
     * Covers a transformed read, a transformed tool result, and the end of a chain alike - all
     * three are one occurrence's output, and `transform_occurrence_chains` already relates them.
     */
    TRANSFORM_RESULT("TRANSFORM_RESULT")

}
