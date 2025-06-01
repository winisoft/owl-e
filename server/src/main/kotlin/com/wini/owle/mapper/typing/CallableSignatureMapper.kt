package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.CallableSignature
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.typing.CallableSignatureEntity
import com.wini.owle.mapper.DbMapper


class CallableSignatureMapper(
    private val daoDelegate: DaoDelegate<ULong, CallableSignature, CallableSignatureEntity>,
    private val typeSchemaRefMapper: TypeSchemaRefMapper
) : DbMapper<ULong, CallableSignature, CallableSignatureEntity> {

    override fun toEntity(dto: CallableSignature): CallableSignatureEntity {
        return daoDelegate.findOrCreate(dto) {
            parentSchemaRef = typeSchemaRefMapper.toEntity(dto.parentSchemaRef)
            returnTypeNode = typeSchemaRefMapper.toEntity(dto.returnTypeNode)
            isStatic = dto.isStatic
            isClassmethod = dto.isClassmethod
            docstring = dto.docstring
            name = dto.name
        }
    }

    override fun toDto(entity: CallableSignatureEntity): CallableSignature {
        return CallableSignature(
            id = entity.id.value,
            parentSchemaRef = typeSchemaRefMapper.toDto(entity.parentSchemaRef),
            returnTypeNode = typeSchemaRefMapper.toDto(entity.returnTypeNode),
            isStatic = entity.isStatic,
            isClassmethod = entity.isClassmethod,
            docstring = entity.docstring,
            name = entity.name
        )
    }
}