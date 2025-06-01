package com.wini.owle.entity.environs.version

import com.wini.owle.entity.environs.PythonVersions
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class PythonVersionEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<PythonVersionEntity>(PythonVersions)

    var version by VersionEntity referencedOn PythonVersions.id

    var isSupported by PythonVersions.isSupported
}
