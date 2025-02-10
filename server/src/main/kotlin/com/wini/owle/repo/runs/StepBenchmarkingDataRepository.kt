package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.StepBenchmarkingDataDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class StepBenchmarkingDataRepository : OwleLongIdRepoImpl<StepBenchmarkingDataDao>() {

    override val entityClass: LongEntityClass<StepBenchmarkingDataDao>
        get() = StepBenchmarkingDataDao

    override fun getWhere(predicate: (StepBenchmarkingDataDao) -> Boolean): List<StepBenchmarkingDataDao> {
        return transaction {
            StepBenchmarkingDataDao.all().filter(predicate)
        }
    }
}