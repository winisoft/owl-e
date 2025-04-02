package com.wini.owle.mapper.runs

import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.dto.user.UserDto
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper
import com.wini.owle.models.User
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
        // Create test DTOs
        val dto = TestCaseDto(
            id = 99L,
            user = mockk(),
            name = "Test Case",
            description = "Description",
            workflow = mockk(),
            step = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TestCaseDao)
        every { TestCaseDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestCase with id=99 not found", exception.message)
        unmockkObject(TestCaseDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TestCaseDao> {
            every { id.value } returns 1L
            every { user } returns mockk()
            every { name } returns "Test Case"
            every { description } returns "Description"
            every { workflow } returns mockk()
            every { step } returns mockk()
        }

        // Mock the behavior of usersMapper, workflowsMapper, and stepsMapper
        val userDto = mockk<UserDto>()
        val workflowDto = mockk<WorkflowDto>()
        val stepDto = mockk<StepDto>()
        every { usersMapper.toDto(entity.user) } returns userDto
        every { workflowsMapper.toDto(entity.workflow) } returns workflowDto
        every { stepsMapper.toDto(entity.step) } returns stepDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(userDto, result.user)
        assertEquals("Test Case", result.name)
        assertEquals("Description", result.description)
        assertEquals(workflowDto, result.workflow)
        assertEquals(stepDto, result.step)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TestCaseDto(
            id = 42L,
            user = mockk(),
            name = "Updated Test Case",
            description = "Updated Description",
            workflow = mockk(),
            step = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TestCaseDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(TestCaseDao)
        every { TestCaseDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val userSlot = slot<User>()
        val workflowSlot = slot<WorkflowDao>()
        val stepSlot = slot<StepDao>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()

        every { existingEntity.user = capture(userSlot) } just runs
        every { existingEntity.workflow = capture(workflowSlot) } just runs
        every { existingEntity.step = capture(stepSlot) } just runs
        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TestCaseDao.findById(42L) }
        verify { existingEntity.user = dto.user }
        verify { existingEntity.workflow = dto.workflow }
        verify { existingEntity.step = dto.step }
        verify { existingEntity.name = dto.name }
        verify { existingEntity.description = dto.description }

        // Verify the captured values
        assertEquals(dto.user, userSlot.captured)
        assertEquals(dto.workflow, workflowSlot.captured)
        assertEquals(dto.step, stepSlot.captured)
        assertEquals(dto.name, nameSlot.captured)
        assertEquals(dto.description, descriptionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TestCaseDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TestCaseDto(
            id = 0L,
            user = mockk(),
            name = "New Test Case",
            description = "New Description",
            workflow = mockk(),
            step = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TestCaseDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val userSlot = slot<User>()
        val workflowSlot = slot<Workflow>()
        val stepSlot = slot<Step>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()

        every { newEntity.user = capture(userSlot) } just runs
        every { newEntity.workflow = capture(workflowSlot) } just runs
        every { newEntity.step = capture(stepSlot) } just runs
        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.user = dto.user }
        verify { newEntity.workflow = dto.workflow }
        verify { newEntity.step = dto.step }
        verify { newEntity.name = dto.name }
        verify { newEntity.description = dto.description }

        // Verify the captured values
        assertEquals(dto.user, userSlot.captured)
        assertEquals(dto.workflow, workflowSlot.captured)
        assertEquals(dto.step, stepSlot.captured)
        assertEquals(dto.name, nameSlot.captured)
        assertEquals(dto.description, descriptionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}