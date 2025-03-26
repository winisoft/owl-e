package com.wini.owle.dll

import com.wini.owle.TestHelper
import com.wini.owle.dto.BaseDto
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest


interface BaseOwleDllTest: KoinTest {

    val implicatedTables: List<IdTable<*>>
    val testModule: Module

    fun baseSetUp() {
        TestHelper.connectToTestDb()
        stopKoin()
        startKoin { modules(testModule) }
        TestHelper.refreshTestDbData(implicatedTables)
    }
}