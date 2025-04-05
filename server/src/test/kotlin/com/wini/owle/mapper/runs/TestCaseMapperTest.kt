package com.wini.owle.mapper.runs

import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.dto.user.UserDto
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class TestCaseMapperTest {

    private lateinit var mapper: TestCasesMapper
    private val testInputMapper: TestInputsMapper = mockk()
    private val testExpectedOutcomeMapper: TestExpectedOutcomesMapper = mockk()
    private val testResultMapper: TestResultsMapper = mockk()
    private val usersMapper: UsersMapper = mockk()
    private val workflowsMapper: WorkflowsMapper = mockk()
    private val stepsMapper: StepsMapper = mockk()
    private val factory: DaoFactory<TestCaseDao, Long> = mockk()

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
        val dto = TestCaseDto(
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
        mockkObject(TestCaseDao)
        every { TestCaseDao.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestCase with id=99 not found", exception.message)
        unmockkObject(TestCaseDao)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<TestCaseDao> {
            every { id.value } returns 1L
            every { user } returns mockk()
            every { name } returns "Test Case"
            every { description } returns "Description"
            every { workflow } returns mockk()
            every { step } returns mockk()
        }

        // Setup mock responses from collaborators
        val userDto = mockk<UserDto>()
        val workflowDto = mockk<WorkflowDto>()
        val stepDto = mockk<StepDto>()
        
        every { usersMapper.toDto(entity.user) } returns userDto
        every { workflowsMapper.toDto(entity.workflow!!) } returns workflowDto
        every { stepsMapper.toDto(entity.step!!) } returns stepDto

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { usersMapper.toDto(entity.user) }
        verify(exactly = 1) { workflowsMapper.toDto(entity.workflow!!) }
        verify(exactly = 1) { stepsMapper.toDto(entity.step!!) }

        // Verify result contains values from collaborators
        assertEquals(userDto, result.user)
        assertEquals(workflowDto, result.workflow)
        assertEquals(stepDto, result.step)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
        assertEquals(entity.name, result.name)
        assertEquals(entity.description, result.description)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = TestCaseDto(
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
        val existingEntity = mockk<TestCaseDao>(relaxed = true)

        // Setup mock responses from collaborators
        val userDao = mockk<UserDao>()
        val workflowDao = mockk<WorkflowDao>()
        val stepDao = mockk<StepDao>()
        
        every { usersMapper.toEntity(dto.user) } returns userDao
        every { workflowsMapper.toEntity(dto.workflow!!) } returns workflowDao
        every { stepsMapper.toEntity(dto.step!!) } returns stepDao

        // Mock finding existing entity
        mockkObject(TestCaseDao)
        every { TestCaseDao.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { usersMapper.toEntity(dto.user) }
        verify(exactly = 1) { workflowsMapper.toEntity(dto.workflow!!) }
        verify(exactly = 1) { stepsMapper.toEntity(dto.step!!) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.user = userDao }
        verify { existingEntity.workflow = workflowDao }
        verify { existingEntity.step = stepDao }
        
        // Verify other fields were set correctly
        verify { existingEntity.name = dto.name }
        verify { existingEntity.description = dto.description }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TestCaseDao)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = TestCaseDto(
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
        val newEntity = mockk<TestCaseDao>(relaxed = true)

        // Setup mock responses from collaborators
        val userDao = mockk<UserDao>()
        val workflowDao = mockk<WorkflowDao>()
        val stepDao = mockk<StepDao>()
        
        every { usersMapper.toEntity(dto.user) } returns userDao
        every { workflowsMapper.toEntity(dto.workflow!!) } returns workflowDao
        every { stepsMapper.toEntity(dto.step!!) } returns stepDao
        
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
        verify { newEntity.user = userDao }
        verify { newEntity.workflow = workflowDao }
        verify { newEntity.step = stepDao }
        
        // Verify other fields were set correctly
        verify { newEntity.name = dto.name }
        verify { newEntity.description = dto.description }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}