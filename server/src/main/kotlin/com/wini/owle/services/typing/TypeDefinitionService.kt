package com.wini.owle.services.typing

import com.wini.owle.dto.typing.TypeDefinition
import com.wini.owle.entity.typing.TypeDefinitionEntity
import com.wini.owle.mapper.typing.TypeDefinitionsMapper
import com.wini.owle.repo.TypeDefinitionIdFactory
import com.wini.owle.repo.typing.TypeDefinitionRepository
import com.wini.owle.services.BaseServiceImpl


class TypeDefinitionService(
    repository: TypeDefinitionRepository,
    mapper: TypeDefinitionsMapper,
    entityIdFactory: TypeDefinitionIdFactory
): BaseServiceImpl<Long, TypeDefinition, TypeDefinitionEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)