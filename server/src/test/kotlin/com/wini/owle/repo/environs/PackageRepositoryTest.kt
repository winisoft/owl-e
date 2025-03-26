package com.wini.owle.repo.environs

import com.wini.owle.entity.DbLayerIntegrationTests.ImplicatedTables
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.packageDao
import com.wini.owle.entity.environs.PackageDao
import org.jetbrains.exposed.dao.id.EntityID
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


open class PackageRepositoryTest: DbLayerIntegrationTests<Long, PackageDao>() {

    private val realRepo by KoinJavaComponent.inject<PackageRepository>(PackageRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = ImplicatedTables.EnvironsModule.Package

    override val testModule: Module
        get() = module {
            single<PackageRepository> { PackageRepository() }
        }

    override fun getInstance(): PackageDao {
        return packageDao()
    }

}