package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.ExecutionLogDto
import com.wini.owle.dto.runs.LogLevelDto
import com.wini.owle.dto.runs.StepExecutionDto
import com.wini.owle.entity.runs.LogLevel
import com.wini.owle.entity.StepExecutions
import com.wini.owle.entity.runs.ExecutionLogDao
import com.wini.owle.entity.runs.LogLevel.Companion.toDto
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class ExecutionLogMapperTest {

    private lateinit var mapper: ExecutionLogMapper
    private val stepExecutionsMapper: StepExecutionsMapper = mockk()
    private val factory: DaoFactory<ExecutionLogDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = ExecutionLogMapper(factory, stepExecutionsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = ExecutionLogDto(
            id = 99L,
            stepExecution = mockk(),
            timestamp = "2023-10-01T10:00:00",
            logLevel = mockk(),
            message = "Test message"
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(ExecutionLogDao)
        every { ExecutionLogDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("ExecutionLog with id=99 not found", exception.message)
        unmockkObject(ExecutionLogDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<ExecutionLogDao> {
            every { id.value } returns 1L
            every { stepExecution } returns mockk()
            every { timestamp.toJavaLocalDateTime().toString() } returns "2023-10-01T10:00:00"
            every { logLevel.toDto() } returns LogLevelDto.Info
            every { message } returns "Test message"
        }

        // Mock the behavior of stepExecutionsMapper
        val stepExecutionDto = mockk<StepExecutionDto>()
        every { stepExecutionsMapper.toDto(entity.stepExecution) } returns stepExecutionDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(stepExecutionDto, result.stepExecution)
        assertEquals("2023-10-01T10:00:00", result.timestamp)
        assertEquals(entity.logLevel.toDto(), result.logLevel)
        assertEquals("Test message", result.message)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = ExecutionLogDto(
            id = 42L,
            stepExecution = mockk(),
            timestamp = "2023-10-01T11:00:00",
            logLevel = mockk(),
            message = "Updated message"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<ExecutionLogDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepExecutionDao: StepExecutionDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(ExecutionLogDao)
        every { ExecutionLogDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val stepExecutionSlot = slot<StepExecutionDao>()
        val timestampSlot = slot<LocalDateTime>()
        val logLevelSlot = slot<LogLevel>()
        val messageSlot = slot<String>()

        every { existingEntity.stepExecution = capture(stepExecutionSlot) } just runs
        every { existingEntity.timestamp = capture(timestampSlot) } just runs
        every { existingEntity.logLevel = capture(logLevelSlot) } just runs
        every { existingEntity.message = capture(messageSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { ExecutionLogDao.findById(42L) }
        verify { existingEntity.stepExecution = stepExecutionDao }
        verify { existingEntity.timestamp = LocalDateTime.parse(dto.timestamp) }
        verify { existingEntity.logLevel = LogLevel.fromDto(dto.logLevel) }
        verify { existingEntity.message = dto.message }

        // Verify the captured values
        assertEquals(stepExecutionDao, stepExecutionSlot.captured)
        assertEquals(LocalDateTime.parse(dto.timestamp), timestampSlot.captured)
        assertEquals(LogLevel.fromDto(dto.logLevel), logLevelSlot.captured)
        assertEquals(dto.message, messageSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(ExecutionLogDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = ExecutionLogDto(
            id = 0L,
            stepExecution = mockk(),
            timestamp = "2023-10-01T12:00:00",
            logLevel = mockk(),
            message = "New message"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<ExecutionLogDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepExecutionDao: StepExecutionDao = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val stepExecutionSlot = slot<StepExecutionDao>()
        val timestampSlot = slot<LocalDateTime>()
        val logLevelSlot = slot<LogLevel>()
        val messageSlot = slot<String>()

        every { newEntity.stepExecution = capture(stepExecutionSlot) } just runs
        every { newEntity.timestamp = capture(timestampSlot) } just runs
        every { newEntity.logLevel = capture(logLevelSlot) } just runs
        every { newEntity.message = capture(messageSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.stepExecution = stepExecutionDao }
        verify { newEntity.timestamp = LocalDateTime.parse(dto.timestamp) }
        verify { newEntity.logLevel = LogLevel.fromDto(dto.logLevel) }
        verify { newEntity.message = dto.message }

        // Verify the captured values
        assertEquals(stepExecutionDao, stepExecutionSlot.captured)
        assertEquals(LocalDateTime.parse(dto.timestamp), timestampSlot.captured)
        assertEquals(LogLevel.fromDto(dto.logLevel), logLevelSlot.captured)
        assertEquals(dto.message, messageSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}