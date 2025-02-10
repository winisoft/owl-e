package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class AppliedTransformRepository : OwleLongIdRepoImpl<AppliedTransformDao>() {

    override val entityClass: LongEntityClass<AppliedTransformDao>
        get() = AppliedTransformDao

    override fun getWhere(predicate: (AppliedTransformDao) -> Boolean): List<AppliedTransformDao> {
        return transaction {
            AppliedTransformDao.all().filter(predicate)
        }
    }
}