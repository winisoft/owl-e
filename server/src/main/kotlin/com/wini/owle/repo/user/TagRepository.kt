package com.wini.owle.repo.user

import com.wini.owle.entity.user.TagDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TagRepository: OwleRepoImpl<Long, TagDao>(TagDao)