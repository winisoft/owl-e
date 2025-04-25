package com.wini.owle.services.typing

import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.repo.TypeDeclarationIdFactory
import com.wini.owle.repo.typing.TypeDeclarationRepository
import com.wini.owle.services.BaseServiceImpl


class TypeDeclarationService(
    repository: TypeDeclarationRepository,
    mapper: TypeDeclarationsMapper,
    entityIdFactory: TypeDeclarationIdFactory
): BaseServiceImpl<Long, TypeDeclaration, TypeDeclarationEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)