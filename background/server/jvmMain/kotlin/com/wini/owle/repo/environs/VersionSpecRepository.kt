package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class VersionSpecRepository : OwleLongIdRepoImpl<VersionSpecDao>() {

    override val entityClass: LongEntityClass<VersionSpecDao>
        get() = VersionSpecDao

    override fun getWhere(predicate: (VersionSpecDao) -> Boolean): List<VersionSpecDao> {
        return transaction {
            VersionSpecDao.all().filter(predicate)
        }
    }
}