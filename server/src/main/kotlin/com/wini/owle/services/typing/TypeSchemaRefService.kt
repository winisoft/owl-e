package com.wini.owle.services.typing

import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.entity.typing.TypeSchemaRefEntity
import com.wini.owle.mapper.typing.TypeSchemaRefsMapper
import com.wini.owle.repo.TypeSchemaRefIdFactory
import com.wini.owle.repo.typing.TypeSchemaRefRepository
import com.wini.owle.services.BaseServiceImpl


class TypeSchemaRefService(
    repository: TypeSchemaRefRepository,
    mapper: TypeSchemaRefsMapper,
    entityIdFactory: TypeSchemaRefIdFactory
): BaseServiceImpl<Long, TypeSchemaRef, TypeSchemaRefEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)