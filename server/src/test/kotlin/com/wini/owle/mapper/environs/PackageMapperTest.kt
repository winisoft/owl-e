package com.wini.owle.mapper.environs

import com.wini.owle.TestHelper
import com.wini.owle.dto.PackageDto
import com.wini.owle.dto.VersionSpecDto
import com.wini.owle.entity.Packages
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.environs.PackageDao
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.mapperModule
import com.wini.owle.repo.environs.PackageRepoRepository
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertEquals



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class PackageMapperTest: BaseOwleMapperTest<PackageDto, PackageDao, PackagesMapper>() {

    private val realMapper by KoinJavaComponent.inject<PackagesMapper>(PackagesMapper::class.java)

    override val implicatedTables: List<IdTable<*>> = TestHelper.ImplicatedTables.EnvironsModule.Package

    override fun instantiateMapper(): PackagesMapper {
        return realMapper
    }

    override fun Transaction.dto(id: Long): PackageDto {
        return PackageDto(
            id = id,
            name = "TestPackage",
            disambiguation = "test",
            versionSpec = VersionSpecDto(
                id = 0L,
                versionMin = "1.0.0",
                versionMax = "2.0.0",
                versionExact = null
            )
        )
    }

    override fun Transaction.existingDao(): PackageDao {
        return PackageDao.new {
            this.name = "TestPackage"
            this.disambiguation = "test"
            this.versionSpec = VersionSpecDao.new {
                this.versionMin = "1.0.0"
                this.versionMax = "2.0.0"
                this.versionExact = null
            }
        }
    }

    override fun Transaction.existingDaoDto(): Pair<PackageDao, PackageDto> {
        val premiseDao = existingDao()
        val premiseDto = PackageDto(
            id = premiseDao.id.value,
            name = premiseDao.name,
            disambiguation = premiseDao.disambiguation,
            versionSpec = VersionSpecDto(
                id = premiseDao.versionSpec.id.value,
                versionMin = premiseDao.versionSpec.versionMin,
                versionMax = premiseDao.versionSpec.versionMax,
                versionExact = premiseDao.versionSpec.versionExact
            )
        )

        return premiseDao to premiseDto
    }

    override fun assertDtoPropsExpectedMatch(dto: PackageDto, dao: PackageDao) {
        assertEquals(dto.name, dao.name)
        assertEquals(dto.disambiguation, dao.disambiguation)
        assertEquals(dto.versionSpec.versionMin, dao.versionSpec.versionMin)
        assertEquals(dto.versionSpec.versionMax, dao.versionSpec.versionMax)
        assertEquals(dto.versionSpec.versionExact, dao.versionSpec.versionExact)
    }

    override fun assertDaoPropsExpectedMatch(dao: PackageDao, dto: PackageDto) {
        assertEquals(dao.id.value, dto.id)
        assertEquals(dao.name, dto.name)
        assertEquals(dao.disambiguation, dto.disambiguation)
        assertEquals(dao.id.value, dto.versionSpec.id)
        assertEquals(dao.versionSpec.versionMin, dto.versionSpec.versionMin)
        assertEquals(dao.versionSpec.versionMax, dto.versionSpec.versionMax)
        assertEquals(dao.versionSpec.versionExact, dto.versionSpec.versionExact)
    }
}
