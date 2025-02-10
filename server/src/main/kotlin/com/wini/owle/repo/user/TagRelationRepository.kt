package com.wini.owle.repo.user

import com.wini.owle.entity.user.TagRelationDao
import com.wini.owle.repo.OwleCompositeIdRepoImpl
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TagRelationRepository : OwleCompositeIdRepoImpl<TagRelationDao>() {

    override val entityClass: CompositeEntityClass<TagRelationDao>
        get() = TagRelationDao

    override fun getWhere(predicate: (TagRelationDao) -> Boolean): List<TagRelationDao> {
        return transaction {
            TagRelationDao.all().filter(predicate)
        }
    }
}