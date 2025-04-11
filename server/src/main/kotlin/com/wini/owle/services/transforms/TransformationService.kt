package com.wini.owle.services.transforms

import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.mapper.transforms.TransformationsMapper
import com.wini.owle.repo.TransformationIdFactory
import com.wini.owle.repo.transforms.TransformationRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class TransformationService(
    repository: TransformationRepository,
    mapper: TransformationsMapper,
    entityIdFactory: TransformationIdFactory
): BaseServiceImpl<Long, TransformationDto, TransformationDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)