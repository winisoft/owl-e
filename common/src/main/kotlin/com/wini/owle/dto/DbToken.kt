package com.wini.owle.dto


/**
 * An enum whose database spelling is not its constant name.
 *
 * `enumerationByName` matches the constant name exactly, and for twenty of the twenty-nine enum
 * columns it never could: MySQL stores `final`, `pypi`, `on hold`, `American Express`, `==` and
 * `not in`, none of which are legal or idiomatic Kotlin constant names. Every one of those columns
 * threw `IllegalStateException: <value> can't be associated with any from enum ...` the moment a
 * row carrying it was read — a latent crash on twenty columns that only surfaced where a test
 * happened to read one.
 *
 * There is no convention that bridges them: nine columns really are spelled in upper case, so
 * blanket-lowercasing breaks as much as it fixes. The spelling is therefore stated per constant
 * rather than derived, and [com.wini.owle.entity.dbEnum] is the only way these columns are declared.
 *
 * The database keeps its spelling because it holds data — the archived 6.6M-row set is written in
 * these tokens, so changing the schema would mean rewriting every row of it.
 */
interface DbToken {
    /** Exactly the value stored in the column. */
    val dbValue: String
}
