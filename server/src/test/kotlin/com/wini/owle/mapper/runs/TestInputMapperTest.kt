package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.GenerationMethodDto
import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.dto.runs.TestInputDto
import com.wini.owle.entity.runs.enums.GenerationMethod
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.runs.enums.GenerationMethodMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class TestInputMapperTest {

    private lateinit var mapper: TestInputsMapper
    private val testCasesMapper: TestCasesMapper = mockk()
    private val factory: DaoFactory<TestInputDao, Long> = mockk()
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
        val dto = TestInputDto(
            id = 99L,
            testCase = mockk(),
            dataPointName = "Data Point",
            value = "Value",
            generationMethod = mockk()
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(TestInputDao)
        every { TestInputDao.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestInput with id=99 not found", exception.message)
        unmockkObject(TestInputDao)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<TestInputDao> {
            every { id.value } returns 1L
            every { testCase } returns mockk()
            every { dataPointName } returns "Data Point"
            every { value } returns "Value"
            every { generationMethod } returns mockk()
        }

        // Setup mock responses from collaborators
        val testCaseDto = mockk<TestCaseDto>()
        val generationMethodDto = GenerationMethodDto.Manual
        
        every { testCasesMapper.toDto(entity.testCase) } returns testCaseDto
        every { generationMethodMapper.toDto(entity.generationMethod) } returns generationMethodDto

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toDto(entity.testCase) }
        verify(exactly = 1) { generationMethodMapper.toDto(entity.generationMethod) }

        // Verify result contains values from collaborators
        assertEquals(testCaseDto, result.testCase)
        assertEquals(generationMethodDto, result.generationMethod)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
        assertEquals(entity.dataPointName, result.dataPointName)
        assertEquals(entity.value, result.value)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = TestInputDto(
            id = 42L,
            testCase = mockk(),
            dataPointName = "Updated Data Point",
            value = "Updated Value",
            generationMethod = GenerationMethodDto.Manual
        )

        // Create mock existing entity to update
        val existingEntity = mockk<TestInputDao>(relaxed = true)

        // Setup mock responses from collaborators
        val testCaseDao = mockk<TestCaseDao>()
        val generationMethod = GenerationMethod.MANUAL
        
        every { testCasesMapper.toEntity(dto.testCase) } returns testCaseDao
        every { generationMethodMapper.toEntity(dto.generationMethod) } returns generationMethod

        // Mock finding existing entity
        mockkObject(TestInputDao)
        every { TestInputDao.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { testCasesMapper.toEntity(dto.testCase) }
        verify(exactly = 1) { generationMethodMapper.toEntity(dto.generationMethod) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.testCase = testCaseDao }
        verify { existingEntity.generationMethod = generationMethod }
        
        // Verify other fields were set correctly
        verify { existingEntity.dataPointName = dto.dataPointName }
        verify { existingEntity.value = dto.value }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TestInputDao)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = TestInputDto(
            id = 0L,
            testCase = mockk(),
            dataPointName = "New Data Point",
            value = "New Value",
            generationMethod = mockk()
        )

        // Create mock new entity
        val newEntity = mockk<TestInputDao>(relaxed = true)

        // Setup mock responses from collaborators
        val testCaseDao = mockk<TestCaseDao>()
        val generationMethod = GenerationMethod.MANUAL
        
        every { testCasesMapper.toEntity(dto.testCase) } returns testCaseDao
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
        verify { newEntity.testCase = testCaseDao }
        verify { newEntity.generationMethod = generationMethod }
        
        // Verify other fields were set correctly
        verify { newEntity.dataPointName = dto.dataPointName }
        verify { newEntity.value = dto.value }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}