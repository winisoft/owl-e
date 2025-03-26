package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.BenchmarkingDataDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class BenchmarkingDataRepository : OwleLongIdRepoImpl<BenchmarkingDataDao>() {

    override val entityClass: LongEntityClass<BenchmarkingDataDao>
        get() = BenchmarkingDataDao

}