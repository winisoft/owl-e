package com.wini.owle.services.environs

import com.wini.owle.dto.environs.ToolPyPackDep
import com.wini.owle.entity.environs.ToolPyPackDepEntity
import com.wini.owle.mapper.environs.ToolPackageDependencyMapper
import com.wini.owle.repo.ToolPackageDependencyIdFactory
import com.wini.owle.repo.environs.ToolPackageDependencyRepository
import com.wini.owle.services.BaseServiceImpl
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyService(
    repository: ToolPackageDependencyRepository,
    mapper: ToolPackageDependencyMapper,
    entityIdFactory: ToolPackageDependencyIdFactory
): BaseServiceImpl<CompositeID, ToolPyPackDep, ToolPyPackDepEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)