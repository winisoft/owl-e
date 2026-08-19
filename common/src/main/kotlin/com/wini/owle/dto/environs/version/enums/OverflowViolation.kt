package com.wini.owle.dto.environs.version.enums

import com.wini.owle.dto.DbToken


/**
 * Which part of a version could not be held by the column meant for it.
 *
 * PEP 440 puts no upper bound on a version's numeric components, and the columns holding them are
 * `BIGINT UNSIGNED` - twenty digits. A version like `20200101000000000000000000.0.0` is entirely
 * spec-compliant and does not fit. Rather than storing every version as text (which would make the
 * common case slow to order) or refusing the package (which would lose real data over a formatting
 * curiosity), what fits is stored, what did not is recorded here verbatim, and `versions
 * .has_overflow` flags the row as one whose numeric columns cannot be trusted for comparison.
 */
enum class OverflowViolation(override val dbValue: String) : DbToken {

    EPOCH("epoch"),

    MAJOR("major"),

    MINOR("minor"),

    MICRO("micro"),

    /**
     * Release segments past the fourth, not a magnitude problem.
     *
     * PEP 440's release segment is `N(.N)*` - arbitrarily many - while the table has epoch, major,
     * minor and micro. `1.2.3.4.5` overflows by *arity*, and `string_value` keeps the remainder
     * verbatim. Same mechanism, different reason.
     */
    MICRO_EXTRA("micro_extra"),

    PHASE_NUMBER("phase_number"),

    POST_NUMBER("post_number"),

    DEV_NUMBER("dev_number"),

    /**
     * The version could not be represented at all, even componentwise. `string_value` holds the
     * whole original string.
     *
     * Nothing seen so far needs this - between per-component overflow and [MICRO_EXTRA] every
     * PEP 440 version decomposes - but it is the backstop that guarantees an ingest never has to
     * choose between dropping a package and storing a lie. Reusing the existing violation mechanism
     * costs one enum value rather than a column.
     */
    WHOLE_VERSION("whole_version")
}
