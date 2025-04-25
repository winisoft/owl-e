package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestExpectation
import com.wini.owle.entity.runs.enums.ComparisonOperatorEntity
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.runs.test.TestExpectationEntity
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
    private val factory: DaoFactory<TestExpectationEntity, Long> = mockk()
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
        val dto = TestExpectation(
            id = 99L,
            testCase = mockk(),
            dataPointName = "Data Point",
            expectedValue = "Expected Value",
            comparisonOperator = mockk()
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(TestExpectationEntity)
        every { TestExpectationEntity.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestExpectedOutcome with id=99 not found", exception.message)
        unmockkObject(TestExpectationEntity)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<TestExpectationEntity> {
            every { id.value } returns 1L
            every { testCaseEntity } returns mockk()
            every { dataPointName } returns "Data Point"
            every { expectedValue } returns "Expected Value"
            every { comparisonOperator } returns mockk()
        }

        // Setup mock responses from collaborators
        val testCase = mockk<TestCase>()
        val comparisonOperatorDto = ComparisonOperator.Equals
        
        every { testCasesMapper.toDto(entity.testCaseEntity) } returns testCase
        every { comparisonOperatorMapper.toDto(entity.comparisonOperator) } returns comparisonOperatorDto

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toDto(entity.testCaseEntity) }
        verify(exactly = 1) { comparisonOperatorMapper.toDto(entity.comparisonOperator) }

        // Verify result contains values from collaborators
        assertEquals(testCase, result.testCase)
        assertEquals(comparisonOperatorDto, result.comparisonOperator)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
        assertEquals(entity.dataPointName, result.dataPointName)
        assertEquals(entity.expectedValue, result.expectedValue)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = TestExpectation(
            id = 42L,
            testCase = mockk(),
            dataPointName = "Updated Data Point",
            expectedValue = "Updated Expected Value",
            comparisonOperator = mockk()
        )

        // Create mock existing entity to update
        val existingEntity = mockk<TestExpectationEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val testCaseEntity = mockk<TestCaseEntity>()
        val comparisonOperator = ComparisonOperatorEntity.EQUALS
        
        every { testCasesMapper.toEntity(dto.testCase) } returns testCaseEntity
        every { comparisonOperatorMapper.toEntity(dto.comparisonOperator) } returns comparisonOperator

        // Mock finding existing entity
        mockkObject(TestExpectationEntity)
        every { TestExpectationEntity.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toEntity(dto.testCase) }
        verify(exactly = 1) { comparisonOperatorMapper.toEntity(dto.comparisonOperator) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.testCaseEntity = testCaseEntity }
        verify { existingEntity.comparisonOperator = comparisonOperator }
        
        // Verify other fields were set correctly
        verify { existingEntity.dataPointName = dto.dataPointName }
        verify { existingEntity.expectedValue = dto.expectedValue }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TestExpectationEntity)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = TestExpectation(
            id = 0L,
            testCase = mockk(),
            dataPointName = "New Data Point",
            expectedValue = "New Expected Value",
            comparisonOperator = mockk()
        )

        // Create mock new entity
        val newEntity = mockk<TestExpectationEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val testCaseEntity = mockk<TestCaseEntity>()
        val comparisonOperator = ComparisonOperatorEntity.EQUALS
        
        every { testCasesMapper.toEntity(dto.testCase) } returns testCaseEntity
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
        verify { newEntity.testCaseEntity = testCaseEntity }
        verify { newEntity.comparisonOperator = comparisonOperator }
        
        // Verify other fields were set correctly
        verify { newEntity.dataPointName = dto.dataPointName }
        verify { newEntity.expectedValue = dto.expectedValue }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}