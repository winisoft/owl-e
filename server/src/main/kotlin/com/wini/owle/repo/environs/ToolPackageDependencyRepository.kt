package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.ToolPyPackDepEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyRepository: OwleRepoImpl<CompositeID, ToolPyPackDepEntity>(ToolPyPackDepEntity)