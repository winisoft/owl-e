package com.wini.owle.entity.environs.pkg

import com.wini.owle.entity.StringEntityClass
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class DistributionEntity(id: EntityID<String>) : Entity<String>(id) {

    companion object : StringEntityClass<DistributionEntity>(Distributions, DistributionEntity::class.java)

    var name by Distributions.id

}