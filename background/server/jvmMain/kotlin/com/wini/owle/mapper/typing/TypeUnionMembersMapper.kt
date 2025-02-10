package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeUnionMemberDto
import com.wini.owle.entity.typing.TypeUnionMemberDao
import com.wini.owle.mapper.DbMapper


class TypeUnionMembersMapper : DbMapper<TypeUnionMemberDto, TypeUnionMemberDao>() {

    override val applyProps: TypeUnionMemberDao.(TypeUnionMemberDto) -> Unit = {}

    override fun TypeUnionMemberDto.toEntity(): TypeUnionMemberDao {
        return TypeUnionMemberDao.create(
            unionTypeId = unionTypeDeclarationId,
            memberTypeId = memberTypeDeclarationId
        )
    }

    override fun TypeUnionMemberDao.toDto(): TypeUnionMemberDto {
        return TypeUnionMemberDto(
            unionTypeDeclarationId = unionTypeDeclaration.id.value,
            memberTypeDeclarationId = memberTypeDeclaration.id.value
        )
    }
}