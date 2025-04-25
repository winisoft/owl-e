package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.TypeUnionMemberIdFactory
import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class TypeUnionMembersMapper(
    private val daoFactory: DaoFactory<TypeUnionMemberEntity, CompositeID>,
    private val compositeIdFactory: CompositeIdFactory<TypeUnionMember> = TypeUnionMemberIdFactory
) : DbMapper<TypeUnionMember, TypeUnionMemberEntity>() {

    override fun toEntity(dto: TypeUnionMember): TypeUnionMemberEntity {
        return compositeIdFactory.fromDtoInstance(dto).let { id ->
            TypeUnionMemberEntity.findById(id)
        } ?: TypeUnionMemberEntity.create(
            unionTypeId = dto.unionTypeDeclarationId,
            memberTypeId = dto.memberTypeDeclarationId
        )
    }

    override fun toDto(entity: TypeUnionMemberEntity): TypeUnionMember {
        // Assumes TypeUnionMemberDao holds references to the actual TypeDeclarationDao instances
        return TypeUnionMember(
            unionTypeDeclarationId = entity.unionTypeDeclarationEntity.id.value,
            memberTypeDeclarationId = entity.memberTypeDeclarationEntity.id.value
        )
    }
}