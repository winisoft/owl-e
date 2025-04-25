package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.runs.workflow.WorkflowTestResultEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import io.mockk.*
import kotlinx.datetime.Instant
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class TestResultMapperWorkflowTest {

    private lateinit var mapper: TestResultsMapper
    private val testCasesMapper: TestCasesMapper = mockk()
    private val workflowExecutionsMapper: WorkflowExecutionsMapper = mockk()
    private val factory: DaoFactory<WorkflowTestResultEntity, Long> = mockk()
    private val dateTimeConverter: DateTimeConverter = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TestResultsMapper(factory, testCasesMapper, workflowExecutionsMapper, dateTimeConverter)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTO
        val dto = WorkflowTestResult(
            id = 99L,
            testCase = mockk(),
            workflowExecution = mockk(),
            passed = true,
            executedAt = "2023-10-01T10:00:00"
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(WorkflowTestResultEntity)
        every { WorkflowTestResultEntity.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestResult with id=99 not found", exception.message)
        unmockkObject(WorkflowTestResultEntity)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<WorkflowTestResultEntity> {
            every { id.value } returns 1L
            every { testCaseEntity } returns mockk()
            every { workflowExecution } returns mockk()
            every { passed } returns true
            every { executedAt } returns mockk()
        }

        // Setup mock responses from collaborators
        val testCase = mockk<TestCase>()
        val workflowExecution = mockk<WorkflowExecution>()
        val executedAtStr = "2023-10-01T10:00:00"
        
        every { testCasesMapper.toDto(entity.testCaseEntity) } returns testCase
        every { workflowExecutionsMapper.toDto(entity.workflowExecution) } returns workflowExecution
        every { dateTimeConverter.toString(entity.executedAt) } returns executedAtStr

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toDto(entity.testCaseEntity) }
        verify(exactly = 1) { workflowExecutionsMapper.toDto(entity.workflowExecution) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.executedAt) }

        // Verify result contains values from collaborators
        assertEquals(testCase, result.testCase)
        assertEquals(workflowExecution, result.workflowExecution)
        assertEquals(executedAtStr, result.executedAt)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
        assertEquals(entity.passed, result.passed)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = WorkflowTestResult(
            id = 42L,
            testCase = mockk(),
            workflowExecution = mockk(),
            passed = false,
            executedAt = "2023-10-01T11:00:00"
        )

        // Create mock existing entity to update
        val existingEntity = mockk<WorkflowTestResultEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val testCaseEntity = mockk<TestCaseEntity>()
        val workflowRunEntity = mockk<WorkflowRunEntity>()
        val executedAt = mockk<Instant>()
        
        every { testCasesMapper.toEntity(dto.testCase) } returns testCaseEntity
        every { workflowExecutionsMapper.toEntity(dto.workflowExecution) } returns workflowRunEntity
        every { dateTimeConverter.fromString(dto.executedAt) } returns executedAt

        // Mock finding existing entity
        mockkObject(WorkflowTestResultEntity)
        every { WorkflowTestResultEntity.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toEntity(dto.testCase) }
        verify(exactly = 1) { workflowExecutionsMapper.toEntity(dto.workflowExecution) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.executedAt) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.testCaseEntity = testCaseEntity }
        verify { existingEntity.workflowExecution = workflowRunEntity }
        verify { existingEntity.executedAt = executedAt }
        
        // Verify other fields were set correctly
        verify { existingEntity.passed = dto.passed }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(WorkflowTestResultEntity)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = WorkflowTestResult(
            id = 0L,
            testCase = mockk(),
            workflowExecution = mockk(),
            passed = true,
            executedAt = "2023-10-01T12:00:00"
        )

        // Create mock new entity
        val newEntity = mockk<WorkflowTestResultEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val testCaseEntity = mockk<TestCaseEntity>()
        val workflowRunEntity = mockk<WorkflowRunEntity>()
        val executedAt = mockk<Instant>()
        
        every { testCasesMapper.toEntity(dto.testCase) } returns testCaseEntity
        every { workflowExecutionsMapper.toEntity(dto.workflowExecution) } returns workflowRunEntity
        every { dateTimeConverter.fromString(dto.executedAt) } returns executedAt
        
        // Setup factory mock
        every { factory.create() } returns newEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify factory was used to create new entity
        verify(exactly = 1) { factory.create() }

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toEntity(dto.testCase) }
        verify(exactly = 1) { workflowExecutionsMapper.toEntity(dto.workflowExecution) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.executedAt) }

        // Verify entity fields were set with values from collaborators
        verify { newEntity.testCaseEntity = testCaseEntity }
        verify { newEntity.workflowExecution = workflowRunEntity }
        verify { newEntity.executedAt = executedAt }
        
        // Verify other fields were set correctly
        verify { newEntity.passed = dto.passed }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}