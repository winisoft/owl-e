package com.wini.owle.dll

import com.wini.owle.dto.BaseDto
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module


abstract class BaseOwleDllTest<ID: Any, Dto: BaseDto> {

    abstract val implicatedTables: List<IdTable<*>>
    abstract val testModule: Module
    abstract val dll: DLL<ID, Dto>


    fun baseSetUp() {
        Database.connect(
            url = "jdbc:mysql://localhost:3306/owle_test_db",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "test_runner",
            password = "test1234"
        )
        stopKoin()
        startKoin { modules(testModule) }
        transaction {
            exec("SET FOREIGN_KEY_CHECKS=0;")
            SchemaUtils.drop(*implicatedTables.toTypedArray())
            exec("SET FOREIGN_KEY_CHECKS=1;")
            SchemaUtils.create(*implicatedTables.toTypedArray())
        }
    }
}