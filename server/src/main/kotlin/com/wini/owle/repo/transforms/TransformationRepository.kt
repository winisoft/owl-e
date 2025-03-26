package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TransformationRepository : OwleLongIdRepoImpl<TransformationDao>() {

    override val entityClass: LongEntityClass<TransformationDao>
        get() = TransformationDao

}