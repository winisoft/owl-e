package com.wini.owle.mapper.transforms

import com.wini.owle.dto.TransformationTargetTypeDto
import com.wini.owle.entity.TransformTargetTypes
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.transforms.TransformTargetTypeDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID


class TransformTargetTypesMapper(
    private val daoFactory: DaoFactory<TransformTargetTypeDao, CompositeID>
) : DbMapper<TransformationTargetTypeDto, TransformTargetTypeDao>() {

//    val applyProps: TransformTargetTypeDao.(TransformationTargetTypeDto) -> TransformTargetTypeDao = {
//    }

    override fun toEntity(dto: TransformationTargetTypeDto): TransformTargetTypeDao {
        return TransformTargetTypeDao.create(
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