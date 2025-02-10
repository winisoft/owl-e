package com.wini.owle.repo.typing

import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.typing.TypeUnionMemberDao
import com.wini.owle.repo.OwleCompositeIdRepoImpl
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class TypeUnionMemberRepository : OwleCompositeIdRepoImpl<TypeUnionMemberDao>() {

    override val entityClass: CompositeEntityClass<TypeUnionMemberDao>
        get() = TypeUnionMemberDao

    fun getByUnionTypeDeclarationId(typeDeclarationId: Long): List<TypeUnionMemberDao> {
        return getWhere {
            it.unionTypeDeclaration.id == EntityID(typeDeclarationId, TypeDeclarations)
        }
    }

    override fun getWhere(predicate: (TypeUnionMemberDao) -> Boolean): List<TypeUnionMemberDao> {
        return transaction {
            TypeUnionMemberDao.all().filter(predicate)
        }
    }
}