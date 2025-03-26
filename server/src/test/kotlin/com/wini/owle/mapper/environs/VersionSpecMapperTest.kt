package com.wini.owle.mapper.environs

import com.wini.owle.entity.DbLayerIntegrationTests.ImplicatedTables
import com.wini.owle.dto.DtoTestInstanceFactory.versionSpecDto
import com.wini.owle.dto.VersionSpecDto
import com.wini.owle.entity.DbLayerIntegrationTests
import com.wini.owle.entity.EntityTestInstanceFactory.versionSpecDao
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.mapperModule
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class VersionSpecMapperTest: BaseOwleMapperTest<VersionSpecDto, VersionSpecDao, VersionSpecsMapper>() {

    private val realMapper by KoinJavaComponent.inject<VersionSpecsMapper>(VersionSpecsMapper::class.java)

    override val implicatedTables = DbLayerIntegrationTests.ImplicatedTables.EnvironsModule.VersionSpec

    override fun instantiateMapper(): VersionSpecsMapper { return realMapper }

    override fun Transaction.dto(id: Long): VersionSpecDto {
        return versionSpecDto(id = id, versionMin = "1.0.0", versionMax = "2.0.0", versionExact = null)
    }

    override fun Transaction.existingDao(): VersionSpecDao {
        return versionSpecDao(
            versionMin = "1.0.0",
            versionMax = "2.0.0",
            versionExact = null
        )
    }

    override fun Transaction.existingDaoDto(): Pair<VersionSpecDao, VersionSpecDto> {
        val dao = existingDao()
        val dto = VersionSpecDto(
            id = dao.id.value,
            versionMin = dao.versionMin,
            versionMax = dao.versionMax,
            versionExact = dao.versionExact
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: VersionSpecDao, dto: VersionSpecDto) {
        assertEquals(dao.versionMin, dto.versionMin)
        assertEquals(dao.versionMax, dto.versionMax)
        assertEquals(dao.versionExact, dto.versionExact)
    }

    override fun assertDtoPropsExpectedMatch(dto: VersionSpecDto, dao: VersionSpecDao) {
        assertEquals(dto.versionMin, dao.versionMin)
        assertEquals(dto.versionMax, dao.versionMax)
        assertEquals(dto.versionExact, dao.versionExact)
    }


}

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class VersionSpecsMapperTest: BaseOwleMapperTest<VersionSpecDto, VersionSpecDao>() {
//
//    private val mapper = VersionSpecsMapper()
//
//    override val implicatedTables: List<IdTable<*>>
//        get() = listOf(VersionSpecs)
//
//    @Test
//    fun `test minimal valid data to entity`() {
//        transaction {
//            // A VersionSpecDto with no optional fields set
//            val intendedDao = VersionSpecDao.new {
//                versionMin = null
//                versionMax = null
//                versionExact = null
//            }
//            val intendedId = intendedDao.id.value
//            val dto = VersionSpecDto(
//                id = intendedId,
//                versionMin = null,
//                versionMax = null,
//                versionExact = null
//            )
//
//            // Convert to DAO
//            val dao = mapper.run { dto.toEntity() }
//
//            // Insert into DB (Exposed automatically does this on flush,
//            // but we can check values right away)
//            assertEquals(intendedId, dao.id.value)
//            assertNull(dao.versionMin)
//            assertNull(dao.versionMax)
//            assertNull(dao.versionExact)
//        }
//    }
//
//    @Test
//    fun `test minimal valid data to dto`() {
//        transaction {
//            // Create a VersionSpecDao in DB
//            val dao = VersionSpecDao.new {
//                this.versionMin = "1.0.0"
//                this.versionMax = "2.0.0"
//                this.versionExact = null
//            }
//
//            // Convert to Dto
//            val dto = mapper.run { dao.toDto() }
//            assertEquals(dao.id.value, dto.id)
//            assertEquals("1.0.0", dto.versionMin)
//            assertEquals("2.0.0", dto.versionMax)
//            assertNull(dto.versionExact)
//        }
//    }
//
//    @Test
//    fun `test round trip`() {
//        transaction {
//            // Original Dto
//            val originalDto = VersionSpecDto(
//                id = 42L,
//                versionMin = "0.9.0",
//                versionMax = "1.5.0",
//                versionExact = "1.2.3"
//            )
//
//            // 1) Convert Dto → Dao
//            val dao = mapper.run { originalDto.toEntity() }
//
//            // 2) Convert Dao → Dto
//            val roundTrippedDto = mapper.run { dao.toDto() }
//
//            // 3) Assert equality
//            assertEquals(originalDto.id, roundTrippedDto.id)
//            assertEquals(originalDto.versionMin, roundTrippedDto.versionMin)
//            assertEquals(originalDto.versionMax, roundTrippedDto.versionMax)
//            assertEquals(originalDto.versionExact, roundTrippedDto.versionExact)
//        }
//    }
//
//    @Test
//    fun `test null optional fields`() {
//        transaction {
//            // Some fields are explicitly set to null
//            val intended = VersionSpecDao.new {
//                versionMin = null
//                versionMax = "9.9.9"
//                versionExact = null
//            }
//
//            val dto = VersionSpecDto(
//                id = intended.id.value,
//                versionMin = null,
//                versionMax = "9.9.9",
//                versionExact = null
//            )
//
//            val dao = mapper.run { dto.toEntity() }
//            // Check that Exposed sees them as null / not-null
//            assertNull(dao.versionMin)
//            assertEquals("9.9.9", dao.versionMax)
//            assertNull(dao.versionExact)
//        }
//    }
//}
