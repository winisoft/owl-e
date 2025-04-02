package com.wini.owle.services.environs

import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.entity.environs.ToolPackageDependencyDao
import com.wini.owle.mapper.environs.ToolPackageDependencyMapper
import com.wini.owle.repo.environs.ToolPackageDependencyRepository
import com.wini.owle.services.BaseServiceImpl
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyService(
    repository: ToolPackageDependencyRepository,
    mapper: ToolPackageDependencyMapper,
): BaseServiceImpl<CompositeID, ToolPackageDependencyDto, ToolPackageDependencyDao>(repository, mapper)