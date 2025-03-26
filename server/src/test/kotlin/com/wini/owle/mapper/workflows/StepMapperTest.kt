package com.wini.owle.mapper.workflows

import com.wini.owle.entity.DbLayerIntegrationTests.ImplicatedTables
import com.wini.owle.dto.DtoTestInstanceFactory.stepDto
import com.wini.owle.dto.DtoTestInstanceFactory.toolDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDefinitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.versionSpecDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.StepDto
import com.wini.owle.dto.UserDesignedMetadataDto
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.DateTimeConverter
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class StepMapperTest: BaseOwleMapperTest<StepDto, StepDao, StepsMapper>() {

    private val realMapper by KoinJavaComponent.inject<StepsMapper>(StepsMapper::class.java)


    override val implicatedTables: List<IdTable<*>> = ImplicatedTables.WorkflowsModule.Step

    override fun instantiateMapper(): StepsMapper { return realMapper }

    override fun Transaction.dto(id: Long): StepDto { return stepDto() }

    override fun Transaction.existingDao(): StepDao { return stepDao() }

    override fun Transaction.existingDaoDto(): Pair<StepDao, StepDto> {
        val dao = existingDao()
        val dto = dao.run {
            stepDto(
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
                tool = tool.run {
                    toolDto(
                        id = id.value,
                        name = name,
                        description = description,
                        body = body,
                        returnTypeDeclaration = returnTypeDeclaration?.run {
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
                                },
                                alias = alias
                            )
                        }
                    )
                },
                isWorkflowHead = isWorkflowHead,
                nextStep = defaultNext.run {
                    stepDto(
                        id = id.value,
                        name = name,
                        description = description,
                        workflow = workflow.run {
                            workflowDto(
                                id = id.value,
                                name = name,
                                description = description,
                                pythonVersion = pythonVersion
                            )
                        }
                    )
                }
            )
        }
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: StepDao, dto: StepDto) {
        assertEquals(dao.name, dto.name)
        assertEquals(dao.description, dto.description)
        assertEquals(dao.workflow.id.value, dto.workflow.id)
        assertEquals(dao.tool.id.value, dto.tool.id)
        assertEquals(dao.isWorkflowHead, dto.isWorkflowHead)
        assertEquals(dao.defaultNext?.id?.value, dto.nextStep?.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: StepDto, dao: StepDao) {
        assertEquals(dto.name, dao.name)
        assertEquals(dto.description, dao.description)
        assertEquals(dto.workflow.id, dao.workflow.id.value)
        assertEquals(dto.tool.id, dao.tool.id.value)
        assertEquals(dto.isWorkflowHead, dao.isWorkflowHead)
        assertEquals(dto.nextStep?.id, dao.defaultNext?.id?.value)
    }

}