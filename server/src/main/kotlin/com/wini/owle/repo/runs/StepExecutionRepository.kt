package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class StepExecutionRepository: OwleRepoImpl<Long, StepExecutionDao>(StepExecutionDao)