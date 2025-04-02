package com.wini.owle.mapper.user

import com.wini.owle.dto.user.PaymentMethodDto
import com.wini.owle.dto.user.UserDto
import com.wini.owle.entity.user.CardBrand
import com.wini.owle.entity.user.PaymentMethodDao
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


open class PaymentMethodMapperTest {

    private lateinit var mapper: PaymentMethodsMapper
    private val userMapper: UsersMapper = mockk()
    private val factory: DaoFactory<PaymentMethodDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = PaymentMethodsMapper(factory, userMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = PaymentMethodDto(
            id = 99L,
            stripePaymentMethodId = "pm_12345",
            user = mockk(),
            cardBrand = mockk(),
            lastFour = "1234",
            isDefault = true,
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00"
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(PaymentMethodDao)
        every { PaymentMethodDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("PaymentMethod with id=99 not found", exception.message)
        unmockkObject(PaymentMethodDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<PaymentMethodDao> {
            every { id.value } returns 1L
            every { stripePaymentMethodId } returns "pm_12345"
            every { user } returns mockk()
            every { cardBrand } returns mockk()
            every { lastFour } returns "1234"
            every { isDefault } returns true
            every { createdAt } returns mockk()
            every { updatedAt } returns mockk()
        }

        // Mock the behavior of userMapper and DateTimeConverter
        val userDto = mockk<UserDto>()
        every { userMapper.toDto(entity.user) } returns userDto
        every { DateTimeConverter.toString(entity.createdAt) } returns "2023-01-01T00:00:00"
        every { DateTimeConverter.toString(entity.updatedAt) } returns "2023-01-01T00:00:00"

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("pm_12345", result.stripePaymentMethodId)
        assertEquals(userDto, result.user)
        assertEquals(entity.cardBrand?.toDto(), result.cardBrand)
        assertEquals("1234", result.lastFour)
        assertEquals(true, result.isDefault)
        assertEquals("2023-01-01T00:00:00", result.createdAt)
        assertEquals("2023-01-01T00:00:00", result.updatedAt)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = PaymentMethodDto(id = 42L, stripePaymentMethodId = "pm_12345", user = mockk(), cardBrand = mockk(), lastFour = "1234", isDefault = true, createdAt = "2023-01-01T00:00:00", updatedAt = "2023-01-01T00:00:00")

        // Mock the existing entity that will be updated
        val existingEntity = mockk<PaymentMethodDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(PaymentMethodDao)
        every { PaymentMethodDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val stripePaymentMethodIdSlot = slot<String>()
        val userSlot = slot<User>()
        val cardBrandSlot = slot<CardBrand>()
        val lastFourSlot = slot<String>()
        val isDefaultSlot = slot<Boolean>()
        val createdAtSlot = slot<LocalDateTime>()
        val updatedAtSlot = slot<LocalDateTime>()

        every { existingEntity.stripePaymentMethodId = capture(stripePaymentMethodIdSlot) } just runs
        every { existingEntity.user = capture(userSlot) } just runs
        every { existingEntity.cardBrand = capture(cardBrandSlot) } just runs
        every { existingEntity.lastFour = capture(lastFourSlot) } just runs
        every { existingEntity.isDefault = capture(isDefaultSlot) } just runs
        every { existingEntity.createdAt = capture(createdAtSlot) } just runs
        every { existingEntity.updatedAt = capture(updatedAtSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { PaymentMethodDao.findById(42L) }
        verify { existingEntity.stripePaymentMethodId = "pm_12345" }
        verify { existingEntity.user = dto.user }
        verify { existingEntity.cardBrand = dto.cardBrand }
        verify { existingEntity.lastFour = "1234" }
        verify { existingEntity.isDefault = true }
        verify { existingEntity.createdAt = LocalDateTime.parse(dto.createdAt) }
        verify { existingEntity.updatedAt = LocalDateTime.parse(dto.updatedAt) }

        // Verify the captured values
        assertEquals("pm_12345", stripePaymentMethodIdSlot.captured)
        assertEquals(dto.user, userSlot.captured)
        assertEquals(dto.cardBrand, cardBrandSlot.captured)
        assertEquals("1234", lastFourSlot.captured)
        assertEquals(true, isDefaultSlot.captured)
        assertEquals(LocalDateTime.parse(dto.createdAt), createdAtSlot.captured)
        assertEquals(LocalDateTime.parse(dto.updatedAt), updatedAtSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(PaymentMethodDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = PaymentMethodDto(
            id = 0L,
            stripePaymentMethodId = "pm_12345",
            user = mockk(),
            cardBrand = mockk(),
            lastFour = "1234",
            isDefault = true,
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<PaymentMethodDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val stripePaymentMethodIdSlot = slot<String>()
        val userSlot = slot<User>()
        val cardBrandSlot = slot<CardBrand>()
        val lastFourSlot = slot<String>()
        val isDefaultSlot = slot<Boolean>()
        val createdAtSlot = slot<LocalDateTime>()
        val updatedAtSlot = slot<LocalDateTime>()

        every { newEntity.stripePaymentMethodId = capture(stripePaymentMethodIdSlot) } just runs
        every { newEntity.user = capture(userSlot) } just runs
        every { newEntity.cardBrand = capture(cardBrandSlot) } just runs
        every { newEntity.lastFour = capture(lastFourSlot) } just runs
        every { newEntity.isDefault = capture(isDefaultSlot) } just runs
        every { newEntity.createdAt = capture(createdAtSlot) } just runs
        every { newEntity.updatedAt = capture(updatedAtSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.stripePaymentMethodId = "pm_12345" }
        verify { newEntity.user = dto.user }
        verify { newEntity.cardBrand = dto.cardBrand }
        verify { newEntity.lastFour = "1234" }
        verify { newEntity.isDefault = true }
        verify { newEntity.createdAt = LocalDateTime.parse(dto.createdAt) }
        verify { newEntity.updatedAt = LocalDateTime.parse(dto.updatedAt) }

        // Verify the captured values
        assertEquals("pm_12345", stripePaymentMethodIdSlot.captured)
        assertEquals(dto.user, userSlot.captured)
        assertEquals(dto.cardBrand, cardBrandSlot.captured)
        assertEquals("1234", lastFourSlot.captured)
        assertEquals(true, isDefaultSlot.captured)
        assertEquals(LocalDateTime.parse(dto.createdAt), createdAtSlot.captured)
        assertEquals(LocalDateTime.parse(dto.updatedAt), updatedAtSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}