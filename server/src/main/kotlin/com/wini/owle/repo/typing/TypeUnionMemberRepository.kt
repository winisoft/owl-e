package com.wini.owle.repo.typing

import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.typing.TypeUnionMemberDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class TypeUnionMemberRepository: OwleRepoImpl<CompositeID, TypeUnionMemberDao>(TypeUnionMemberDao) {

    fun getByUnionTypeDeclarationId(typeDeclarationId: Long): List<TypeUnionMemberDao> {
        return getWhere {
            it.unionTypeDeclaration.id == EntityID(typeDeclarationId, TypeDeclarations)
        }
    }

}