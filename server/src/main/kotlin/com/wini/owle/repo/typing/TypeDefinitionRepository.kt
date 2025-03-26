package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeDefinitionDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction


class TypeDefinitionRepository: OwleRepoImpl<Long, TypeDefinitionDao>(TypeDefinitionDao)