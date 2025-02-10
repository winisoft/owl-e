package com.wini.owle.mapper.transforms

import com.wini.owle.dto.TransformationTargetTypeDto
import com.wini.owle.entity.TransformTargetTypes
import com.wini.owle.entity.transforms.TransformTargetTypeDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID


class TransformTargetTypesMapper : DbMapper<TransformationTargetTypeDto, TransformTargetTypeDao>() {

    override val applyProps: TransformTargetTypeDao.(TransformationTargetTypeDto) -> Unit = {}

    override fun TransformationTargetTypeDto.toEntity(): TransformTargetTypeDao {
        return TransformTargetTypeDao.create(
            transformationId = transformationId,
            typeDeclarationId = typeDeclarationId
        )
    }

    override fun TransformTargetTypeDao.toDto(): TransformationTargetTypeDto {
        return TransformationTargetTypeDto(
            transformationId = transformation.id.value,
            typeDeclarationId = eligibleTypeDeclaration.id.value
        )
    }
}