package com.wini.owle.services.transforms

import com.wini.owle.dto.transforms.Transform
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.mapper.transforms.TransformationsMapper
import com.wini.owle.repo.TransformationIdFactory
import com.wini.owle.repo.transforms.TransformationRepository
import com.wini.owle.services.BaseServiceImpl


class TransformationService(
    repository: TransformationRepository,
    mapper: TransformationsMapper,
    entityIdFactory: TransformationIdFactory
): BaseServiceImpl<Long, Transform, TransformEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)