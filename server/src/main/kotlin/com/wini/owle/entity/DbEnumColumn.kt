package com.wini.owle.entity

import com.wini.owle.dto.DbToken
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table


/**
 * An enum column that reads and writes the database's own spelling.
 *
 * Replaces `enumerationByName`, which matches the constant name exactly and so could not read
 * twenty of this schema's twenty-nine enum columns. See [DbToken] for why the spellings differ and
 * why the database keeps them.
 *
 * A value the enum does not declare fails naming the column, the value and the alternatives,
 * rather than surfacing as Exposed's "can't be associated with any from enum" with no column named.
 */
inline fun <reified T> Table.dbEnum(name: String, length: Int): Column<T>
    where T : Enum<T>, T : DbToken =
    customEnumeration(
        name,
        "VARCHAR($length)",
        { value ->
            val stored = value as? String ?: value.toString()
            enumValues<T>().firstOrNull { it.dbValue == stored }
                ?: error(
                    "$tableName.$name holds '$stored', which ${T::class.simpleName} does not " +
                        "declare; it has ${enumValues<T>().joinToString { it.dbValue }}"
                )
        },
        { it.dbValue }
    )
