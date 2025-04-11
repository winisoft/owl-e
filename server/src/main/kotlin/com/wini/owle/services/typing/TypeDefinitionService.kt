package com.wini.owle.services.typing

import com.wini.owle.dto.typing.TypeDefinitionDto
import com.wini.owle.entity.typing.TypeDefinitionDao
import com.wini.owle.mapper.typing.TypeDefinitionsMapper
import com.wini.owle.repo.TypeDefinitionIdFactory
import com.wini.owle.repo.typing.TypeDefinitionRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class TypeDefinitionService(
    repository: TypeDefinitionRepository,
    mapper: TypeDefinitionsMapper,
    entityIdFactory: TypeDefinitionIdFactory
): BaseServiceImpl<Long, TypeDefinitionDto, TypeDefinitionDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)