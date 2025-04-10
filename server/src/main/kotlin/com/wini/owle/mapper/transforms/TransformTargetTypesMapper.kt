package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.TransformTargetTypeIdFactory
import com.wini.owle.entity.composteid.TypeUnionMemberIdFactory
import com.wini.owle.entity.transforms.TransformTargetTypeDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class TransformTargetTypesMapper(
    private val daoFactory: DaoFactory<TransformTargetTypeDao, CompositeID>,
    private val compositeIdFactory: CompositeIdFactory<TransformationTargetTypeDto> = TransformTargetTypeIdFactory
) : DbMapper<TransformationTargetTypeDto, TransformTargetTypeDao>() {

    override fun toEntity(dto: TransformationTargetTypeDto): TransformTargetTypeDao {
        return compositeIdFactory.fromDtoInstance(dto).let { id ->
            TransformTargetTypeDao.findById(id)
        } ?: TransformTargetTypeDao.create(
            transformationId = dto.transformationId,
            typeDeclarationId = dto.typeDeclarationId
        )
    }

    override fun toDto(entity: TransformTargetTypeDao) : TransformationTargetTypeDto {
        return TransformationTargetTypeDto(
            transformationId = 1,
            typeDeclarationId = 1
        )
    }
}