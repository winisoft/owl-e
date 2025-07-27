package com.wini.owle.schema.wrapper.annotation

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueArgument
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSName
import com.wini.owle.ksp.utils.LocalizedStringResolver
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

open class ApiEndpointAnnotationTest {

    private lateinit var mockKsAnnotation: KSAnnotation

    @BeforeEach
    fun setUp() {
        mockKsAnnotation = mockk<KSAnnotation>(relaxed = true)
        mockkObject(LocalizedStringResolver)
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(LocalizedStringResolver)
        clearAllMocks()
    }

    private fun mockArgument(name: String, value: Any?): KSValueArgument {
        val arg = mockk<KSValueArgument>()
        every { arg.name?.asString() } returns name
        every { arg.value } returns value
        return arg
    }

    private fun mockKSType(qualifiedNameValue: String): KSType {
        val ksType = mockk<KSType>()
        val ksDeclaration = mockk<KSDeclaration>()
        val ksName = mockk<KSName>()
        every { ksType.declaration } returns ksDeclaration
        every { ksDeclaration.qualifiedName } returns ksName
        every { ksName.asString() } returns qualifiedNameValue
        return ksType
    }

    @Test
    fun `ApiEndpointAnnotationImpl should correctly extract properties from KSAnnotation`() {
        // Arrange
        val idArgValue = "test.endpoint.id"
        val tag1Type = mockKSType("com.example.Tag1")
        val tag2Type = mockKSType("com.example.Tag2")
        val tagsArgValue = listOf(tag1Type, tag2Type, null)

        every { mockKsAnnotation.arguments } returns listOf(
            mockArgument("id", idArgValue),
            mockArgument("tags", tagsArgValue)
        )

        val expectedDtoType = "com.example.Dto"
        val expectedBasePath = "/api/test"
        val expectedSummary = "Test Endpoint Summary"
        val expectedDescription = "Detailed description of the test endpoint."

        every { LocalizedStringResolver.resolve("$idArgValue.dtoType") } returns expectedDtoType
        every { LocalizedStringResolver.resolve("$idArgValue.basePath") } returns expectedBasePath
        every { LocalizedStringResolver.resolve("$idArgValue.summary") } returns expectedSummary
        every { LocalizedStringResolver.resolve("$idArgValue.description") } returns expectedDescription

        // Act
        val annotationImpl = ApiEndpointAnnotationImpl(mockKsAnnotation)

        // Assert
        assertEquals(idArgValue, annotationImpl.id)
        assertEquals(listOf("com.example.Tag1", "com.example.Tag2"), annotationImpl.tags)
        assertEquals(expectedDtoType, annotationImpl.dtoType)
        assertEquals(expectedBasePath, annotationImpl.basePath)
        assertEquals(expectedSummary, annotationImpl.summary)
        assertEquals(expectedDescription, annotationImpl.description)
    }

    @Test
    fun `tags property should be empty if tags argument is missing`() {
        // Arrange
        val idArgValue = "test.no.tags.arg"
        every { mockKsAnnotation.arguments } returns listOf(mockArgument("id", idArgValue))
        // "tags" argument is missing

        // Act
        val annotationImpl = ApiEndpointAnnotationImpl(mockKsAnnotation)

        // Assert
        assertTrue(annotationImpl.tags.isEmpty(), "Tags should be empty if argument is missing")
    }

    @Test
    fun `tags property should be empty if tags argument value is not a List`() {
        // Arrange
        val idArgValue = "test.bad.tags.type"
        every { mockKsAnnotation.arguments } returns listOf(
            mockArgument("id", idArgValue),
            mockArgument("tags", "this-is-not-a-list") // Value is not a List
        )

        // Act
        val annotationImpl = ApiEndpointAnnotationImpl(mockKsAnnotation)

        // Assert
        assertTrue(annotationImpl.tags.isEmpty(), "Tags should be empty if value is not a List")
    }

    @Test
    fun `tags property should be empty if tags argument value is an empty List`() {
        // Arrange
        val idArgValue = "test.empty.tags.list"
        every { mockKsAnnotation.arguments } returns listOf(
            mockArgument("id", idArgValue),
            mockArgument("tags", emptyList<Any?>()) // Value is an empty List
        )

        // Act
        val annotationImpl = ApiEndpointAnnotationImpl(mockKsAnnotation)

        // Assert
        assertTrue(annotationImpl.tags.isEmpty(), "Tags should be empty if value is an empty list")
    }

    @Test
    fun `id property should throw exception if id argument is missing`() {
        // Arrange: No 'id' argument
        every { mockKsAnnotation.arguments } returns emptyList()

        // Act & Assert
        assertThrows(NoSuchElementException::class.java) {
            ApiEndpointAnnotationImpl(mockKsAnnotation).id // Accessing id will trigger .first on arguments
        }
    }
    // The test for ClassCastException on tags is removed as the implementation now handles it gracefully.
}