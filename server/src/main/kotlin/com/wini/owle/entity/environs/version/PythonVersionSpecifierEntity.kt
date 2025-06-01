package com.wini.owle.entity.environs.version

import com.wini.owle.entity.environs.PythonVersionSpecifiers
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class PythonVersionSpecifierEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<PythonVersionSpecifierEntity>(PythonVersionSpecifiers)

    var version by PythonVersionEntity referencedOn PythonVersionSpecifiers.pythonVersion

    var operator by PythonVersionSpecifiers.operator

    var wildcard by PythonVersionSpecifiers.wildcard

}