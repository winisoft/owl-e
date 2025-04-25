package com.wini.owle.services.transforms

import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.mapper.transforms.TransformationArgsMapper
import com.wini.owle.repo.TransformationArgIdFactory
import com.wini.owle.repo.transforms.TransformationArgRepository
import com.wini.owle.services.BaseServiceImpl


class TransformationArgService(
    repository: TransformationArgRepository,
    mapper: TransformationArgsMapper,
    entityIdFactory: TransformationArgIdFactory
): BaseServiceImpl<Long, TransformArg, TransformArgEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)