package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ProvisionDataPointDao
import com.wini.owle.repo.OwleCompositeIdRepoImpl
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.sql.transactions.transaction


class ProvisionDataPointRepository : OwleCompositeIdRepoImpl<ProvisionDataPointDao>() {

    override val entityClass: CompositeEntityClass<ProvisionDataPointDao>
        get() = ProvisionDataPointDao

}