package com.wini.owle.ksp.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSName
import com.wini.owle.content.oas.ApiRouteDoc
import com.wini.owle.schema.wrapper.annotation.ApiEndpointAnnotation
import com.wini.owle.schema.wrapper.declaration.ApiEndpointDeclaration

/*
class ApiInterfaceProcessorTest {

    private lateinit var mockResolver: Resolver
    private lateinit var mockLogger: KSPLogger
    private lateinit var mockModels: Map<String, KSClassDeclaration> // Though not used in current process()

    private lateinit var processor: ApiInterfaceProcessor

    @BeforeEach
    fun setUp() {
        mockResolver = mockk()
        mockLogger = mockk(relaxed = true) // Relaxed to avoid mocking every logger call
        mockModels = mockk()

        // Mock the constructor of ApiMethodProcessor as it's instantiated internally
        mockkConstructor(ApiMethodProcessor::class)

        processor = ApiInterfaceProcessor(mockResolver, mockLogger, mockModels)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks() // Clears constructor mocks as well
    }

    @Test
    fun `process should correctly map data from EndpointDeclaration and its annotation`() {
        // 1. Arrange
        val mockApiEndpointDeclaration = mockk<ApiEndpointDeclaration>()
        val mockApiEndpointAnnotation = mockk<ApiEndpointAnnotation>()
        val mockKsName = mockk<KSName>()

        val endpointNameString = "TestEndpoint"
        val endpointDescription = "This is a test endpoint."
        val endpointBasePath = "/test/api"
        val endpointDtoType = "com.example.TestDto"
        val endpointTags = listOf("tag1", "tag2")

        // Mocking EndpointDeclaration
        // EndpointDeclaration extends KSClassDeclaration, so we need to mock simpleName
        every { mockApiEndpointDeclaration.simpleName } returns mockKsName
        every { mockKsName.asString() } returns endpointNameString
        every { mockApiEndpointDeclaration.apiEndpointAnnotation } returns mockApiEndpointAnnotation

        // Mocking ApiEndpointAnnotation (accessed via mockEndpointDeclaration.annotation)
        every { mockApiEndpointAnnotation.description } returns endpointDescription
        every { mockApiEndpointAnnotation.basePath } returns endpointBasePath
        every { mockApiEndpointAnnotation.dtoType } returns endpointDtoType
        every { mockApiEndpointAnnotation.tags } returns endpointTags

        // Mocking ApiMethodProcessor behavior
        val expectedRoutes = listOf(ApiRouteDoc(path = "/test/api/items", operations = emptyList()))
        every {
            anyConstructed<ApiMethodProcessor>().processRoutes(
                mockApiEndpointDeclaration
            )
        } returns expectedRoutes

        // 2. Act
        val resultDoc = processor.process(mockApiEndpointDeclaration)

        // 3. Assert
        assertNotNull(resultDoc)
        assertEquals(endpointNameString, resultDoc!!.name)
        assertEquals(endpointDescription, resultDoc.description)
        assertEquals(endpointBasePath, resultDoc.basePath)
        assertEquals(endpointDtoType, resultDoc.dtoType)
        assertEquals(endpointTags, resultDoc.tags)
        assertEquals(expectedRoutes, resultDoc.routes)

        // Verify that ApiMethodProcessor was constructed and its method was called correctly
        verify {
            anyConstructed<ApiMethodProcessor>().processRoutes(
                eq(mockApiEndpointDeclaration)
            )
        }
        // Verify logger calls (optional, depends on if ApiInterfaceProcessor does any logging)
        // For the current version of ApiInterfaceProcessor, no specific logging is done in the happy path.
        // verify(exactly = 0) { mockLogger.warn(any()) } // Example if no warnings are expected
        // verify(exactly = 0) { mockLogger.error(any()) } // Example if no errors are expected
    }

    @Test
    fun `process should handle empty routes from ApiMethodProcessor`() {
        // 1. Arrange
        val mockApiEndpointDeclaration = mockk<ApiEndpointDeclaration>()
        val mockApiEndpointAnnotation = mockk<ApiEndpointAnnotation>()
        val mockKsName = mockk<KSName>()

        every { mockApiEndpointDeclaration.simpleName } returns mockKsName
        every { mockKsName.asString() } returns "EndpointWithNoRoutes"
        every { mockApiEndpointDeclaration.apiEndpointAnnotation } returns mockApiEndpointAnnotation

        every { mockApiEndpointAnnotation.description } returns "Desc"
        every { mockApiEndpointAnnotation.basePath } returns "/base"
        every { mockApiEndpointAnnotation.dtoType } returns "Dto"
        every { mockApiEndpointAnnotation.tags } returns emptyList()

        val emptyRoutes = emptyList<ApiRouteDoc>()
        every {
            anyConstructed<ApiMethodProcessor>().processRoutes(mockApiEndpointDeclaration)
        } returns emptyRoutes

        // 2. Act
        val resultDoc = processor.process(mockApiEndpointDeclaration)

        // 3. Assert
        assertNotNull(resultDoc)
        assertEquals("EndpointWithNoRoutes", resultDoc!!.name)
        assertTrue(resultDoc.routes.isEmpty())
    }

    // Add more tests if there are specific edge cases or error conditions to be handled
    // by ApiInterfaceProcessor itself. Given its current simplicity, most error handling
    // would likely be in the EndpointDeclarationImpl or ApiEndpointAnnotationImpl,
    // or in ApiMethodProcessor.
}


 */