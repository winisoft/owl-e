package com.wini.owle.services.typing

import com.wini.owle.dto.typing.TypeBound
import com.wini.owle.entity.typing.TypeBoundEntity
import com.wini.owle.mapper.typing.TypeBoundsMapper
import com.wini.owle.repo.TypeBoundIdFactory
import com.wini.owle.repo.typing.TypeBoundRepository
import com.wini.owle.services.BaseServiceImpl


class TypeBoundsService(
    repository: TypeBoundRepository,
    mapper: TypeBoundsMapper,
    entityIdFactory: TypeBoundIdFactory
): BaseServiceImpl<Long, TypeBound, TypeBoundEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)