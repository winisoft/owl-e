package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.PreReqInjectionDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class PreReqInjectionRepository : OwleLongIdRepoImpl<PreReqInjectionDao>() {

    override val entityClass: LongEntityClass<PreReqInjectionDao>
        get() = PreReqInjectionDao

}