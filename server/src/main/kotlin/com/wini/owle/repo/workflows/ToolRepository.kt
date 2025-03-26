package com.wini.owle.repo.workflows

import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class ToolRepository: OwleRepoImpl<Long, ToolDao>(ToolDao)