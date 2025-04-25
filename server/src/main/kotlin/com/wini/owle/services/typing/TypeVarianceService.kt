package com.wini.owle.services.typing

import com.wini.owle.dto.typing.TypeVariance
import com.wini.owle.entity.typing.TypeVarianceEntity
import com.wini.owle.mapper.typing.TypeVariancesMapper
import com.wini.owle.repo.TypeVarianceIdFactory
import com.wini.owle.repo.typing.TypeVarianceRepository
import com.wini.owle.services.BaseServiceImpl


class TypeVarianceService(
    repository: TypeVarianceRepository,
    mapper: TypeVariancesMapper,
    entityIdFactory: TypeVarianceIdFactory
): BaseServiceImpl<Long, TypeVariance, TypeVarianceEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)