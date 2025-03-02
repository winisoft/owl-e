package com.wini.owle.api

import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.OwleRepoTest
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.koin.core.module.Module
import org.koin.test.KoinTest


interface OwleRepoTest<ID: Any, T>: KoinTest where T : Entity<ID> {
    val implicatedTables: List<IdTable<*>>
    val testModule: Module
    fun getInstance(): T
    fun getList(): List<T>
    fun makeEntityID(id: ID): EntityID<ID>
    fun getRepo(): OwleRepo<ID, T>
    fun modifyEntity(entity: T): T
    fun modifyEntities(entities: List<T>): List<T>
}