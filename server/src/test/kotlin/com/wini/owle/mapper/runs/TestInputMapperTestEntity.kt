package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.entity.runs.enums.GenerationMethodEntity
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.runs.enums.GenerationMethodMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class TestInputMapperTestEntity {

    private lateinit var mapper: TestInputsMapper
    private val testCasesMapper: TestCasesMapper = mockk()
    private val factory: DaoFactory<TestInputEntity, Long> = mockk()
    private val generationMethodMapper: GenerationMethodMapper = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TestInputsMapper(
            factory,
            testCasesMapper,
            generationMethodMapper,
        )
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTO
        val dto = TestInput(
            id = 99L,
            testCase = mockk(),
            dataPointName = "Data Point",
            value = "Value",
            generationMethod = mockk()
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(TestInputEntity)
        every { TestInputEntity.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestInput with id=99 not found", exception.message)
        unmockkObject(TestInputEntity)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<TestInputEntity> {
            every { id.value } returns 1L
            every { testCaseEntity } returns mockk()
            every { dataPointName } returns "Data Point"
            every { value } returns "Value"
            every { generationMethod } returns mockk()
        }

        // Setup mock responses from collaborators
        val testCase = mockk<TestCase>()
        val generationMethodDto = GenerationMethod.Manual
        
        every { testCasesMapper.toDto(entity.testCaseEntity) } returns testCase
        every { generationMethodMapper.toDto(entity.generationMethod) } returns generationMethodDto

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toDto(entity.testCaseEntity) }
        verify(exactly = 1) { generationMethodMapper.toDto(entity.generationMethod) }

        // Verify result contains values from collaborators
        assertEquals(testCase, result.testCase)
        assertEquals(generationMethodDto, result.generationMethod)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
        assertEquals(entity.dataPointName, result.dataPointName)
        assertEquals(entity.value, result.value)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = TestInput(
            id = 42L,
            testCase = mockk(),
            dataPointName = "Updated Data Point",
            value = "Updated Value",
            generationMethod = GenerationMethod.Manual
        )

        // Create mock existing entity to update
        val existingEntity = mockk<TestInputEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val testCaseEntity = mockk<TestCaseEntity>()
        val generationMethod = GenerationMethodEntity.MANUAL
        
        every { testCasesMapper.toEntity(dto.testCase) } returns testCaseEntity
        every { generationMethodMapper.toEntity(dto.generationMethod) } returns generationMethod

        // Mock finding existing entity
        mockkObject(TestInputEntity)
        every { TestInputEntity.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toEntity(dto.testCase) }
        verify(exactly = 1) { generationMethodMapper.toEntity(dto.generationMethod) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.testCaseEntity = testCaseEntity }
        verify { existingEntity.generationMethod = generationMethod }
        
        // Verify other fields were set correctly
        verify { existingEntity.dataPointName = dto.dataPointName }
        verify { existingEntity.value = dto.value }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TestInputEntity)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = TestInput(
            id = 0L,
            testCase = mockk(),
            dataPointName = "New Data Point",
            value = "New Value",
            generationMethod = mockk()
        )

        // Create mock new entity
        val newEntity = mockk<TestInputEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val testCaseEntity = mockk<TestCaseEntity>()
        val generationMethod = GenerationMethodEntity.MANUAL
        
        every { testCasesMapper.toEntity(dto.testCase) } returns testCaseEntity
        every { generationMethodMapper.toEntity(dto.generationMethod) } returns generationMethod
        
        // Setup factory mock
        every { factory.create() } returns newEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify factory was used to create new entity
        verify(exactly = 1) { factory.create() }

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toEntity(dto.testCase) }
        verify(exactly = 1) { generationMethodMapper.toEntity(dto.generationMethod) }

        // Verify entity fields were set with values from collaborators
        verify { newEntity.testCaseEntity = testCaseEntity }
        verify { newEntity.generationMethod = generationMethod }
        
        // Verify other fields were set correctly
        verify { newEntity.dataPointName = dto.dataPointName }
        verify { newEntity.value = dto.value }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}