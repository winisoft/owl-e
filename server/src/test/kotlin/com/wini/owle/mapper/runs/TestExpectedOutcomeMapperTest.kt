package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.ComparisonOperatorDto
import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import com.wini.owle.entity.runs.enums.ComparisonOperator
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.runs.enums.ComparisonOperatorMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class TestExpectedOutcomeMapperTest {

    private lateinit var mapper: TestExpectedOutcomesMapper
    private val testCasesMapper: TestCasesMapper = mockk()
    private val factory: DaoFactory<TestExpectedOutcomeDao, Long> = mockk()
    private val comparisonOperatorMapper: ComparisonOperatorMapper = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TestExpectedOutcomesMapper(
            factory,
            testCasesMapper,
            comparisonOperatorMapper
        )
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTO
        val dto = TestExpectedOutcomeDto(
            id = 99L,
            testCase = mockk(),
            dataPointName = "Data Point",
            expectedValue = "Expected Value",
            comparisonOperator = mockk()
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(TestExpectedOutcomeDao)
        every { TestExpectedOutcomeDao.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestExpectedOutcome with id=99 not found", exception.message)
        unmockkObject(TestExpectedOutcomeDao)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<TestExpectedOutcomeDao> {
            every { id.value } returns 1L
            every { testCase } returns mockk()
            every { dataPointName } returns "Data Point"
            every { expectedValue } returns "Expected Value"
            every { comparisonOperator } returns mockk()
        }

        // Setup mock responses from collaborators
        val testCaseDto = mockk<TestCaseDto>()
        val comparisonOperatorDto = ComparisonOperatorDto.Equals
        
        every { testCasesMapper.toDto(entity.testCase) } returns testCaseDto
        every { comparisonOperatorMapper.toDto(entity.comparisonOperator) } returns comparisonOperatorDto

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toDto(entity.testCase) }
        verify(exactly = 1) { comparisonOperatorMapper.toDto(entity.comparisonOperator) }

        // Verify result contains values from collaborators
        assertEquals(testCaseDto, result.testCase)
        assertEquals(comparisonOperatorDto, result.comparisonOperator)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
        assertEquals(entity.dataPointName, result.dataPointName)
        assertEquals(entity.expectedValue, result.expectedValue)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = TestExpectedOutcomeDto(
            id = 42L,
            testCase = mockk(),
            dataPointName = "Updated Data Point",
            expectedValue = "Updated Expected Value",
            comparisonOperator = mockk()
        )

        // Create mock existing entity to update
        val existingEntity = mockk<TestExpectedOutcomeDao>(relaxed = true)

        // Setup mock responses from collaborators
        val testCaseDao = mockk<TestCaseDao>()
        val comparisonOperator = ComparisonOperator.EQUALS
        
        every { testCasesMapper.toEntity(dto.testCase) } returns testCaseDao
        every { comparisonOperatorMapper.toEntity(dto.comparisonOperator) } returns comparisonOperator

        // Mock finding existing entity
        mockkObject(TestExpectedOutcomeDao)
        every { TestExpectedOutcomeDao.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toEntity(dto.testCase) }
        verify(exactly = 1) { comparisonOperatorMapper.toEntity(dto.comparisonOperator) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.testCase = testCaseDao }
        verify { existingEntity.comparisonOperator = comparisonOperator }
        
        // Verify other fields were set correctly
        verify { existingEntity.dataPointName = dto.dataPointName }
        verify { existingEntity.expectedValue = dto.expectedValue }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TestExpectedOutcomeDao)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = TestExpectedOutcomeDto(
            id = 0L,
            testCase = mockk(),
            dataPointName = "New Data Point",
            expectedValue = "New Expected Value",
            comparisonOperator = mockk()
        )

        // Create mock new entity
        val newEntity = mockk<TestExpectedOutcomeDao>(relaxed = true)

        // Setup mock responses from collaborators
        val testCaseDao = mockk<TestCaseDao>()
        val comparisonOperator = ComparisonOperator.EQUALS
        
        every { testCasesMapper.toEntity(dto.testCase) } returns testCaseDao
        every { comparisonOperatorMapper.toEntity(dto.comparisonOperator) } returns comparisonOperator
        
        // Setup factory mock
        every { factory.create() } returns newEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify factory was used to create new entity
        verify(exactly = 1) { factory.create() }

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toEntity(dto.testCase) }
        verify(exactly = 1) { comparisonOperatorMapper.toEntity(dto.comparisonOperator) }

        // Verify entity fields were set with values from collaborators
        verify { newEntity.testCase = testCaseDao }
        verify { newEntity.comparisonOperator = comparisonOperator }
        
        // Verify other fields were set correctly
        verify { newEntity.dataPointName = dto.dataPointName }
        verify { newEntity.expectedValue = dto.expectedValue }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}