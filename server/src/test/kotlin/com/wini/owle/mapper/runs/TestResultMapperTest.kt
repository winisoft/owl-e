package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.dto.runs.TestResultDto
import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.WorkflowExecutions
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.runs.TestResultDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class TestResultMapperTest {

    private lateinit var mapper: TestResultsMapper
    private val testCasesMapper: TestCasesMapper = mockk()
    private val workflowExecutionsMapper: WorkflowExecutionsMapper = mockk()
    private val factory: DaoFactory<TestResultDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TestResultsMapper(factory, testCasesMapper, workflowExecutionsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TestResultDto(
            id = 99L,
            testCase = mockk(),
            workflowExecution = mockk(),
            passed = true,
            executedAt = "2023-10-01T10:00:00"
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TestResultDao)
        every { TestResultDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestResult with id=99 not found", exception.message)
        unmockkObject(TestResultDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TestResultDao> {
            every { id.value } returns 1L
            every { testCase } returns mockk()
            every { workflowExecution } returns mockk()
            every { passed } returns true
            every { executedAt } returns LocalDateTime.parse("2023-10-01T10:00:00")
        }

        // Mock the behavior of testCasesMapper and workflowExecutionsMapper
        val testCaseDto = mockk<TestCaseDto>()
        val workflowExecutionDto = mockk<WorkflowExecutionDto>()
        every { testCasesMapper.toDto(entity.testCase) } returns testCaseDto
        every { workflowExecutionsMapper.toDto(entity.workflowExecution) } returns workflowExecutionDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(testCaseDto, result.testCase)
        assertEquals(workflowExecutionDto, result.workflowExecution)
        assertEquals(true, result.passed)
        assertEquals("2023-10-01T10:00:00", result.executedAt)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TestResultDto(
            id = 42L,
            testCase = mockk(),
            workflowExecution = mockk(),
            passed = false,
            executedAt = "2023-10-01T11:00:00"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TestResultDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val testCaseDao: TestCaseDao = mockk()
        val workflowExecutionDao: WorkflowExecutionDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(TestResultDao)
        every { TestResultDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val testCaseSlot = slot<TestCaseDao>()
        val workflowExecutionSlot = slot<WorkflowExecutionDao>()
        val passedSlot = slot<Boolean>()
        val executedAtSlot = slot<LocalDateTime>()

        every { existingEntity.testCase = capture(testCaseSlot) } just runs
        every { existingEntity.workflowExecution = capture(workflowExecutionSlot) } just runs
        every { existingEntity.passed = capture(passedSlot) } just runs
        every { existingEntity.executedAt = capture(executedAtSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TestResultDao.findById(42L) }
        verify { existingEntity.testCase = testCaseDao }
        verify { existingEntity.workflowExecution = workflowExecutionDao }
        verify { existingEntity.passed = dto.passed }
        verify { existingEntity.executedAt = LocalDateTime.parse(dto.executedAt) }

        // Verify the captured values
        assertEquals(testCaseDao, testCaseSlot.captured)
        assertEquals(workflowExecutionDao, workflowExecutionSlot.captured)
        assertEquals(dto.passed, passedSlot.captured)
        assertEquals(LocalDateTime.parse(dto.executedAt), executedAtSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TestResultDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TestResultDto(
            id = 0L,
            testCase = mockk(),
            workflowExecution = mockk(),
            passed = true,
            executedAt = "2023-10-01T12:00:00"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TestResultDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val testCaseDao: TestCaseDao = mockk()
        val workflowExecutionDao: WorkflowExecutionDao = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val testCaseSlot = slot<TestCaseDao>()
        val workflowExecutionSlot = slot<WorkflowExecutionDao>()
        val passedSlot = slot<Boolean>()
        val executedAtSlot = slot<LocalDateTime>()

        every { newEntity.testCase = capture(testCaseSlot) } just runs
        every { newEntity.workflowExecution = capture(workflowExecutionSlot) } just runs
        every { newEntity.passed = capture(passedSlot) } just runs
        every { newEntity.executedAt = capture(executedAtSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.testCase = testCaseDao }
        verify { newEntity.workflowExecution = workflowExecutionDao }
        verify { newEntity.passed = dto.passed }
        verify { newEntity.executedAt = LocalDateTime.parse(dto.executedAt) }

        // Verify the captured values
        assertEquals(testCaseDao, testCaseSlot.captured)
        assertEquals(workflowExecutionDao, workflowExecutionSlot.captured)
        assertEquals(dto.passed, passedSlot.captured)
        assertEquals(LocalDateTime.parse(dto.executedAt), executedAtSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}