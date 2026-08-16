package com.wini.owle.entity.environs.version

import com.wini.owle.entity.environs.PythonVersions
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * Marks a [VersionEntity] as being a Python interpreter version. The row carries no data of its
 * own — its id *is* the version's id, so this is a membership assertion, not a record.
 */
class PythonVersionEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<PythonVersionEntity>(PythonVersions)

    /**
     * The underlying version — same row id as this entity.
     */
    val version: VersionEntity get() = VersionEntity[id]
}
