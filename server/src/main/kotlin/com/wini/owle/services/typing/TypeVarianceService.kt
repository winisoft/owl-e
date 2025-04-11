package com.wini.owle.services.typing

import com.wini.owle.dto.typing.TypeVarianceDto
import com.wini.owle.entity.typing.TypeVarianceDao
import com.wini.owle.mapper.typing.TypeVariancesMapper
import com.wini.owle.repo.TypeVarianceIdFactory
import com.wini.owle.repo.typing.TypeVarianceRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class TypeVarianceService(
    repository: TypeVarianceRepository,
    mapper: TypeVariancesMapper,
    entityIdFactory: TypeVarianceIdFactory
): BaseServiceImpl<Long, TypeVarianceDto, TypeVarianceDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)