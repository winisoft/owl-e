package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class TypeUnionMemberMapper(
    private val daoDelegate: DaoDelegate<CompositeID, TypeUnionMember, TypeUnionMemberEntity>,
    private val typeNodeMapper: TypeNodeMapper
): DbMapper<CompositeID, TypeUnionMember, TypeUnionMemberEntity> {

    override fun toEntity(dto: TypeUnionMember): TypeUnionMemberEntity {
        return daoDelegate.findOrCreate(dto) {
            unionTypeNode = typeNodeMapper.toEntity(dto.unionTypeNode)
            memberTypeNode = typeNodeMapper.toEntity(dto.memberTypeNode)
        }
    }

    override fun toDto(entity: TypeUnionMemberEntity): TypeUnionMember {
        return TypeUnionMember(
            unionTypeNode = typeNodeMapper.toDto(entity.unionTypeNode),
            memberTypeNode = typeNodeMapper.toDto(entity.memberTypeNode)
        )
    }
}