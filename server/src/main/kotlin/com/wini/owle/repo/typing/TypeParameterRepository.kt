package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeParameterEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.EntityID

class TypeParameterRepository: OwleRepoImpl<Long, TypeParameterEntity>(TypeParameterEntity) {

    fun getByTypeDeclarationIdOrderByPosition(typeDeclarationId: EntityID<Long>): List<TypeParameterEntity> {
        return getWhere {
            it.typeDeclarationEntity.id == typeDeclarationId
        }.sortedBy {
            it.position
        }
    }

}