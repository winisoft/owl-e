package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeUnionMemberDao
import com.wini.owle.entity.typing.TypeVarianceDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TypeVarianceRepository: OwleRepoImpl<Long, TypeVarianceDao>(TypeVarianceDao)