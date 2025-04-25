package com.wini.owle.repo.typing

import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class TypeUnionMemberRepository: OwleRepoImpl<CompositeID, TypeUnionMemberEntity>(TypeUnionMemberEntity) {

    fun getByUnionTypeDeclarationId(typeDeclarationId: Long): List<TypeUnionMemberEntity> {
        return getWhere {
            it.unionTypeDeclarationEntity.id == EntityID(typeDeclarationId, TypeDeclarations)
        }
    }

}