package com.wini.owle.repo.user

import com.wini.owle.entity.user.TagDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TagRepository : OwleLongIdRepoImpl<TagDao>() {

    override val entityClass: LongEntityClass<TagDao>
        get() = TagDao

    override fun getWhere(predicate: (TagDao) -> Boolean): List<TagDao> {
        return transaction {
            TagDao.all().filter(predicate)
        }
    }
}