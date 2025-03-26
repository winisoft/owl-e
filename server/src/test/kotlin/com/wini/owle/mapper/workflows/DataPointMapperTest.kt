package com.wini.owle.mapper.workflows

import com.wini.owle.entity.DbLayerIntegrationTests.ImplicatedTables
import com.wini.owle.dto.DataPointDto
import com.wini.owle.dto.DtoTestInstanceFactory.dataPointDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDefinitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.versionSpecDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.UserDesignedMetadataDto
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.dataPointDao
import com.wini.owle.entity.StoreMethod.Companion.toDto
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.DateTimeConverter
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class DataPointMapperTest: BaseOwleMapperTest<DataPointDto, DataPointDao, DataPointsMapper>() {

    private val realMapper by KoinJavaComponent.inject<DataPointsMapper>(DataPointsMapper::class.java)


    override val implicatedTables: List<IdTable<*>> = ImplicatedTables.WorkflowsModule.DataPoint

    override fun instantiateMapper(): DataPointsMapper {
        return realMapper
    }

    override fun Transaction.dto(id: Long): DataPointDto {
        return dataPointDto()
    }

    override fun Transaction.existingDao(): DataPointDao {
        return dataPointDao()
    }

    override fun Transaction.existingDaoDto(): Pair<DataPointDao, DataPointDto> {
        val dao = existingDao()
        val dto = dao.run {
            DataPointDto(
                id = id.value,
                name = name,
                description = description,
                workflow = workflow.run {
                    workflowDto(
                        id = id.value,
                        name = name,
                        description = description,
                        pythonVersion = pythonVersion,
                        parentFolder = null,
                        metadata = UserDesignedMetadataDto.mock,
                        createdAt = DateTimeConverter.toString(createdAt),
                        updatedAt = DateTimeConverter.toString(updatedAt)
                    )
                },
                presetValue = presetValue,
                storeMethod = storeMethod.toDto(),
                typeDeclaration = typeDeclaration.run {
                    typeDeclarationDto(
                        id = id.value,
                        typeDefinition = typeDefinition.run {
                            typeDefinitionDto(
                                id = id.value,
                                name = name,
                                namespace = namespace,
                                versionSpec = versionSpec.run {
                                    versionSpecDto(
                                        id = id.value,
                                        versionMin = versionMin,
                                        versionMax = versionMax,
                                        versionExact = versionExact
                                    )
                                }
                            )
                        }
                    )
                },
                createdAt = TODO(),
                updatedAt = TODO()
            )
        }
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: DataPointDao, dto: DataPointDto) {
        assertEquals(dao.name, dto.name)
        assertEquals(dao.description, dto.description)
        assertEquals(dao.typeDeclaration, dto.typeDeclaration)
    }

    override fun assertDtoPropsExpectedMatch(dto: DataPointDto, dao: DataPointDao) {
        assertEquals(dto.name, dao.name)
        assertEquals(dto.description, dao.description)
        assertEquals(dto.typeDeclaration, dao.typeDeclaration)
    }


}