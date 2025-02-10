package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeSchemaRefDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TypeSchemaRefRepository : OwleLongIdRepoImpl<TypeSchemaRefDao>() {

    override val entityClass: LongEntityClass<TypeSchemaRefDao>
        get() = TypeSchemaRefDao

    override fun getWhere(predicate: (TypeSchemaRefDao) -> Boolean): List<TypeSchemaRefDao> {
        return transaction {
            TypeSchemaRefDao.all().filter(predicate)
        }
    }
}