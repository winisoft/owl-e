package com.wini.owle.repo.user

import com.wini.owle.entity.user.UserDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository : OwleLongIdRepoImpl<UserDao>() {

    override val entityClass: LongEntityClass<UserDao>
        get() = UserDao

}