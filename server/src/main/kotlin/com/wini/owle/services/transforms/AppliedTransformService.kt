package com.wini.owle.services.transforms

import com.wini.owle.dto.transforms.AppliedTransformDto
import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.mapper.transforms.AppliedTransformsMapper
import com.wini.owle.repo.AppliedTransformIdFactory
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.transforms.AppliedTransformRepository
import com.wini.owle.services.BaseServiceImpl


class AppliedTransformService(
    repository: AppliedTransformRepository,
    mapper: AppliedTransformsMapper,
    entityIdFactory: AppliedTransformIdFactory
): BaseServiceImpl<Long, AppliedTransformDto, AppliedTransformDao>(
    repository,
    mapper,
    entityIdFactory
)