package com.wini.owle.mapper.runs

import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class TestCaseMapperTestEntity {

    private lateinit var mapper: TestCasesMapper
    private val testInputMapper: TestInputsMapper = mockk()
    private val testExpectedOutcomeMapper: TestExpectedOutcomesMapper = mockk()
    private val testResultMapper: TestResultsMapper = mockk()
    private val usersMapper: UsersMapper = mockk()
    private val workflowsMapper: WorkflowsMapper = mockk()
    private val stepsMapper: StepsMapper = mockk()
    private val factory: DaoFactory<TestCaseEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TestCasesMapper(
            factory,
            testInputMapper,
            testExpectedOutcomeMapper,
            testResultMapper,
            usersMapper,
            workflowsMapper,
            stepsMapper
        )
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTO
        val dto = TestCase(
            id = 99L,
            user = mockk(),
            name = "Test Case",
            description = "Description",
            workflow = mockk(),
            step = mockk(),
            inputs = emptyList(),
            expectedOutcomes = emptyList(),
            results = emptyList()
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(TestCaseEntity)
        every { TestCaseEntity.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestCase with id=99 not found", exception.message)
        unmockkObject(TestCaseEntity)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<TestCaseEntity> {
            every { id.value } returns 1L
            every { userEntity } returns mockk()
            every { name } returns "Test Case"
            every { description } returns "Description"
            every { workflow } returns mockk()
            every { step } returns mockk()
        }

        // Setup mock responses from collaborators
        val user = mockk<User>()
        val workflow = mockk<Workflow>()
        val step = mockk<Step>()
        
        every { usersMapper.toDto(entity.userEntity) } returns user
        every { workflowsMapper.toDto(entity.workflow!!) } returns workflow
        every { stepsMapper.toDto(entity.step!!) } returns step

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { usersMapper.toDto(entity.userEntity) }
        verify(exactly = 1) { workflowsMapper.toDto(entity.workflow!!) }
        verify(exactly = 1) { stepsMapper.toDto(entity.step!!) }

        // Verify result contains values from collaborators
        assertEquals(user, result.user)
        assertEquals(workflow, result.workflow)
        assertEquals(step, result.step)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
        assertEquals(entity.name, result.name)
        assertEquals(entity.description, result.description)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = TestCase(
            id = 42L,
            user = mockk(),
            name = "Updated Test Case",
            description = "Updated Description",
            workflow = mockk(),
            step = mockk(),
            inputs = emptyList(),
            expectedOutcomes = emptyList(),
            results = emptyList()
        )

        // Create mock existing entity to update
        val existingEntity = mockk<TestCaseEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val userEntity = mockk<UserEntity>()
        val workflowEntity = mockk<WorkflowEntity>()
        val stepEntity = mockk<StepEntity>()
        
        every { usersMapper.toEntity(dto.user) } returns userEntity
        every { workflowsMapper.toEntity(dto.workflow!!) } returns workflowEntity
        every { stepsMapper.toEntity(dto.step!!) } returns stepEntity

        // Mock finding existing entity
        mockkObject(TestCaseEntity)
        every { TestCaseEntity.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { usersMapper.toEntity(dto.user) }
        verify(exactly = 1) { workflowsMapper.toEntity(dto.workflow!!) }
        verify(exactly = 1) { stepsMapper.toEntity(dto.step!!) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.userEntity = userEntity }
        verify { existingEntity.workflow = workflowEntity }
        verify { existingEntity.step = stepEntity }
        
        // Verify other fields were set correctly
        verify { existingEntity.name = dto.name }
        verify { existingEntity.description = dto.description }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TestCaseEntity)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = TestCase(
            id = 0L,
            user = mockk(),
            name = "New Test Case",
            description = "New Description",
            workflow = mockk(),
            step = mockk(),
            inputs = emptyList(),
            expectedOutcomes = emptyList(),
            results = emptyList()
        )

        // Create mock new entity
        val newEntity = mockk<TestCaseEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val userEntity = mockk<UserEntity>()
        val workflowEntity = mockk<WorkflowEntity>()
        val stepEntity = mockk<StepEntity>()
        
        every { usersMapper.toEntity(dto.user) } returns userEntity
        every { workflowsMapper.toEntity(dto.workflow!!) } returns workflowEntity
        every { stepsMapper.toEntity(dto.step!!) } returns stepEntity
        
        // Setup factory mock
        every { factory.create() } returns newEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify factory was used to create new entity
        verify(exactly = 1) { factory.create() }

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { usersMapper.toEntity(dto.user) }
        verify(exactly = 1) { workflowsMapper.toEntity(dto.workflow!!) }
        verify(exactly = 1) { stepsMapper.toEntity(dto.step!!) }

        // Verify entity fields were set with values from collaborators
        verify { newEntity.userEntity = userEntity }
        verify { newEntity.workflow = workflowEntity }
        verify { newEntity.step = stepEntity }
        
        // Verify other fields were set correctly
        verify { newEntity.name = dto.name }
        verify { newEntity.description = dto.description }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}