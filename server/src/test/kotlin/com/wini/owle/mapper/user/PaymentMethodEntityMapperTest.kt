package com.wini.owle.mapper.user

import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.entity.user.enums.CardBrandEntity
import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.user.enums.CardBrandMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import kotlinx.datetime.Instant
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class PaymentMethodEntityMapperTest {

    private lateinit var mapper: PaymentMethodsMapper
    private val userMapper: UsersMapper = mockk()
    private val cardBrandMapper: CardBrandMapper = mockk()
    private val dateTimeConverter: DateTimeConverter = mockk()
    private val factory: DaoFactory<PaymentMethodEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = PaymentMethodsMapper(factory, userMapper, cardBrandMapper, dateTimeConverter)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTO
        val dto = PaymentMethod(
            id = 99L,
            stripePaymentMethodId = "pm_12345",
            user = mockk(),
            cardBrand = mockk(),
            lastFour = "1234",
            isDefault = true,
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00"
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(PaymentMethodEntity)
        every { PaymentMethodEntity.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("PaymentMethod with id=99 not found", exception.message)
        unmockkObject(PaymentMethodEntity)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<PaymentMethodEntity> {
            every { id.value } returns 1L
            every { stripePaymentMethodId } returns "pm_12345"
            every { userEntity } returns mockk()
            every { cardBrand } returns mockk()
            every { lastFour } returns "1234"
            every { isDefault } returns true
            every { createdAt } returns mockk()
            every { updatedAt } returns mockk()
        }

        // Setup mock responses from collaborators
        val user = mockk<User>()
        val cardBrandDto = mockk<CardBrand>()
        val createdAtStr = "2023-01-01T00:00:00"
        val updatedAtStr = "2023-01-01T00:00:00"
        
        every { userMapper.toDto(entity.userEntity) } returns user
        every { cardBrandMapper.toDto(entity.cardBrand!!) } returns cardBrandDto
        every { dateTimeConverter.toString(entity.createdAt) } returns createdAtStr
        every { dateTimeConverter.toString(entity.updatedAt) } returns updatedAtStr

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { userMapper.toDto(entity.userEntity) }
        verify(exactly = 1) { cardBrandMapper.toDto(entity.cardBrand!!) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.createdAt) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.updatedAt) }

        // Verify result contains values from collaborators
        assertEquals(user, result.user)
        assertEquals(cardBrandDto, result.cardBrand)
        assertEquals(createdAtStr, result.createdAt)
        assertEquals(updatedAtStr, result.updatedAt)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = PaymentMethod(
            id = 42L, 
            stripePaymentMethodId = "pm_12345", 
            user = mockk(), 
            cardBrand = mockk(), 
            lastFour = "1234", 
            isDefault = true, 
            createdAt = "2023-01-01T00:00:00", 
            updatedAt = "2023-02-01T00:00:00"
        )

        // Create mock existing entity to update
        val existingEntity = mockk<PaymentMethodEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val userEntity = mockk<UserEntity>()
        val cardBrand = mockk<CardBrandEntity>()
        val createdAt = mockk<Instant>()
        val updatedAt = mockk<Instant>()

        every { userMapper.toEntity(dto.user) } returns userEntity
        every { cardBrandMapper.toEntity(dto.cardBrand!!) } returns cardBrand
        every { dateTimeConverter.fromString(dto.createdAt) } returns createdAt
        every { dateTimeConverter.fromString(dto.updatedAt) } returns updatedAt

        // Mock finding existing entity
        mockkObject(PaymentMethodEntity)
        every { PaymentMethodEntity.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { userMapper.toEntity(dto.user) }
        verify(exactly = 1) { cardBrandMapper.toEntity(dto.cardBrand!!) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.createdAt) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.updatedAt) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.userEntity = userEntity }
        verify { existingEntity.cardBrand = cardBrand }
        verify { existingEntity.createdAt = createdAt }
        verify { existingEntity.updatedAt = updatedAt }

        // Verify other fields were set correctly
        verify { existingEntity.stripePaymentMethodId = dto.stripePaymentMethodId }
        verify { existingEntity.lastFour = dto.lastFour }
        verify { existingEntity.isDefault = dto.isDefault }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(PaymentMethodEntity)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = PaymentMethod(
            id = 0L,
            stripePaymentMethodId = "pm_12345",
            user = mockk(),
            cardBrand = mockk(),
            lastFour = "1234",
            isDefault = true,
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-02-01T00:00:00"
        )

        // Create mock new entity
        val newEntity = mockk<PaymentMethodEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val userEntity = mockk<UserEntity>()
        val cardBrand = mockk<CardBrandEntity>()
        val createdAt = mockk<Instant>()
        val updatedAt = mockk<Instant>()

        every { userMapper.toEntity(dto.user) } returns userEntity
        every { cardBrandMapper.toEntity(dto.cardBrand!!) } returns cardBrand
        every { dateTimeConverter.fromString(any<String>()) }.returnsMany(createdAt, updatedAt)
        
        // Setup factory mock
        every { factory.create() } returns newEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify factory was used to create new entity
        verify(exactly = 1) { factory.create() }

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { userMapper.toEntity(dto.user) }
        verify(exactly = 1) { cardBrandMapper.toEntity(dto.cardBrand!!) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.createdAt) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.updatedAt) }

        // Verify entity fields were set with values from collaborators
        verify { newEntity.userEntity = userEntity }
        verify { newEntity.cardBrand = cardBrand }
        verify { newEntity.createdAt = createdAt }
        verify { newEntity.updatedAt = updatedAt }

        // Verify other fields were set correctly
        verify { newEntity.stripePaymentMethodId = dto.stripePaymentMethodId }
        verify { newEntity.lastFour = dto.lastFour }
        verify { newEntity.isDefault = dto.isDefault }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}