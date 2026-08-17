package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.CallableParameter
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.typing.CallableParameterEntity
import com.wini.owle.mapper.DbMapper


class CallableParameterMapper(
    private val daoDelegate: DaoDelegate<ULong, CallableParameter, CallableParameterEntity>,
    private val signatureMapper: CallableSignatureMapper,
    private val typeNodeMapper: TypeNodeMapper
): DbMapper<ULong, CallableParameter, CallableParameterEntity> {

    override fun toEntity(dto: CallableParameter): CallableParameterEntity {
        return daoDelegate.findOrCreate(dto) {
            signature = signatureMapper .toEntity(dto.signature)
            paramTypeNode = typeNodeMapper.toEntity(dto.paramTypeNode)
            position = dto.position
            paramName = dto.paramName
            isVarArg = dto.isVarArg
            isKArg = dto.isKwarg
            defaultValueText = dto.defaultValueText
        }
    }

    override fun toDto(entity: CallableParameterEntity): CallableParameter {
        return CallableParameter(
            id = entity.id.value,
            signature = signatureMapper.toDto(entity.signature),
            paramTypeNode = typeNodeMapper.toDto(entity.paramTypeNode),
            position = entity.position,
            paramName = entity.paramName,
            isVarArg = entity.isVarArg,
            isKArg = entity.isKArg,
            defaultValueText = entity.defaultValueText
        )
    }
}