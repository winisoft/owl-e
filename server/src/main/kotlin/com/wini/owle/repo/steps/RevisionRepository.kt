package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.RevisionDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class RevisionRepository: OwleRepoImpl<Long, RevisionDao>(RevisionDao)