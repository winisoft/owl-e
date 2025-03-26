package com.wini.owle.repo.typing

import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.typing.TypeParameterDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class TypeParameterRepository: OwleRepoImpl<Long, TypeParameterDao>(TypeParameterDao) {

    fun getByTypeDeclarationIdOrderByPosition(typeDeclarationId: EntityID<Long>): List<TypeParameterDao> {
        return getWhere {
            it.typeDeclaration.id == typeDeclarationId
        }.sortedBy {
            it.position
        }
    }

}