package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ErrorWatchDto
import com.wini.owle.dto.steps.ErrorWatchFnDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.entity.steps.ErrorWatchFnDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class ErrorWatchFnMapperTest {

    private lateinit var mapper: ErrorWatchFnsMapper
    private val errorWatchesMapper: ErrorWatchesMapper = mockk()
    private val factory: DaoFactory<ErrorWatchFnDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = ErrorWatchFnsMapper(factory, errorWatchesMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = ErrorWatchFnDto(
            id = 99L,
            errorWatch = mockk(),
            functionName = "testFunction",
            position = 1
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(ErrorWatchFnDao)
        every { ErrorWatchFnDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("ErrorWatchFn with id=99 not found", exception.message)
        unmockkObject(ErrorWatchFnDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<ErrorWatchFnDao> {
            every { id.value } returns 1L
            every { errorWatch } returns mockk()
            every { functionName } returns "testFunction"
            every { position } returns 1
        }

        // Mock the behavior of errorWatchesMapper
        val errorWatchDto = mockk<ErrorWatchDto>()
        every { errorWatchesMapper.toDto(entity.errorWatch) } returns errorWatchDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(errorWatchDto, result.errorWatch)
        assertEquals("testFunction", result.functionName)
        assertEquals(1, result.position)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = ErrorWatchFnDto(
            id = 42L,
            errorWatch = mockk(),
            functionName = "updatedFunction",
            position = 2
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<ErrorWatchFnDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val errorWatchDao: ErrorWatchDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(ErrorWatchFnDao)
        every { errorWatchesMapper.toEntity(any<ErrorWatchDto>()) } returns errorWatchDao
        every { ErrorWatchFnDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val errorWatchSlot = slot<ErrorWatchDao>()
        val functionNameSlot = slot<String>()
        val positionSlot = slot<Int>()

        every { existingEntity.errorWatch = capture(errorWatchSlot) } just runs
        every { existingEntity.functionName = capture(functionNameSlot) } just runs
        every { existingEntity.position = capture(positionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { ErrorWatchFnDao.findById(42L) }
        verify { existingEntity.errorWatch = errorWatchDao }
        verify { existingEntity.functionName = dto.functionName }
        verify { existingEntity.position = dto.position }

        // Verify the captured values
        assertEquals(errorWatchDao, errorWatchSlot.captured)
        assertEquals(dto.functionName, functionNameSlot.captured)
        assertEquals(dto.position, positionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(ErrorWatchFnDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = ErrorWatchFnDto(
            id = 0L,
            errorWatch = mockk(),
            functionName = "newFunction",
            position = 3
        )

        // Mock the new entity that will be created
        val newEntity = mockk<ErrorWatchFnDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val errorWatchDao: ErrorWatchDao = mockk()
        every { errorWatchesMapper.toEntity(any<ErrorWatchDto>()) } returns errorWatchDao

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val errorWatchSlot = slot<ErrorWatchDao>()
        val functionNameSlot = slot<String>()
        val positionSlot = slot<Int>()

        every { newEntity.errorWatch = capture(errorWatchSlot) } just runs
        every { newEntity.functionName = capture(functionNameSlot) } just runs
        every { newEntity.position = capture(positionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.errorWatch = errorWatchDao }
        verify { newEntity.functionName = dto.functionName }
        verify { newEntity.position = dto.position }

        // Verify the captured values
        assertEquals(errorWatchDao, errorWatchSlot.captured)
        assertEquals(dto.functionName, functionNameSlot.captured)
        assertEquals(dto.position, positionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}