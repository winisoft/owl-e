package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class AppliedTransformRepository: OwleRepoImpl<Long, AppliedTransformDao>(AppliedTransformDao)