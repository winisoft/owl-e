package com.wini.owle.services.transforms

import com.wini.owle.dto.TransformationArgDto
import com.wini.owle.entity.transforms.TransformationArgDao
import com.wini.owle.mapper.transforms.TransformationArgsMapper
import com.wini.owle.repo.transforms.TransformationArgRepository
import com.wini.owle.services.BaseServiceImpl


class TransformationArgService(
    repository: TransformationArgRepository,
    mapper: TransformationArgsMapper
): BaseServiceImpl<Long, TransformationArgDto, TransformationArgDao>(repository, mapper)