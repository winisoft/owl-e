package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TestInputRepository: OwleRepoImpl<Long, TestInputDao>(TestInputDao)