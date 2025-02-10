package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ProvisionDataPointDao
import com.wini.owle.repo.OwleCompositeIdRepoImpl
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.sql.transactions.transaction


class ProvisionDataPointRepository : OwleCompositeIdRepoImpl<ProvisionDataPointDao>() {

    override val entityClass: CompositeEntityClass<ProvisionDataPointDao>
        get() = ProvisionDataPointDao

    override fun getWhere(predicate: (ProvisionDataPointDao) -> Boolean): List<ProvisionDataPointDao> {
        return transaction {
            ProvisionDataPointDao.all().filter(predicate)
        }
    }
}