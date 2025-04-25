package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.entity.runs.enums.LogLevelEntity
import com.wini.owle.entity.runs.step.StepRunLogLineEntity
import com.wini.owle.entity.runs.step.StepRunEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.runs.enums.LogLevelMapper
import io.mockk.*
import kotlinx.datetime.Instant
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class ExecutionLogMapperTest {

    private lateinit var mapper: ExecutionLogMapper
    private val stepExecutionsMapper: StepExecutionsMapper = mockk()
    private val factory: DaoFactory<StepRunLogLineEntity, Long> = mockk()
    private val logLevelMapper: LogLevelMapper = mockk()
    private val dateTimeConverter: DateTimeConverter = mockk()

    @BeforeEach
    fun setUp() {
        mapper = ExecutionLogMapper(
            factory,
            stepExecutionsMapper,
            logLevelMapper,
            dateTimeConverter
        )
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTO
        val dto = StepRunLogLine(
            id = 99L,
            stepExecution = mockk(),
            timestamp = "2023-10-01T10:00:00",
            logLevel = LogLevel.Debug,
            message = "Test message"
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(StepRunLogLineEntity)
        every { StepRunLogLineEntity.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("ExecutionLog with id=99 not found", exception.message)
        unmockkObject(StepRunLogLineEntity)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<StepRunLogLineEntity> {
            every { id.value } returns 1L
            every { stepExecution } returns mockk()
            every { timestamp } returns mockk()
            every { logLevel } returns mockk()
            every { message } returns "Test message"
        }

        // Setup mock responses from collaborators
        val stepExecution = mockk<StepExecution>()
        val timestampStr = "2023-10-01T10:00:00"
        val logLevelDto = LogLevel.Info
        
        every { stepExecutionsMapper.toDto(entity.stepExecution) } returns stepExecution
        every { dateTimeConverter.toString(entity.timestamp) } returns timestampStr
        every { logLevelMapper.toDto(entity.logLevel) } returns logLevelDto

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { stepExecutionsMapper.toDto(entity.stepExecution) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.timestamp) }
        verify(exactly = 1) { logLevelMapper.toDto(entity.logLevel) }

        // Verify result contains values from collaborators
        assertEquals(stepExecution, result.stepExecution)
        assertEquals(timestampStr, result.timestamp)
        assertEquals(logLevelDto, result.logLevel)
        assertEquals("Test message", result.message)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = StepRunLogLine(
            id = 42L,
            stepExecution = mockk(),
            timestamp = "2023-10-01T11:00:00",
            logLevel = LogLevel.Warning,
            message = "Updated message"
        )

        // Create mock existing entity to update
        val existingEntity = mockk<StepRunLogLineEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val stepRunEntity = mockk<StepRunEntity>()
        val timestamp = mockk<Instant>()
        val logLevel = LogLevelEntity.WARNING
        
        every { stepExecutionsMapper.toEntity(dto.stepExecution) } returns stepRunEntity
        every { dateTimeConverter.fromString(dto.timestamp) } returns timestamp
        every { logLevelMapper.toEntity(dto.logLevel) } returns logLevel

        // Mock finding existing entity
        mockkObject(StepRunLogLineEntity)
        every { StepRunLogLineEntity.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { stepExecutionsMapper.toEntity(dto.stepExecution) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.timestamp) }
        verify(exactly = 1) { logLevelMapper.toEntity(dto.logLevel) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.stepExecution = stepRunEntity }
        verify { existingEntity.timestamp = timestamp }
        verify { existingEntity.logLevel = logLevel }
        
        // Verify other fields were set correctly
        verify { existingEntity.message = dto.message }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(StepRunLogLineEntity)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = StepRunLogLine(
            id = 0L,
            stepExecution = mockk(),
            timestamp = "2023-10-01T12:00:00",
            logLevel = LogLevel.Error,
            message = "New message"
        )

        // Create mock new entity
        val newEntity = mockk<StepRunLogLineEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val stepRunEntity = mockk<StepRunEntity>()
        val timestamp = mockk<Instant>()
        val logLevel = LogLevelEntity.ERROR
        
        every { stepExecutionsMapper.toEntity(dto.stepExecution) } returns stepRunEntity
        every { dateTimeConverter.fromString(dto.timestamp) } returns timestamp
        every { logLevelMapper.toEntity(dto.logLevel) } returns logLevel
        
        // Setup factory mock
        every { factory.create() } returns newEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify factory was used to create new entity
        verify(exactly = 1) { factory.create() }

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { stepExecutionsMapper.toEntity(dto.stepExecution) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.timestamp) }
        verify(exactly = 1) { logLevelMapper.toEntity(dto.logLevel) }

        // Verify entity fields were set with values from collaborators
        verify { newEntity.stepExecution = stepRunEntity }
        verify { newEntity.timestamp = timestamp }
        verify { newEntity.logLevel = logLevel }
        
        // Verify other fields were set correctly
        verify { newEntity.message = dto.message }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}