package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.ClassInheritance
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.dto.typing.TypeVarConstraint
import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.version.VersionMapper


class TypeNodeMapper(
    private val daoDelegate: DaoDelegate<ULong, TypeNode, TypeNodeEntity>,
    private val versionMapper: VersionMapper,
    private val typeSchemaRefMapper: TypeSchemaRefMapper,
    private val typeParameterMapper: TypeParameterMapper,
    private val classInheritanceMapper: ClassInheritanceMapper,
    private val typeUnionMemberMapper: TypeUnionMemberMapper,
): DbMapper<ULong, TypeNode, TypeNodeEntity> {

    override fun toEntity(dto: TypeNode): TypeNodeEntity {
        return daoDelegate.findOrCreate(dto) {
            version = versionMapper.toEntity(dto.version)
            namespace = dto.namespace
            name = dto.name
            alias = dto.alias
            isGeneric = dto.isGeneric

//            schemaRefs = typeSchemaRefMapper.toEntity(dto.schemaRefs)
//            usedInSchemas
//            typeParameters
//            referencedAsParameter
//            variance
//            constraints
//            bounds
//            literalMembers
//            unionMemberships
//            unionRoot
//            baseClasses
//            subclasses
        }
    }

    override fun toDto(entity: TypeNodeEntity): TypeNode {
        return TypeNode(
            id = entity.id.value,
            version = versionMapper.toDto(entity.version),
            namespace = entity.namespace,
            name = entity.name,
            alias = entity.alias,
            isGeneric = entity.isGeneric
        )
    }
}