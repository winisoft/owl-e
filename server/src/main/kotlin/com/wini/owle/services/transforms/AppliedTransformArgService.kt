package com.wini.owle.services.transforms

import com.wini.owle.dto.transforms.AppliedTransformArgDto
import com.wini.owle.entity.transforms.AppliedTransformArgDao
import com.wini.owle.mapper.transforms.AppliedTransformArgsMapper
import com.wini.owle.repo.AppliedTransformArgIdFactory
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.transforms.AppliedTransformArgRepository
import com.wini.owle.services.BaseServiceImpl


class AppliedTransformArgService(
    repository: AppliedTransformArgRepository,
    mapper: AppliedTransformArgsMapper,
    entityIdFactory: AppliedTransformArgIdFactory
): BaseServiceImpl<Long, AppliedTransformArgDto, AppliedTransformArgDao>(
    repository,
    mapper,
    entityIdFactory
)