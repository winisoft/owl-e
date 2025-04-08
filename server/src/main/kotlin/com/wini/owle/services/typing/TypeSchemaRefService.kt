package com.wini.owle.services.typing

import com.wini.owle.dto.typing.TypeSchemaRefDto
import com.wini.owle.entity.typing.TypeSchemaRefDao
import com.wini.owle.mapper.typing.TypeSchemaRefsMapper
import com.wini.owle.repo.TypeSchemaRefIdFactory
import com.wini.owle.repo.typing.TypeSchemaRefRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class TypeSchemaRefService(
    repository: TypeSchemaRefRepository,
    mapper: TypeSchemaRefsMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, TypeSchemaRefDto, TypeSchemaRefDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)