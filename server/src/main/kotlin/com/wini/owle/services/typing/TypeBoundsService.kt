package com.wini.owle.services.typing

import com.wini.owle.dto.typing.TypeBoundsDto
import com.wini.owle.entity.typing.TypeBoundDao
import com.wini.owle.mapper.typing.TypeBoundsMapper
import com.wini.owle.repo.TypeBoundIdFactory
import com.wini.owle.repo.typing.TypeBoundRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class TypeBoundsService(
    repository: TypeBoundRepository,
    mapper: TypeBoundsMapper,
    entityIdFactory: TypeBoundIdFactory
): BaseServiceImpl<Long, TypeBoundsDto, TypeBoundDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)