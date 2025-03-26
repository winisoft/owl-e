package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TransformationRepository: OwleRepoImpl<Long, TransformationDao>(TransformationDao)