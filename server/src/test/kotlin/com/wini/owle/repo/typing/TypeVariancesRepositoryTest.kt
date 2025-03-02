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

open class TypeVariancesRepositoryTest : UnconstrainedDeleteRepoTest<Long, TypeVarianceDao>() {
    private val realRepo by KoinJavaComponent.inject<TypeVarianceRepository>(TypeVarianceRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<TypeVarianceDao>): List<TypeVarianceDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    varianceCardinality = VarianceCardinality.CONTRAVARIANT
                }
                get(1).apply {
                    varianceCardinality = VarianceCardinality.INVARIANT
                }
            }
        }
    }

    override fun modifyEntity(entity: TypeVarianceDao): TypeVarianceDao {
        return transaction {
            entity.apply {
                varianceCardinality = VarianceCardinality.CONTRAVARIANT
            }
        }
    }

    override val implicatedTables = listOf(TypeVariances, TypeDeclarations, TypeDefinitions, VersionSpecs)

    override val testModule: Module
        get() = module {
            single<TypeVarianceRepository> { TypeVarianceRepository() }
        }

    override fun getInstance(): TypeVarianceDao {
        return typeVarianceDao()
    }

    override fun getList(): List<TypeVarianceDao> {
        return listOf(typeVarianceDao(), typeVarianceDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TypeVariances)
    }


}