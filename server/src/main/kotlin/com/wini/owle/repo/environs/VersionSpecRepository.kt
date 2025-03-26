package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class VersionSpecRepository: OwleRepoImpl<Long, VersionSpecDao>(VersionSpecDao)