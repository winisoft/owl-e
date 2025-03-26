package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.AppliedTransformArgDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class AppliedTransformArgRepository: OwleRepoImpl<Long, AppliedTransformArgDao>(AppliedTransformArgDao)