package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeDefinitionDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction


class TypeDefinitionRepository : OwleLongIdRepoImpl<TypeDefinitionDao>() {

    override val entityClass: LongEntityClass<TypeDefinitionDao>
        get() = TypeDefinitionDao

    override fun getWhere(predicate: (TypeDefinitionDao) -> Boolean): List<TypeDefinitionDao> {
        return transaction {
            TypeDefinitionDao.all().filter(predicate)
        }
    }
}