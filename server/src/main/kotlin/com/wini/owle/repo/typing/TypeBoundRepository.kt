package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeBoundDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TypeBoundRepository: OwleRepoImpl<Long, TypeBoundDao>(TypeBoundDao)