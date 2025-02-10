package com.wini.owle.mapper.environs

import com.wini.owle.dto.PackageRepoDto
import com.wini.owle.entity.PackageRepoType.Companion.toDto
import com.wini.owle.entity.PackageRepositories
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.mapperModule
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertEquals
import com.wini.owle.dto.enums.PackageRepoType as PackageRepoTypeDto
import com.wini.owle.entity.PackageRepoType as PackageRepoTypeEntity
import org.koin.dsl.module


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class PackageRepoMapperTest: BaseOwleMapperTest<PackageRepoDto, PackageRepoDao, PackageRepoMapper>() {

    private val realMapper by KoinJavaComponent.inject<PackageRepoMapper>(PackageRepoMapper::class.java)

    override val implicatedTables: List<IdTable<*>> = listOf(PackageRepositories)

    override fun instantiateMapper(): PackageRepoMapper { return realMapper }

    override fun Transaction.dto(id: Long): PackageRepoDto {
        return PackageRepoDto(
            id = id,
            sourceType = PackageRepoTypeDto.Pypi,
            sourceUrl = "https://pypi.org/simple"
        )
    }

    override fun Transaction.existingDao(): PackageRepoDao {
        return PackageRepoDao.new {
            sourceType = PackageRepoTypeEntity.CONDA
            sourceUrl = "https://conda.io/simple"
        }
    }

    override fun Transaction.existingDaoDto(): Pair<PackageRepoDao, PackageRepoDto> {
        val dao = existingDao()
        val dto = PackageRepoDto(
            id = dao.id.value,
            sourceType = dao.sourceType.toDto(),
            sourceUrl = dao.sourceUrl
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: PackageRepoDao, dto: PackageRepoDto) {
        assertEquals(dao.id.value, dto.id)
        assertEquals(dao.sourceUrl, dto.sourceUrl)
        assertEquals(dao.sourceType.toDto(), dto.sourceType)
    }

    override fun assertDtoPropsExpectedMatch(dto: PackageRepoDto, dao: PackageRepoDao) {
        assertEquals(dto.sourceUrl, dao.sourceUrl)
        assertEquals(dto.sourceType, dao.sourceType.toDto())
    }

}
