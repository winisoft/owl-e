package com.wini.owle.services.transforms

import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.mapper.transforms.TransformationsMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.TransformationArgIdFactory
import com.wini.owle.repo.TransformationIdFactory
import com.wini.owle.repo.transforms.TransformationRepository
import com.wini.owle.services.BaseServiceImpl


class TransformationService(
    repository: TransformationRepository,
    mapper: TransformationsMapper,
    entityIdFactory: TransformationIdFactory
): BaseServiceImpl<Long, TransformationDto, TransformationDao>(
    repository,
    mapper,
    entityIdFactory
)