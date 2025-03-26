package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformationArgDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TransformationArgRepository: OwleRepoImpl<Long, TransformationArgDao>(TransformationArgDao)