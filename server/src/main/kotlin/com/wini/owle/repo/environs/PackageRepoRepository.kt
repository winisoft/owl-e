package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction


open class PackageRepoRepository : OwleLongIdRepoImpl<PackageRepoDao>() {

    override val entityClass: LongEntityClass<PackageRepoDao>
        get() = PackageRepoDao

}