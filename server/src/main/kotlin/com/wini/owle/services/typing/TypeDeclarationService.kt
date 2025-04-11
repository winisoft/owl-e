package com.wini.owle.services.typing

import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.repo.TypeDeclarationIdFactory
import com.wini.owle.repo.typing.TypeDeclarationRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class TypeDeclarationService(
    repository: TypeDeclarationRepository,
    mapper: TypeDeclarationsMapper,
    entityIdFactory: TypeDeclarationIdFactory
): BaseServiceImpl<Long, TypeDeclarationDto, TypeDeclarationDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)