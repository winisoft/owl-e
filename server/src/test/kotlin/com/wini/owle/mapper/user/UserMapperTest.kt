package com.wini.owle.mapper.user

import com.wini.owle.dto.user.UserDto
import com.wini.owle.entity.user.SubscriptionStatus
import com.wini.owle.entity.user.UserDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import io.mockk.slot
import io.mockk.just
import io.mockk.runs
import kotlinx.datetime.LocalDateTime
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class UserMapperTest {

    private lateinit var mapper: UsersMapper
    private val factory: DaoFactory<UserDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = UsersMapper(factory)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = UserDto(
            id = 99L,
            email = "test@example.com",
            password = "password",
            resetToken = "token",
            oauthProvider = "provider",
            oauthId = "oauthId",
            subscriptionStatus = mockk(),
            apiKey = "apiKey",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00",
            paymentMethods = emptyList()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(UserDao)
        every { UserDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("User with id=99 not found", exception.message)
        unmockkObject(UserDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
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

        // Mock the behavior of DateTimeConverter
        every { DateTimeConverter.toString(entity.createdAt) } returns "2023-01-01T00:00:00"
        every { DateTimeConverter.toString(entity.updatedAt) } returns "2023-01-01T00:00:00"

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("test@example.com", result.email)
        assertEquals("password", result.password)
        assertEquals("token", result.resetToken)
        assertEquals("provider", result.oauthProvider)
        assertEquals("oauthId", result.oauthId)
        assertEquals(entity.subscriptionStatus.toDto(), result.subscriptionStatus)
        assertEquals("apiKey", result.apiKey)
        assertEquals("2023-01-01T00:00:00", result.createdAt)
        assertEquals("2023-01-01T00:00:00", result.updatedAt)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = UserDto(
            id = 42L,
            email = "test@example.com",
            password = "password",
            resetToken = "token",
            oauthProvider = "provider",
            oauthId = "oauthId",
            subscriptionStatus = mockk(),
            apiKey = "apiKey",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00",
            paymentMethods = emptyList()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<UserDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(UserDao)
        every { UserDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val emailSlot = slot<String>()
        val passwordSlot = slot<String>()
        val resetTokenSlot = slot<String>()
        val oauthProviderSlot = slot<String>()
        val oauthIdSlot = slot<String>()
        val subscriptionStatusSlot = slot<SubscriptionStatus>()
        val apiKeySlot = slot<String>()
        val createdAtSlot = slot<LocalDateTime>()
        val updatedAtSlot = slot<LocalDateTime>()

        every { existingEntity.email = capture(emailSlot) } just runs
        every { existingEntity.password = capture(passwordSlot) } just runs
        every { existingEntity.resetToken = capture(resetTokenSlot) } just runs
        every { existingEntity.oauthProvider = capture(oauthProviderSlot) } just runs
        every { existingEntity.oauthId = capture(oauthIdSlot) } just runs
        every { existingEntity.subscriptionStatus = capture(subscriptionStatusSlot) } just runs
        every { existingEntity.apiKey = capture(apiKeySlot) } just runs
        every { existingEntity.createdAt = capture(createdAtSlot) } just runs
        every { existingEntity.updatedAt = capture(updatedAtSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { UserDao.findById(42L) }
        verify { existingEntity.email = "test@example.com" }
        verify { existingEntity.password = "password" }
        verify { existingEntity.resetToken = "token" }
        verify { existingEntity.oauthProvider = "provider" }
        verify { existingEntity.oauthId = "oauthId" }
        verify { existingEntity.subscriptionStatus = dto.subscriptionStatus }
        verify { existingEntity.apiKey = "apiKey" }
        verify { existingEntity.createdAt = LocalDateTime.parse(dto.createdAt) }
        verify { existingEntity.updatedAt = LocalDateTime.parse(dto.updatedAt) }

        // Verify the captured values
        assertEquals("test@example.com", emailSlot.captured)
        assertEquals("password", passwordSlot.captured)
        assertEquals("token", resetTokenSlot.captured)
        assertEquals("provider", oauthProviderSlot.captured)
        assertEquals("oauthId", oauthIdSlot.captured)
        assertEquals(dto.subscriptionStatus, subscriptionStatusSlot.captured)
        assertEquals("apiKey", apiKeySlot.captured)
        assertEquals(LocalDateTime.parse(dto.createdAt), createdAtSlot.captured)
        assertEquals(LocalDateTime.parse(dto.updatedAt), updatedAtSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(UserDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = UserDto(
            id = 0L,
            email = "test@example.com",
            password = "password",
            resetToken = "token",
            oauthProvider = "provider",
            oauthId = "oauthId",
            subscriptionStatus = mockk(),
            apiKey = "apiKey",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00",
            paymentMethods = emptyList()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<UserDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val emailSlot = slot<String>()
        val passwordSlot = slot<String>()
        val resetTokenSlot = slot<String>()
        val oauthProviderSlot = slot<String>()
        val oauthIdSlot = slot<String>()
        val subscriptionStatusSlot = slot<SubscriptionStatus>()
        val apiKeySlot = slot<String>()
        val createdAtSlot = slot<LocalDateTime>()
        val updatedAtSlot = slot<LocalDateTime>()

        every { newEntity.email = capture(emailSlot) } just runs
        every { newEntity.password = capture(passwordSlot) } just runs
        every { newEntity.resetToken = capture(resetTokenSlot) } just runs
        every { newEntity.oauthProvider = capture(oauthProviderSlot) } just runs
        every { newEntity.oauthId = capture(oauthIdSlot) } just runs
        every { newEntity.subscriptionStatus = capture(subscriptionStatusSlot) } just runs
        every { newEntity.apiKey = capture(apiKeySlot) } just runs
        every { newEntity.createdAt = capture(createdAtSlot) } just runs
        every { newEntity.updatedAt = capture(updatedAtSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.email = "test@example.com" }
        verify { newEntity.password = "password" }
        verify { newEntity.resetToken = "token" }
        verify { newEntity.oauthProvider = "provider" }
        verify { newEntity.oauthId = "oauthId" }
        verify { newEntity.subscriptionStatus = dto.subscriptionStatus }
        verify { newEntity.apiKey = "apiKey" }
        verify { newEntity.createdAt = LocalDateTime.parse(dto.createdAt) }
        verify { newEntity.updatedAt = LocalDateTime.parse(dto.updatedAt) }

        // Verify the captured values
        assertEquals("test@example.com", emailSlot.captured)
        assertEquals("password", passwordSlot.captured)
        assertEquals("token", resetTokenSlot.captured)
        assertEquals("provider", oauthProviderSlot.captured)
        assertEquals("oauthId", oauthIdSlot.captured)
        assertEquals(dto.subscriptionStatus, subscriptionStatusSlot.captured)
        assertEquals("apiKey", apiKeySlot.captured)
        assertEquals(LocalDateTime.parse(dto.createdAt), createdAtSlot.captured)
        assertEquals(LocalDateTime.parse(dto.updatedAt), updatedAtSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}
