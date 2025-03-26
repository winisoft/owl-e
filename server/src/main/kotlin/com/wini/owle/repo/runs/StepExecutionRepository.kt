package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class StepExecutionRepository : OwleLongIdRepoImpl<StepExecutionDao>() {

    override val entityClass: LongEntityClass<StepExecutionDao>
        get() = StepExecutionDao

}