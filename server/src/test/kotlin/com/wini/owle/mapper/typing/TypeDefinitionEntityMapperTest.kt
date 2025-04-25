package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeDefinition
import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.entity.environs.VersionSpecEntity
import com.wini.owle.entity.typing.TypeDefinitionEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.environs.VersionSpecsMapper
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


open class TypeDefinitionEntityMapperTest {

    private lateinit var mapper: TypeDefinitionsMapper
    private val versionSpecsMapper: VersionSpecsMapper = mockk()
    private val factory: DaoFactory<TypeDefinitionEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TypeDefinitionsMapper(factory, versionSpecsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TypeDefinition(
            id = 99L,
            name = "definitionName",
            namespace = "namespace",
            versionSpec = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TypeDefinitionEntity)
        every { TypeDefinitionEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TypeDefinition with id=99 not found", exception.message)
        unmockkObject(TypeDefinitionEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TypeDefinitionEntity> {
            every { id.value } returns 1L
            every { name } returns "definitionName"
            every { namespace } returns "namespace"
            every { versionSpec } returns mockk()
        }

        // Mock the behavior of versionSpecsMapper
        val versionSpec = mockk<VersionSpec>()
        every { versionSpecsMapper.toDto(entity.versionSpec) } returns versionSpec

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("definitionName", result.name)
        assertEquals("namespace", result.namespace)
        assertEquals(versionSpec, result.versionSpec)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TypeDefinition(id = 42L, name = "definitionName", namespace = "namespace", versionSpec = mockk())

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TypeDefinitionEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val versionSpecEntity: VersionSpecEntity = mockk()

        // Mock the behavior of versionSpecsMapper.toEntity
        every { versionSpecsMapper.toEntity(any()) } returns versionSpecEntity

        // Set up mocks for finding existing entity
        mockkObject(TypeDefinitionEntity)
        every { TypeDefinitionEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val namespaceSlot = slot<String>()
        val versionSpecSlot = slot<VersionSpecEntity>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.namespace = capture(namespaceSlot) } just runs
        every { existingEntity.versionSpec = capture(versionSpecSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TypeDefinitionEntity.findById(42L) }
        verify { existingEntity.name = "definitionName" }
        verify { existingEntity.namespace = "namespace" }
        verify { existingEntity.versionSpec = versionSpecEntity }

        // Verify the captured values
        assertEquals("definitionName", nameSlot.captured)
        assertEquals("namespace", namespaceSlot.captured)
        assertEquals(versionSpecEntity, versionSpecSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TypeDefinitionEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TypeDefinition(
            id = 0L,
            name = "definitionName",
            namespace = "namespace",
            versionSpec = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TypeDefinitionEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val versionSpecEntity: VersionSpecEntity = mockk()

        // Mock the behavior of versionSpecsMapper.toEntity
        every { versionSpecsMapper.toEntity(any()) } returns versionSpecEntity

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val namespaceSlot = slot<String>()
        val versionSpecSlot = slot<VersionSpecEntity>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.namespace = capture(namespaceSlot) } just runs
        every { newEntity.versionSpec = capture(versionSpecSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "definitionName" }
        verify { newEntity.namespace = "namespace" }
        verify { newEntity.versionSpec = versionSpecEntity }

        // Verify the captured values
        assertEquals("definitionName", nameSlot.captured)
        assertEquals("namespace", namespaceSlot.captured)
        assertEquals(versionSpecEntity, versionSpecSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}