package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TypeDeclarationRepository : OwleLongIdRepoImpl<TypeDeclarationDao>() {

    override val entityClass: LongEntityClass<TypeDeclarationDao>
        get() = TypeDeclarationDao

    override fun getWhere(predicate: (TypeDeclarationDao) -> Boolean): List<TypeDeclarationDao> {
        return transaction {
            TypeDeclarationDao.all().filter(predicate)
        }
    }
}