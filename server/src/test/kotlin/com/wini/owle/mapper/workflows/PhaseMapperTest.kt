package com.wini.owle.mapper.workflows

import com.wini.owle.dto.DtoTestInstanceFactory.phaseDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.PhaseDto
import com.wini.owle.dto.WorkflowDto
import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.workflows.PhaseDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.BaseMapperUnitTest
import com.wini.owle.models.metadata.UserDesignedMetadata
import io.mockk.*
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class PhaseMapperTest: BaseMapperUnitTest<PhaseDto, PhaseDao, PhasesMapper>() {

    private val workflowMapper: WorkflowsMapper = mockk()
    private lateinit var mockWorkflowDto: WorkflowDto
    private lateinit var mockWorkflowEntity: WorkflowDao
    
    override fun createMapperWithMocks(): PhasesMapper {
        // Setup workflow mocks
        mockWorkflowDto = workflowDto(id = 1)
        mockWorkflowEntity = EntityTestInstanceFactory.workflowDao(id=1)
        
        // Mock workflow mapper behavior
        every { workflowMapper.run { any<WorkflowDto>().toEntity() } } returns mockWorkflowEntity
        every { workflowMapper.run { any<WorkflowDao>().toDto() } } returns mockWorkflowDto
        
        return PhasesMapper(workflowMapper)
    }
    
    override fun createNewDto(): PhaseDto {
        return phaseDto(id = 0, workflow = mockWorkflowDto)
    }
    
    override fun createExistingDto(id: Long): PhaseDto {
        return phaseDto(id = id, workflow = mockWorkflowDto)
    }

    override fun createMockEntity(id: Long): PhaseDao {
        return EntityTestInstanceFactory.phaseDao(id=id, workflow = mockWorkflowEntity)
    }

    override fun setupMockForNewEntity(dto: PhaseDto, entity: PhaseDao) {
        mockkObject(PhaseDao.Companion)
        every {
            PhaseDao.new(captureLambda<PhaseDao.() -> Unit>())
        } answers {
            val mockEntity = createMockEntity(0)
            with(mockEntity) {
                lambda<PhaseDao.() -> Unit>().invoke(this)
            }
            mockEntity
        }
    }
    
    override fun setupMockForExistingEntity(dto: PhaseDto, entity: PhaseDao) {
        // Mock PhaseDao.findById
        mockkObject(PhaseDao.Companion)
        every { PhaseDao.findById(dto.id) } returns entity
    }
    
    override fun setupMockForNonExistentEntity(dto: PhaseDto) {
        // Mock PhaseDao.findById returning null
        mockkObject(PhaseDao.Companion)
        every { PhaseDao.findById(dto.id) } returns null
    }
    
    override fun assertEntityMatchesDto(entity: PhaseDao, dto: PhaseDto) {
        assertEquals(dto.name, entity.name)
        assertEquals(dto.description, entity.description)
        assertEquals(dto.displayOrder, entity.displayOrder)
    }
    
    override fun assertDtoMatchesEntity(dto: PhaseDto, entity: PhaseDao) {
        assertEquals(entity.name, dto.name)
        assertEquals(entity.description, dto.description)
        assertEquals(entity.id.value, dto.id)
    }
}