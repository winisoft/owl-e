package com.wini.owle.services.transforms

import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.entity.transforms.TransformTargetTypeDao
import com.wini.owle.mapper.transforms.TransformTargetTypesMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.TransformTargetTypeIdFactory
import com.wini.owle.repo.transforms.TransformTargetTypeRepository
import com.wini.owle.services.BaseServiceImpl
import org.jetbrains.exposed.dao.id.CompositeID


class TransformTargetTypeService(
    repository: TransformTargetTypeRepository,
    mapper: TransformTargetTypesMapper,
    entityIdFactory: TransformTargetTypeIdFactory
): BaseServiceImpl<CompositeID, TransformationTargetTypeDto, TransformTargetTypeDao>(
    repository,
    mapper,
    entityIdFactory
)