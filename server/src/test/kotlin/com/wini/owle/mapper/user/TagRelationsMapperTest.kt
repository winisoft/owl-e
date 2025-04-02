package com.wini.owle.mapper.user

import com.wini.owle.dto.user.TagDto
import com.wini.owle.dto.user.UserDto
import com.wini.owle.entity.user.TagDao
import com.wini.owle.entity.user.TagRelationDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.mockk
import org.jetbrains.exposed.dao.id.CompositeID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import io.mockk.slot
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class TagRelationsMapperTest {

    private lateinit var mapper: TagRelationsMapper
    private val factory: DaoFactory<TagRelationDao, CompositeID> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TagRelationsMapper(factory)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TagDto(
            id = 99L,
            name = "tagName",
            description = "tagDescription",
            user = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TagDao)
        every { TagDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Tag with id=99 not found", exception.message)
        unmockkObject(TagDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TagDao> {
            every { id.value } returns 1L
            every { name } returns "tagName"
            every { description } returns "tagDescription"
            every { user } returns mockk()
        }

        // Mock the behavior of usersMapper
        val userDto = mockk<UserDto>()
        every { usersMapper.toDto(entity.user) } returns userDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("tagName", result.name)
        assertEquals("tagDescription", result.description)
        assertEquals(userDto, result.user)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TagDto(id = 42L, name = "tagName", description = "tagDescription", user = mockk())

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TagDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(TagDao)
        every { TagDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val userSlot = slot<User>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs
        every { existingEntity.user = capture(userSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TagDao.findById(42L) }
        verify { existingEntity.name = "tagName" }
        verify { existingEntity.description = "tagDescription" }
        verify { existingEntity.user = dto.user }

        // Verify the captured values
        assertEquals("tagName", nameSlot.captured)
        assertEquals("tagDescription", descriptionSlot.captured)
        assertEquals(dto.user, userSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TagDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TagDto(id = 0L, name = "tagName", description = "tagDescription", user = mockk())

        // Mock the new entity that will be created
        val newEntity = mockk<TagDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val userSlot = slot<User>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs
        every { newEntity.user = capture(userSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "tagName" }
        verify { newEntity.description = "tagDescription" }
        verify { newEntity.user = dto.user }

        // Verify the captured values
        assertEquals("tagName", nameSlot.captured)
        assertEquals("tagDescription", descriptionSlot.captured)
        assertEquals(dto.user, userSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}