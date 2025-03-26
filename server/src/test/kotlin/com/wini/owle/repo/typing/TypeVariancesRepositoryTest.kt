package com.wini.owle.repo.typing

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.typeVarianceDao
import com.wini.owle.entity.typing.TypeVarianceDao
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent

import com.wini.owle.repo.UnconstrainedDeleteRepoTest


open class TypeVariancesRepositoryTest : DbLayerIntegrationTests<Long, TypeVarianceDao>() {

    private val realRepo by KoinJavaComponent.inject<TypeVarianceRepository>(TypeVarianceRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = listOf(TypeVariances, TypeDeclarations, TypeDefinitions, VersionSpecs)

    override val testModule: Module
        get() = module {
            single<TypeVarianceRepository> { TypeVarianceRepository() }
        }

    override fun getInstance(): TypeVarianceDao {
        return typeVarianceDao()
    }

}