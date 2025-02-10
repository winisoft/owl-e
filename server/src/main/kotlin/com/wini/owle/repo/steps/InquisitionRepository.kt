package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.InquisitionDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class InquisitionRepository : OwleLongIdRepoImpl<InquisitionDao>() {

    override val entityClass: LongEntityClass<InquisitionDao>
        get() = InquisitionDao

    override fun getWhere(predicate: (InquisitionDao) -> Boolean): List<InquisitionDao> {
        return transaction {
            InquisitionDao.all().filter(predicate)
        }
    }
}