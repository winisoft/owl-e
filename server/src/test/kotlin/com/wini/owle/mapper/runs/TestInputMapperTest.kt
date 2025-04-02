package com.wini.owle.mapper.runs

import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class TestInputMapperTest {

    private lateinit var mapper: TestInputsMapper
    private val testCasesMapper: TestCasesMapper = mockk()
    private val factory: DaoFactory<TestInputDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TestInputsMapper(factory, testCasesMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs

        // Mock the nested dao member(s) that would be created from the DTO

        // Mock the behavior of findById to return null (not found)

        // Verify that the exception is thrown with the correct message

    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects

        // Mock toDto behavior of the mapper

        // Call the method under test

        // Verify the result
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs

        // Mock the existing entity that will be updated

        // Mock the VersionSpecDao that would be returned from the mapper

        // Set up mocks for finding existing entity

        // Define slots to capture property assignments

        // Capture the property updates

        // Execute the method under test

        // Verify that the existing entity was found and updated

        // Verify the captured values

        // Verify the result is the updated entity

    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs

        // Mock the new entity that will be created

        // Mock nested entity type(s) that would be returned from the mapper

        // Set up the factory mock to return our mock entity

        // Define slots to capture property assignments

        // Set up property capture

        // Execute the method under test

        // Verify that the factory was used to create a new entity

        // Verify the properties were set correctly

        // Verify the captured values

        // Verify the result is the new entity
    }
}