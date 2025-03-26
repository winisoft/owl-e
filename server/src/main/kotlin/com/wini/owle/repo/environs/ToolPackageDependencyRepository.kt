package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.ToolPackageDependencyDao
import com.wini.owle.repo.OwleCompositeIdRepoImpl
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class ToolPackageDependencyRepository : OwleCompositeIdRepoImpl<ToolPackageDependencyDao>() {

    override val entityClass: CompositeEntityClass<ToolPackageDependencyDao>
        get() = ToolPackageDependencyDao

}