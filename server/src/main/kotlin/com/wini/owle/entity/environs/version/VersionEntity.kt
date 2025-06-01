package com.wini.owle.entity.environs.version

import com.wini.owle.entity.environs.VersionOverflows
import com.wini.owle.entity.environs.Versions
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class VersionEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<VersionEntity>(Versions)

    var epoch by Versions.epoch

    var major by Versions.major

    var minor by Versions.minor

    var micro by Versions.micro

    var phase by Versions.phase

    var phaseNumber by Versions.phaseNumber

    var postNumber by Versions.postNumber

    var devNumber by Versions.devNumber

    var local by Versions.local

    var hasOverflow by Versions.hasOverflow

    val versionOverflows by VersionOverflowEntity referrersOn VersionOverflows.version

}
