package com.wini.owle.services.transforms

import com.wini.owle.dto.AppliedTransformDto
import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.mapper.transforms.AppliedTransformsMapper
import com.wini.owle.repo.transforms.AppliedTransformRepository
import com.wini.owle.services.BaseServiceImpl


class AppliedTransformService(
    repository: AppliedTransformRepository,
    mapper: AppliedTransformsMapper
): BaseServiceImpl<Long, AppliedTransformDto, AppliedTransformDao>(repository, mapper)