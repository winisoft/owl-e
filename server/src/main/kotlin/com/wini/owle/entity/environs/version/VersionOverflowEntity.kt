package com.wini.owle.entity.environs.version

import com.wini.owle.entity.environs.VersionOverflows
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class VersionOverflowEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<VersionOverflowEntity>(VersionOverflows)

    var referenceVersion by VersionEntity referencedOn VersionOverflows.version

    var violation by VersionOverflows.violation

    var value by VersionOverflows.stringValue

}

