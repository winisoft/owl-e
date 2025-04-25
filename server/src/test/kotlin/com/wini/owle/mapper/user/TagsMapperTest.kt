package com.wini.owle.mapper.user

import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.dto.user.User
import com.wini.owle.entity.user.tags.TagEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.mapper.DaoFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import io.mockk.slot
import io.mockk.just
import io.mockk.runs
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class TagsMapperTest {

    private lateinit var mapper: TagsMapper
    private val usersMapper: UsersMapper = mockk()
    private val factory: DaoFactory<TagEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TagsMapper(factory, usersMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = Tag(id = 99L, name = "tagName", description = "tagDescription", user = mockk())

        // Mock the behavior of findById to return null (not found)
        mockkObject(TagEntity)
        every { TagEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Tag with id=99 not found", exception.message)
        unmockkObject(TagEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TagEntity> {
            every { id.value } returns 1L
            every { name } returns "tagName"
            every { description } returns "tagDescription"
            every { userEntity } returns mockk()
        }

        // Mock the behavior of usersMapper
        val user = mockk<User>()
        every { usersMapper.toDto(entity.userEntity) } returns user

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("tagName", result.name)
        assertEquals("tagDescription", result.description)
        assertEquals(user, result.user)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = Tag(id = 42L, name = "tagName", description = "tagDescription", user = mockk())

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TagEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val userEntity: UserEntity = mockk()

        // Mock the behavior of usersMapper.toEntity
        every { usersMapper.toEntity(any()) } returns userEntity

        // Set up mocks for finding existing entity
        mockkObject(TagEntity)
        every { TagEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val userSlot = slot<UserEntity>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs
        every { existingEntity.userEntity = capture(userSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TagEntity.findById(42L) }
        verify { existingEntity.name = "tagName" }
        verify { existingEntity.description = "tagDescription" }
        verify { existingEntity.userEntity = userEntity }

        // Verify the captured values
        assertEquals("tagName", nameSlot.captured)
        assertEquals("tagDescription", descriptionSlot.captured)
        assertEquals(userEntity, userSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TagEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = Tag(id = 0L, name = "tagName", description = "tagDescription", user = mockk())

        // Mock the new entity that will be created
        val newEntity = mockk<TagEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val userEntity: UserEntity = mockk()

        // Mock the behavior of usersMapper.toEntity
        every { usersMapper.toEntity(any()) } returns userEntity

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val userSlot = slot<UserEntity>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs
        every { newEntity.userEntity = capture(userSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "tagName" }
        verify { newEntity.description = "tagDescription" }
        verify { newEntity.userEntity = userEntity }

        // Verify the captured values
        assertEquals("tagName", nameSlot.captured)
        assertEquals("tagDescription", descriptionSlot.captured)
        assertEquals(userEntity, userSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}