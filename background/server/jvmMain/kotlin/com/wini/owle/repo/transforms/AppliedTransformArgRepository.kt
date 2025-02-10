package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.AppliedTransformArgDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class AppliedTransformArgRepository : OwleLongIdRepoImpl<AppliedTransformArgDao>() {

    override val entityClass: LongEntityClass<AppliedTransformArgDao>
        get() = AppliedTransformArgDao

    override fun getWhere(predicate: (AppliedTransformArgDao) -> Boolean): List<AppliedTransformArgDao> {
        return transaction {
            AppliedTransformArgDao.all().filter(predicate)
        }
    }
}