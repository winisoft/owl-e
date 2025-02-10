package com.wini.owle.mapper

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.PackageDto
import com.wini.owle.dto.VersionSpecDto
import com.wini.owle.entity.PackageRepositories
import com.wini.owle.entity.Packages
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.environs.PackageDao
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.mapper.environs.PackagesMapper
import com.wini.owle.mapper.environs.VersionSpecsMapper
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.jetbrains.exposed.sql.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseOwleMapperTest<Dto: BaseDto, T: Entity<Long>, M : DbMapper<Dto, T>>: KoinTest {

    /**
     * List of Exposed tables that this test manipulates.
     */
    abstract val implicatedTables: List<IdTable<*>>

    /**
     * Mapper S.U.T.
     */
    private lateinit var mapper: M

    protected val testModule: Module get() = module { includes(mapperModule) }

    @BeforeEach
    fun setUp() {
        stopKoin()
        startKoin { modules(testModule) }
        Database.connect(
            url = "jdbc:mysql://localhost:3306/owle_test_db",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "test_runner",
            password = "test1234"
        )
        transaction {
            exec("SET FOREIGN_KEY_CHECKS=0;")
            SchemaUtils.drop(*implicatedTables.toTypedArray())
            exec("SET FOREIGN_KEY_CHECKS=1;")
            SchemaUtils.create(*implicatedTables.toTypedArray())
        }
        mapper = instantiateMapper()
    }

    abstract fun instantiateMapper(): M

    abstract fun Transaction.dto(id: Long): Dto

    abstract fun Transaction.existingDao(): T

    abstract fun Transaction.existingDaoDto(): Pair<T, Dto>

    abstract fun assertDtoPropsExpectedMatch(dto: Dto, dao: T)

    abstract fun assertDaoPropsExpectedMatch(dao: T, dto: Dto)

    @Test
    @DisplayName("Maps new Dto to Entity")
    fun `should map new Dto to Dao`() {
        transaction {
            val newDto = dto(0L)
            val dao = mapper.run { newDto.toEntity() }
            assertDtoPropsExpectedMatch(newDto, dao)
        }
    }

    @Test
    @DisplayName("Maps existing Dto to Entity")
    fun `should map existing Dto to Dao`() {
        transaction {
            val (premiseDao, premiseDto) = existingDaoDto()
            val mappedDao = mapper.run { premiseDto.toEntity() }
            assertEquals(premiseDao, mappedDao)
        }
    }

    @Test
    @DisplayName("Maps Entity to Dto")
    fun `should map Dao to Dto`() {
        transaction {
            val existingDao = existingDao()
            val dto = mapper.run { existingDao.toDto() }
            assertDaoPropsExpectedMatch(existingDao, dto)
        }
    }

    @Test
    @DisplayName("Fails mapping non-existent Entity")
    fun `should throw exception when mapping to non-existent entity`() {
        transaction {
            val notRealDto = dto(9999L)

            assertFailsWith<IllegalStateException> {
                transaction { mapper.run { notRealDto.toEntity() } }
            }
        }
    }

}