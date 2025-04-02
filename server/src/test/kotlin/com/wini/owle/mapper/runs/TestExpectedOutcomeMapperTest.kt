package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.ComparisonOperatorDto
import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import com.wini.owle.entity.runs.ComparisonOperator
import com.wini.owle.entity.runs.ComparisonOperator.Companion.toDto
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class TestExpectedOutcomeMapperTest {

    private lateinit var mapper: TestExpectedOutcomesMapper
    private val testCasesMapper: TestCasesMapper = mockk()
    private val factory: DaoFactory<TestExpectedOutcomeDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TestExpectedOutcomesMapper(factory, testCasesMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TestExpectedOutcomeDto(
            id = 99L,
            testCase = mockk(),
            dataPointName = "Data Point",
            expectedValue = "Expected Value",
            comparisonOperator = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TestExpectedOutcomeDao)
        every { TestExpectedOutcomeDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestExpectedOutcome with id=99 not found", exception.message)
        unmockkObject(TestExpectedOutcomeDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TestExpectedOutcomeDao> {
            every { id.value } returns 1L
            every { testCase } returns mockk()
            every { dataPointName } returns "Data Point"
            every { expectedValue } returns "Expected Value"
            every { comparisonOperator.toDto() } returns ComparisonOperatorDto.Equals
        }

        // Mock the behavior of testCasesMapper
        val testCaseDto = mockk<TestCaseDto>()
        every { testCasesMapper.toDto(entity.testCase) } returns testCaseDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(testCaseDto, result.testCase)
        assertEquals("Data Point", result.dataPointName)
        assertEquals("Expected Value", result.expectedValue)
        assertEquals(entity.comparisonOperator.toDto(), result.comparisonOperator)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TestExpectedOutcomeDto(
            id = 42L,
            testCase = mockk(),
            dataPointName = "Updated Data Point",
            expectedValue = "Updated Expected Value",
            comparisonOperator = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TestExpectedOutcomeDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val testCaseDao: TestCaseDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(TestExpectedOutcomeDao)
        every { TestExpectedOutcomeDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val testCaseSlot = slot<TestCaseDao>()
        val dataPointNameSlot = slot<String>()
        val expectedValueSlot = slot<String>()
        val comparisonOperatorSlot = slot<ComparisonOperator>()

        every { existingEntity.testCase = capture(testCaseSlot) } just runs
        every { existingEntity.dataPointName = capture(dataPointNameSlot) } just runs
        every { existingEntity.expectedValue = capture(expectedValueSlot) } just runs
        every { existingEntity.comparisonOperator = capture(comparisonOperatorSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TestExpectedOutcomeDao.findById(42L) }
        verify { existingEntity.testCase = testCaseDao }
        verify { existingEntity.dataPointName = dto.dataPointName }
        verify { existingEntity.expectedValue = dto.expectedValue }
        verify { existingEntity.comparisonOperator = ComparisonOperator.fromDto(dto.comparisonOperator) }

        // Verify the captured values
        assertEquals(testCaseDao, testCaseSlot.captured)
        assertEquals(dto.dataPointName, dataPointNameSlot.captured)
        assertEquals(dto.expectedValue, expectedValueSlot.captured)
        assertEquals(ComparisonOperator.fromDto(dto.comparisonOperator), comparisonOperatorSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TestExpectedOutcomeDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TestExpectedOutcomeDto(
            id = 0L,
            testCase = mockk(),
            dataPointName = "New Data Point",
            expectedValue = "New Expected Value",
            comparisonOperator = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TestExpectedOutcomeDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val testCaseDao: TestCaseDao = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val testCaseSlot = slot<TestCaseDao>()
        val dataPointNameSlot = slot<String>()
        val expectedValueSlot = slot<String>()
        val comparisonOperatorSlot = slot<ComparisonOperator>()

        every { newEntity.testCase = capture(testCaseSlot) } just runs
        every { newEntity.dataPointName = capture(dataPointNameSlot) } just runs
        every { newEntity.expectedValue = capture(expectedValueSlot) } just runs
        every { newEntity.comparisonOperator = capture(comparisonOperatorSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.testCase = testCaseDao }
        verify { newEntity.dataPointName = dto.dataPointName }
        verify { newEntity.expectedValue = dto.expectedValue }
        verify { newEntity.comparisonOperator = ComparisonOperator.fromDto(dto.comparisonOperator) }

        // Verify the captured values
        assertEquals(testCaseDao, testCaseSlot.captured)
        assertEquals(dto.dataPointName, dataPointNameSlot.captured)
        assertEquals(dto.expectedValue, expectedValueSlot.captured)
        assertEquals(ComparisonOperator.fromDto(dto.comparisonOperator), comparisonOperatorSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}