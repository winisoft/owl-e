package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.dto.runs.TestInputDto
import com.wini.owle.entity.runs.GenerationMethod
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class TestInputMapperTest {

    private lateinit var mapper: TestInputsMapper
    private val testCasesMapper: TestCasesMapper = mockk()
    private val factory: DaoFactory<TestInputDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TestInputsMapper(factory, testCasesMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TestInputDto(
            id = 99L,
            testCase = mockk(),
            dataPointName = "Data Point",
            value = "Value",
            generationMethod = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TestInputDao)
        every { TestInputDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TestInput with id=99 not found", exception.message)
        unmockkObject(TestInputDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TestInputDao> {
            every { id.value } returns 1L
            every { testCase } returns mockk()
            every { dataPointName } returns "Data Point"
            every { value } returns "Value"
            every { generationMethod.toDto() } returns mockk()
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
        assertEquals("Value", result.value)
        assertEquals(entity.generationMethod.toDto(), result.generationMethod)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TestInputDto(
            id = 42L,
            testCase = mockk(),
            dataPointName = "Updated Data Point",
            value = "Updated Value",
            generationMethod = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TestInputDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(TestInputDao)
        every { TestInputDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val testCaseSlot = slot<TestCaseDao>()
        val dataPointNameSlot = slot<String>()
        val valueSlot = slot<String>()
        val generationMethodSlot = slot<GenerationMethod>()

        every { existingEntity.testCase = capture(testCaseSlot) } just runs
        every { existingEntity.dataPointName = capture(dataPointNameSlot) } just runs
        every { existingEntity.value = capture(valueSlot) } just runs
        every { existingEntity.generationMethod = capture(generationMethodSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TestInputDao.findById(42L) }
        verify { existingEntity.testCase = dto.testCase }
        verify { existingEntity.dataPointName = dto.dataPointName }
        verify { existingEntity.value = dto.value }
        verify { existingEntity.generationMethod = GenerationMethod.fromDto(dto.generationMethod) }

        // Verify the captured values
        assertEquals(dto.testCase, testCaseSlot.captured)
        assertEquals(dto.dataPointName, dataPointNameSlot.captured)
        assertEquals(dto.value, valueSlot.captured)
        assertEquals(GenerationMethod.fromDto(dto.generationMethod), generationMethodSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TestInputDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TestInputDto(id = 0L, testCase = mockk(), dataPointName = "New Data Point", value = "New Value", generationMethod = mockk())

        // Mock the new entity that will be created
        val newEntity = mockk<TestInputDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val testCaseSlot = slot<TestCaseDao>()
        val dataPointNameSlot = slot<String>()
        val valueSlot = slot<String>()
        val generationMethodSlot = slot<GenerationMethod>()

        every { newEntity.testCase = capture(testCaseSlot) } just runs
        every { newEntity.dataPointName = capture(dataPointNameSlot) } just runs
        every { newEntity.value = capture(valueSlot) } just runs
        every { newEntity.generationMethod = capture(generationMethodSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.testCase = dto.testCase }
        verify { newEntity.dataPointName = dto.dataPointName }
        verify { newEntity.value = dto.value }
        verify { newEntity.generationMethod = GenerationMethod.fromDto(dto.generationMethod) }

        // Verify the captured values
        assertEquals(dto.testCase, testCaseSlot.captured)
        assertEquals(dto.dataPointName, dataPointNameSlot.captured)
        assertEquals(dto.value, valueSlot.captured)
        assertEquals(GenerationMethod.fromDto(dto.generationMethod), generationMethodSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}