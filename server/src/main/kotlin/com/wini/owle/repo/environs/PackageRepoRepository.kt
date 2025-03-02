package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction


class PackageRepoRepository : OwleLongIdRepoImpl<PackageRepoDao>() {

    override val entityClass: LongEntityClass<PackageRepoDao>
        get() = PackageRepoDao

    override fun getWhere(predicate: (PackageRepoDao) -> Boolean): List<PackageRepoDao> {
        return transaction {
            PackageRepoDao.all().filter(predicate)
        }
    }
}