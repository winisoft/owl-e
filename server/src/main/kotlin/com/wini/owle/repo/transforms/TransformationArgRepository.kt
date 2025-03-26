package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformationArgDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TransformationArgRepository : OwleLongIdRepoImpl<TransformationArgDao>() {

    override val entityClass: LongEntityClass<TransformationArgDao>
        get() = TransformationArgDao

}