package com.wini.owle.data.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1

/**
 * Utility for converting Kotlin values to SQL-safe literal strings.
 *
 * Usage:
 *  val literal = Sql[value]  // produces NULL, numeric, or quoted/escaped value.
 */
object Sql {

    /** Returns an SQL-safe literal for the supplied [value]. */
    operator fun get(value: Any?): String = when (value) {
        null -> "NULL"
        is String -> "'${value.replace("'", "''")}'" // escape single quotes
        is Char -> "'${value.toString().replace("'", "''")}'"
        is Boolean -> if (value) "TRUE" else "FALSE"
        is Enum<*> -> "'${value.name}'"
        is LocalDateTime -> "'${value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}'"
        is LocalDate -> "'${value.format(DateTimeFormatter.ISO_LOCAL_DATE)}'"
        is UByte, is UShort, is UInt, is ULong,
        is Byte, is Short, is Int, is Long,
        is Float, is Double -> value.toString()
        else -> "'${value.toString().replace("'", "''")}'"
    }

    operator fun get(vararg values: Any?): String = "(${values.joinToString(",") { get(it) }})"

}
