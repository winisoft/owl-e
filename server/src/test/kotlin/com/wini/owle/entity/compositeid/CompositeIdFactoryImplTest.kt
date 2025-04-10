package com.wini.owle.entity.compositeid

import com.wini.owle.dto.BaseDto
import com.wini.owle.entity.composteid.CompositeIdFactoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.spyk
import io.mockk.unmockkObject
import io.mockk.verify
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class CompositeIdFactoryImplTest {

    // Create a concrete implementation of CompositeIdFactoryImpl for testing
    private class TestCompositeIdFactoryImpl : CompositeIdFactoryImpl<TestDto>() {
        override val fkReferences: Map<Column<EntityID<Long>>, String> = mapOf(
            mockk<Column<EntityID<Long>>>() to "id1",
            mockk<Column<EntityID<Long>>>() to "id2"
        )

        override fun fromDtoInstance(dto: TestDto): CompositeID {
            val id1 = dto.id1
            val id2 = dto.id2

            return CompositeID {
                for ((entityIdColumn, columnName) in fkReferences) {
                    when (columnName) {
                        "id1" -> it[entityIdColumn] = id1
                        "id2" -> it[entityIdColumn] = id2
                    }
                }
            }
        }
    }

    // Simple DTO for testing
    private class TestDto : BaseDto {
        var id1: EntityID<Long> = mockk()
        var id2: EntityID<Long> = mockk()
    }

    private lateinit var factory: TestCompositeIdFactoryImpl
    private lateinit var mockColumns: Map<Column<EntityID<Long>>, String>

    @BeforeEach
    fun setup() {
        mockkObject(CompositeID.Companion)
        
        factory = spyk(TestCompositeIdFactoryImpl())
        mockColumns = factory.fkReferences
        
        // Setup mockk behavior for CompositeID creation
        for ((column, _) in mockColumns) {
            every { column.table } returns mockk()
            every { column.name } returns "column"
        }
    }
    
    @AfterEach
    fun tearDown() {
        unmockkObject(CompositeID.Companion)
    }

    @Test
    fun `fromRequestParams - when params are missing altogether - returns null`() {
        val params = mapOf<String, List<String>>()
        val result = factory.fromRequestParams(params)
        assertNull(result)
    }

    @Test
    fun `fromRequestParams - where parameters have no values for required keys - returns null`() {
        val params = mapOf(
            "id1" to emptyList<String>(),
            "id2" to emptyList<String>()
        )
        val result = factory.fromRequestParams(params)
        assertNull(result)
    }

    @Test
    fun `fromRequestParams - passed params with invalid values - returns null`() {
        val params = mapOf(
            "id1" to listOf("123"),
            "id2" to listOf("not-a-number")
        )
        val result = factory.fromRequestParams(params)
        assertNull(result)
    }

    @Test
    fun `fromRequestParams - not provided all required params - should return null`() {
        val params = mapOf(
            "id1" to listOf("123")
            // id2 is missing
        )
        val result = factory.fromRequestParams(params)
        assertNull(result)
    }

    @Test
    fun `fromRequestParams - provided valid params - creates valid CompositeID`() {
        // Setup
        val params = mapOf(
            "id1" to listOf("123"),
            "id2" to listOf("456")
        )
        val expectedValues = mapOf(
            "id1" to 123L,
            "id2" to 456L
        )
        
        // Mock the CompositeID creation
        val mockCompositeId = mockk<CompositeID>()
        val lambdaSlot = slot<CompositeID.() -> Unit>()
        
        every { 
            CompositeID(capture(lambdaSlot)) 
        } answers {
            mockCompositeId.apply(lambdaSlot.captured)
            mockCompositeId
        }
        
        // For each column in the factory, set up a capture for the assignment
        for ((column, columnName) in mockColumns) {
            every { 
                mockCompositeId[column] = expectedValues[columnName]!! 
            } returns mockk()
        }
        
        // Execute
        val result = factory.fromRequestParams(params)
        
        // Verify
        assertEquals(mockCompositeId, result)
    }
    
    @Test
    fun `fromDtoInstance - when DTO has null ID fields - returns null `() {
        val dto = TestDto() // All fields are null
        val result = factory.fromDtoInstance(dto)
        assertNull(result)
    }
    
    @Test
    fun `fromDtoInstance - when any required DTO ID field is null - returns null`() {
        val dto = TestDto().apply {
            id1 = mockk {
                every { value } returns 123L
            }
            // id2 is null
        }
        val result = factory.fromDtoInstance(dto)
        assertNull(result)
    }
    
    @Test
    fun `fromDtoInstance - passed a valid DTO - returns appropriate CompositeID`() {
        // Setup
        val dto = TestDto().apply {
            id1 = mockk {
                every { value } returns 123L
            }
            id2 = mockk {
                every { value } returns 456L
            }
        }
        
        // Mock the CompositeID creation
        val mockCompositeId = mockk<CompositeID>()
        val lambdaSlot = slot<CompositeID.() -> Unit>()
        
        every { 
            CompositeID(capture(lambdaSlot)) 
        } answers {
            mockCompositeId.apply(lambdaSlot.captured)
            mockCompositeId
        }
        
        // For each column in the factory, set up a capture for the assignment
        for ((column, columnName) in mockColumns) {
            every { 
                mockCompositeId[column] = any<Long>() 
            } returns mockk()
        }
        
        // Execute
        val result = factory.fromDtoInstance(dto)
        
        // Verify
        assertEquals(mockCompositeId, result)
        
        // Verify that the ID values were assigned in the lambda
        for ((column, columnName) in mockColumns) {
            verify { 
                mockCompositeId[column] = when (columnName) {
                    "id1" -> 123L
                    "id2" -> 456L
                    else -> any()
                }
            }
        }
    }
}