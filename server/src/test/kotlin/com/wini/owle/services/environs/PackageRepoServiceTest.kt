package com.wini.owle.services.environs

import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.dto.environs.PackageRepoTypeDto
import com.wini.owle.entity.environs.PackageRepoType
import com.wini.owle.entity.PackageRepositories
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.services.OwleServiceUnitTest
import com.wini.owle.services.Service
import io.mockk.MockKMatcherScope
import io.mockk.every
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.java.KoinJavaComponent.getKoin
import kotlin.reflect.KClass


open class PackageRepoServiceTest: OwleServiceUnitTest<Long, PackageRepoDto, PackageRepoDao>() {

    override val service: Service<Long, PackageRepoDto>
        get() = getKoin().get<PackageRepoService>()

    override val entityClass: KClass<PackageRepoDao>
        get() = PackageRepoDao::class

    override val tableName: String
        get() = PackageRepositories.tableName

    override fun createSampleDto(): PackageRepoDto {
        return PackageRepoDto(
            id = 1L,
            sourceType = PackageRepoTypeDto.Pypi,
            sourceUrl = "https://repo.example.com"
        )
    }

    override fun createSampleEntity(): PackageRepoDao {
        return transaction {
            PackageRepoDao.new(1L) {
                sourceType = PackageRepoType.PYPI
                sourceUrl = "https://repo.example.com"
            }
        }
    }

    override fun createEntityId(id: Long): EntityID<Long> {
        return EntityID(id, PackageRepositories)
    }

    override fun extractEntityId(entity: PackageRepoDao): Long {
        return entity.id.value
    }

    override fun extractDtoId(dto: PackageRepoDto): Long {
        return dto.id
    }

    override fun setupMocks()  {
        // Setup mapper mocks - now much simpler with regular methods
        every { mapper.toEntity(anyDto()) } returns createSampleEntity()
        every { mapper.toDto(anyEntity()) } returns createSampleDto()
        every { mapper.toEntities(listOf(anyDto())) } returns listOf(createSampleEntity())
        every { mapper.toDtos(listOf(anyEntity())) } returns listOf(createSampleDto())
    }

    override fun MockKMatcherScope.anyDto(): PackageRepoDto {
        return any<PackageRepoDto>()
    }

    override fun MockKMatcherScope.anyEntity(): PackageRepoDao {
        return any<PackageRepoDao>()
    }

    override fun getInstance(): PackageRepoDto {
        return createSampleDto()
    }

}