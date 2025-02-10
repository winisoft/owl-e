package com.wini.owle.services.transforms

import com.wini.owle.dto.AppliedTransformArgDto
import com.wini.owle.entity.transforms.AppliedTransformArgDao
import com.wini.owle.mapper.transforms.AppliedTransformArgsMapper
import com.wini.owle.repo.transforms.AppliedTransformArgRepository
import com.wini.owle.services.BaseServiceImpl


class AppliedTransformArgService(
    repository: AppliedTransformArgRepository,
    mapper: AppliedTransformArgsMapper
): BaseServiceImpl<Long, AppliedTransformArgDto, AppliedTransformArgDao>(repository, mapper)