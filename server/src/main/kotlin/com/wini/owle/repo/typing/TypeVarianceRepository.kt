package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeVarianceDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TypeVarianceRepository : OwleLongIdRepoImpl<TypeVarianceDao>() {

    override val entityClass: LongEntityClass<TypeVarianceDao>
        get() = TypeVarianceDao

}