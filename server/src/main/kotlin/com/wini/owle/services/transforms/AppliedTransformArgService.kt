package com.wini.owle.services.transforms

import com.wini.owle.dto.transforms.AppliedTransformArg
import com.wini.owle.entity.transforms.AppliedTransformArgEntity
import com.wini.owle.mapper.transforms.AppliedTransformArgsMapper
import com.wini.owle.repo.AppliedTransformArgIdFactory
import com.wini.owle.repo.transforms.AppliedTransformArgRepository
import com.wini.owle.services.BaseServiceImpl


class AppliedTransformArgService(
    repository: AppliedTransformArgRepository,
    mapper: AppliedTransformArgsMapper,
    entityIdFactory: AppliedTransformArgIdFactory
): BaseServiceImpl<Long, AppliedTransformArg, AppliedTransformArgEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)