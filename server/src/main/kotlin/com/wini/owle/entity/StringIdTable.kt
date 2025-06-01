package com.wini.owle.entity

import com.wini.owle.dto.environs.pkg.Distribution
import org.jetbrains.exposed.v1.core.dao.id.EntityID

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.IdTable


/*
 * Base class for table objects with string id
 */
open class StringIdTable(
    tableName: String,
    columnName: String = "id",
    columnLength: Int = DEFAULT_NAME_LENGTH
) : IdTable<String>(tableName) {

    override val id: Column<EntityID<String>> = varchar(columnName, columnLength).entityId()

    override val primaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }

    companion object {

        const val DEFAULT_NAME_LENGTH = 255
    }
}