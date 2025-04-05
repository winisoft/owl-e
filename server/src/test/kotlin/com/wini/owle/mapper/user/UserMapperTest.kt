package com.wini.owle.mapper.user

import com.wini.owle.dto.user.SubscriptionStatusDto
import com.wini.owle.dto.user.UserDto
import com.wini.owle.entity.user.enums.SubscriptionStatus
import com.wini.owle.entity.user.UserDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.user.enums.SubscriptionStatusMapper
import io.mockk.*
import kotlinx.datetime.Instant
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class UserMapperTest {

    private lateinit var mapper: UsersMapper
    private val factory: DaoFactory<UserDao, Long> = mockk()
    private val subscriptionStatusMapper: SubscriptionStatusMapper = mockk()
    private val dateTimeConverter: DateTimeConverter = mockk()

    @BeforeEach
    fun setUp() {
        mapper = UsersMapper(factory, subscriptionStatusMapper, dateTimeConverter)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTO
        val dto = UserDto(
            id = 99L,
            email = "test@example.com",
            password = "password",
            resetToken = "token",
            oauthProvider = "provider",
            oauthId = "oauthId",
            subscriptionStatus = mockk(),
            apiKey = "apiKey",
            createdAt = "2023-01-01T00:00:00Z",
            updatedAt = "2023-01-01T00:00:00Z",
            paymentMethods = emptyList()
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(UserDao)
        every { UserDao.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("User with id=99 not found", exception.message)
        unmockkObject(UserDao)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<UserDao> {
            every { id.value } returns 1L
            every { email } returns "test@example.com"
            every { password } returns "password"
            every { resetToken } returns "token"
            every { oauthProvider } returns "provider"
            every { oauthId } returns "oauthId"
            every { subscriptionStatus } returns mockk()
            every { apiKey } returns "apiKey"
            every { createdAt } returns mockk()
            every { updatedAt } returns mockk()
        }

        // Setup mock responses from collaborators
        val subscriptionStatusDto = mockk<SubscriptionStatusDto>()
        val createdAtStr = "2023-01-01T00:00:00Z"
        val updatedAtStr = "2023-01-02T00:00:00Z"
        
        every { subscriptionStatusMapper.toDto(entity.subscriptionStatus) } returns subscriptionStatusDto
        every { dateTimeConverter.toString(entity.createdAt) } returns createdAtStr
        every { dateTimeConverter.toString(entity.updatedAt) } returns updatedAtStr

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { subscriptionStatusMapper.toDto(entity.subscriptionStatus) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.createdAt) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.updatedAt) }

        // Verify result contains values from collaborators
        assertEquals(subscriptionStatusDto, result.subscriptionStatus)
        assertEquals(createdAtStr, result.createdAt)
        assertEquals(updatedAtStr, result.updatedAt)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
        assertEquals(entity.email, result.email)
        assertEquals(entity.password, result.password)
        assertEquals(entity.resetToken, result.resetToken)
        assertEquals(entity.oauthProvider, result.oauthProvider)
        assertEquals(entity.oauthId, result.oauthId)
        assertEquals(entity.apiKey, result.apiKey)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = UserDto(
            id = 42L,
            email = "test@example.com",
            password = "password",
            resetToken = "token",
            oauthProvider = "provider",
            oauthId = "oauthId",
            subscriptionStatus = mockk(),
            apiKey = "apiKey",
            createdAt = "2023-01-01T00:00:00Z",
            updatedAt = "2023-01-02T00:00:00Z",
            paymentMethods = emptyList()
        )

        // Create mock existing entity to update
        val existingEntity = mockk<UserDao>(relaxed = true)

        // Setup mock responses from collaborators
        val subscriptionStatus = mockk<SubscriptionStatus>()
        val createdAt = mockk<Instant>(name = "createdAt")
        val updatedAt = mockk<Instant>(name = "updatedAt")
        
        every { subscriptionStatusMapper.toEntity(dto.subscriptionStatus) } returns subscriptionStatus
        every { dateTimeConverter.fromString(dto.createdAt) } returns createdAt
        every { dateTimeConverter.fromString(dto.updatedAt) } returns updatedAt

        // Mock finding existing entity
        mockkObject(UserDao)
        every { UserDao.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { subscriptionStatusMapper.toEntity(dto.subscriptionStatus) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.createdAt) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.updatedAt) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.subscriptionStatus = subscriptionStatus }
        verify { existingEntity.createdAt = createdAt }
        verify { existingEntity.updatedAt = updatedAt }
        
        // Verify other fields were set correctly
        verify { existingEntity.email = dto.email }
        verify { existingEntity.password = dto.password }
        verify { existingEntity.resetToken = dto.resetToken }
        verify { existingEntity.oauthProvider = dto.oauthProvider }
        verify { existingEntity.oauthId = dto.oauthId }
        verify { existingEntity.apiKey = dto.apiKey }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(UserDao)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = UserDto(
            id = 0L,
            email = "test@example.com",
            password = "password",
            resetToken = "token",
            oauthProvider = "provider",
            oauthId = "oauthId",
            subscriptionStatus = mockk(),
            apiKey = "apiKey",
            createdAt = "2023-01-01T00:00:00Z",
            updatedAt = "2023-01-02T00:00:00Z",
            paymentMethods = emptyList()
        )

        // Create mock new entity
        val newEntity = mockk<UserDao>(relaxed = true)

        // Setup mock responses from collaborators
        val subscriptionStatus = mockk<SubscriptionStatus>()
        val createdAt = mockk<Instant>(name = "createdAt")
        val updatedAt = mockk<Instant>(name = "updatedAt")
        
        every { subscriptionStatusMapper.toEntity(dto.subscriptionStatus) } returns subscriptionStatus
        every { dateTimeConverter.fromString(dto.createdAt) } returns createdAt
        every { dateTimeConverter.fromString(dto.updatedAt) } returns updatedAt
        
        // Setup factory mock
        every { factory.create() } returns newEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify factory was used to create new entity
        verify(exactly = 1) { factory.create() }

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { subscriptionStatusMapper.toEntity(dto.subscriptionStatus) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.createdAt) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.updatedAt) }

        // Verify entity fields were set with values from collaborators
        verify { newEntity.subscriptionStatus = subscriptionStatus }
        verify { newEntity.createdAt = createdAt }
        verify { newEntity.updatedAt = updatedAt }
        
        // Verify other fields were set correctly
        verify { newEntity.email = dto.email }
        verify { newEntity.password = dto.password }
        verify { newEntity.resetToken = dto.resetToken }
        verify { newEntity.oauthProvider = dto.oauthProvider }
        verify { newEntity.oauthId = dto.oauthId }
        verify { newEntity.apiKey = dto.apiKey }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}
