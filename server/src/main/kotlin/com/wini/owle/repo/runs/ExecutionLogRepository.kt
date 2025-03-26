package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.ExecutionLogDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class ExecutionLogRepository: OwleRepoImpl<Long, ExecutionLogDao>(ExecutionLogDao)