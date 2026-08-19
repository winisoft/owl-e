package com.wini.owle.dto.environs.version.enums

import com.wini.owle.dto.DbToken


/**
 * A version's pre-release phase, per PEP 440.
 *
 * **Declared in ascending order, and that order is load-bearing.** PEP 440 ranks
 * `alpha < beta < candidate < final`, MySQL orders an `ENUM` by declaration order, and the column
 * is declared to match - so `ORDER BY phase` sorts correctly with no `CASE` at any call site, in
 * Kotlin and in SQL alike. Reordering these constants silently reverses version comparison.
 */
enum class PrereleasePhase(override val dbValue: String) : DbToken {

    ALPHA("alpha"),

    BETA("beta"),

    CANDIDATE("candidate"),

    /** No pre-release phase. `.post` and `.dev` releases attach to this. */
    FINAL("final")
}
