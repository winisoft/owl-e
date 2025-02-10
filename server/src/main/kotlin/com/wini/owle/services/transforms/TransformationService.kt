package com.wini.owle.services.transforms

import com.wini.owle.dto.TransformationDto
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.mapper.transforms.TransformationsMapper
import com.wini.owle.repo.transforms.TransformationRepository
import com.wini.owle.services.BaseServiceImpl


class TransformationService(
    repository: TransformationRepository,
    mapper: TransformationsMapper
): BaseServiceImpl<Long, TransformationDto, TransformationDao>(repository, mapper)