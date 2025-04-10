package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.TypeUnionMemberIdFactory
import com.wini.owle.entity.typing.TypeUnionMemberDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class TypeUnionMembersMapper(
    private val daoFactory: DaoFactory<TypeUnionMemberDao, CompositeID>,
    private val compositeIdFactory: CompositeIdFactory<TypeUnionMemberDto> = TypeUnionMemberIdFactory
) : DbMapper<TypeUnionMemberDto, TypeUnionMemberDao>() {

    override fun toEntity(dto: TypeUnionMemberDto): TypeUnionMemberDao {
        return compositeIdFactory.fromDtoInstance(dto).let { id ->
            TypeUnionMemberDao.findById(id)
        } ?: TypeUnionMemberDao.create(
            unionTypeId = dto.unionTypeDeclarationId,
            memberTypeId = dto.memberTypeDeclarationId
        )
    }

    override fun toDto(entity: TypeUnionMemberDao): TypeUnionMemberDto {
        // Assumes TypeUnionMemberDao holds references to the actual TypeDeclarationDao instances
        return TypeUnionMemberDto(
            unionTypeDeclarationId = entity.unionTypeDeclaration.id.value,
            memberTypeDeclarationId = entity.memberTypeDeclaration.id.value
        )
    }
}