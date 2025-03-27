package com.wini.owle.mapper

import com.wini.owle.ExposedTestUtils
import com.wini.owle.dto.BaseDto
import io.mockk.*
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertFailsWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseMapperUnitTest<Dto : BaseDto, T : Entity<Long>, M : DbMapper<Dto, T>> {

    /**
     * The mapper under test - will be properly mocked
     */
    protected lateinit var mapper: M
    
    /**
     * Mock transaction for testing
     */
    protected val mockTransaction: Transaction = mockk(relaxed = true)
    
    /**
     * Abstract methods to be implemented by test classes
     */
    abstract fun createMapperWithMocks(): M
    abstract fun createNewDto(): Dto
    abstract fun createExistingDto(id: Long): Dto
    abstract fun createMockEntity(id: Long): T
    abstract fun assertEntityMatchesDto(entity: T, dto: Dto)
    abstract fun assertDtoMatchesEntity(dto: Dto, entity: T)
    
    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        
        // Set up the mock transaction context
        ExposedTestUtils.setupMockTransactions()
        
        mapper = createMapperWithMocks()
    }
    
    @Test
    @DisplayName("Maps new Dto to Entity")
    fun `should map new Dto to Entity`() {
        // Arrange
        val dto = createNewDto()
        val mockEntity = createMockEntity(0)
        setupMockForNewEntity(dto, mockEntity)
        
        // Act
        val result = mapper.run { dto.toEntity() }
        
        // Assert
        assertEntityMatchesDto(result, dto)
    }
    
    @Test
    @DisplayName("Maps existing Dto to Entity")
    fun `should map existing Dto to Entity`() {
        // Arrange
        val existingId = 1L
        val dto = createExistingDto(existingId)
        val mockEntity = createMockEntity(existingId)
        setupMockForExistingEntity(dto, mockEntity)
        
        // Act
        val result = mapper.run { dto.toEntity() }
        
        // Assert 
        assertEntityMatchesDto(result, dto)
    }
    
    @Test
    @DisplayName("Maps Entity to Dto")
    fun `should map Entity to Dto`() {
        // Arrange
        val entity = createMockEntity(1)
        
        // Act
        val dto = mapper.run { entity.toDto() }
        
        // Assert
        assertDtoMatchesEntity(dto, entity)
    }
    
    @Test
    @DisplayName("Fails mapping non-existent Entity")
    fun `should throw exception when mapping to non-existent entity`() {
        // Arrange
        val nonExistentId = 9999L
        val dto = createExistingDto(nonExistentId)
        setupMockForNonExistentEntity(dto)
        
        // Act & Assert
        assertFailsWith<IllegalStateException> {
            mapper.run { dto.toEntity() }
        }
    }
    
    /**
     * Override these methods in your test class if you need custom mocking behavior
     */
    protected open fun setupMockForNewEntity(dto: Dto, entity: T) {
        // To be implemented by specific test classes
    }
    
    protected open fun setupMockForExistingEntity(dto: Dto, entity: T) {
        // To be implemented by specific test classes
    }
    
    protected open fun setupMockForNonExistentEntity(dto: Dto) {
        // To be implemented by specific test classes
    }
}