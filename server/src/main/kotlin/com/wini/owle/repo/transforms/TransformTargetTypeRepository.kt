package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformTargetTypeDao
import com.wini.owle.repo.OwleCompositeIdRepoImpl
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TransformTargetTypeRepository : OwleCompositeIdRepoImpl<TransformTargetTypeDao>() {

    override val entityClass: CompositeEntityClass<TransformTargetTypeDao>
        get() = TransformTargetTypeDao

}