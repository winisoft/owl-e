package com.wini.owle.ksp.processor

import io.mockk.every
import io.mockk.mockk

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSName
import com.wini.owle.content.oas.ApiResponseDoc
import com.wini.owle.ksp.utils.ResponseBuilder
import com.wini.owle.schema.wrapper.annotation.ApiOperationAnnotation
import com.wini.owle.schema.wrapper.declaration.ApiEndpointDeclaration
import com.wini.owle.schema.wrapper.declaration.ApiOperationDeclaration

/*
class ApiMethodProcessorTest {

    private lateinit var mockResolver: Resolver
    private lateinit var mockLogger: KSPLogger
    private lateinit var processor: ApiMethodProcessor

    @BeforeEach
    fun setUp() {
        mockResolver = mockk()
        mockLogger = mockk(relaxed = true) // Relaxed to avoid mocking every logger call

        // Mock the static ResponseBuilder.build method
        mockkObject(ResponseBuilder)

        processor = ApiMethodProcessor(mockLogger)
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(ResponseBuilder)
        clearAllMocks()
    }

    private fun mockOperation(
        path: String, summary: String, description: String,
        httpMethod: String, cardinality: String, opResKey: String = "test.opResKey"
    ): ApiOperationDeclaration {
        val mockOpDecl = mockk<ApiOperationDeclaration>()
        val mockOpAnnotation = mockk<ApiOperationAnnotation>()

        every { mockOpDecl.apiOpsAnnotation } returns mockOpAnnotation
        every { mockOpAnnotation.path } returns path
        every { mockOpAnnotation.summary } returns summary
        every { mockOpAnnotation.description } returns description
        every { mockOpAnnotation.httpMethod } returns httpMethod
        every { mockOpAnnotation.cardinality } returns cardinality
        every { mockOpAnnotation.opResKey } returns opResKey

        return mockOpDecl
    }
/*
    @Test
    fun `processRoutes should correctly process a single operation`() {
        // Arrange
        val mockEndpointDecl = mockk<ApiEndpointDeclaration>()
        val mockKsName = mockk<KSName>()
        every { mockEndpointDecl.simpleName } returns mockKsName
        every { mockKsName.asString() } returns "TestEndpoint"

        val op1 = mockOperation("/items", "Get items", "Fetch all items", "get", "many")
        every { mockEndpointDecl.operations } returns listOf(op1)

        val expectedResponses = mapOf(200 to ApiResponseDoc("OK"))
        every { ResponseBuilder.build("get", "array") } returns expectedResponses

        // Act
        val routes = processor.processRoutes(mockEndpointDecl)

        // Assert
        assertEquals(1, routes.size)
        val routeDoc = routes[0]
        assertEquals("/items", routeDoc.path)
        assertEquals(1, routeDoc.operations.size)

        val operationDoc = routeDoc.operations[0]
        assertEquals("get", operationDoc.method)
        assertEquals("Get items", operationDoc.summary)
        assertEquals("Fetch all items", operationDoc.description)
        assertEquals(expectedResponses, operationDoc.responses)
        assertTrue(operationDoc.tags.isEmpty()) // Default tags are empty

        verify { ResponseBuilder.build("get", "array") }
        verify { mockLogger.warn("ApiMethodProcessor::processRoutes for Endpoint [TestEndpoint]") }
    }
*/
    @Test
    fun `processRoutes should group multiple operations under the same path`() {
        // Arrange
        val mockEndpointDecl = mockk<ApiEndpointDeclaration>(relaxed = true)
        every { mockEndpointDecl.simpleName.asString() } returns "TestEndpoint"

        val op1 = mockOperation("/items", "Get items", "Fetch all items", "get", "many")
        val op2 = mockOperation("/items", "Create item", "Add new item", "post", "one")
        every { mockEndpointDecl.operations } returns listOf(op1, op2)

        every { ResponseBuilder.build("get", "array") } returns mapOf(200 to ApiResponseDoc("OK"))
        every { ResponseBuilder.build("post", "object") } returns mapOf(201 to ApiResponseDoc("Created"))

        // Act
        val routes = processor.processRoutes(mockEndpointDecl)

        // Assert
        assertEquals(1, routes.size)
        val routeDoc = routes[0]
        assertEquals("/items", routeDoc.path)
        assertEquals(2, routeDoc.operations.size)
        assertTrue(routeDoc.operations.any { it.method == "get" && it.summary == "Get items" })
        assertTrue(routeDoc.operations.any { it.method == "post" && it.summary == "Create item" })
    }

    @Test
    fun `processRoutes should handle multiple operations with different paths`() {
        // Arrange
        val mockEndpointDecl = mockk<ApiEndpointDeclaration>(relaxed = true)
        every { mockEndpointDecl.simpleName.asString() } returns "TestEndpoint"

        val op1 = mockOperation("/items", "Get items", "Fetch all items", "get", "many")
        val op2 = mockOperation("/users", "Get users", "Fetch all users", "get", "many")
        every { mockEndpointDecl.operations } returns listOf(op1, op2)

        every { ResponseBuilder.build("get", "array") } returns mapOf(200 to ApiResponseDoc("OK"))

        // Act
        val routes = processor.processRoutes(mockEndpointDecl)

        // Assert
        assertEquals(2, routes.size)
        assertTrue(routes.any { it.path == "/items" && it.operations.size == 1 && it.operations[0].method == "get" })
        assertTrue(routes.any { it.path == "/users" && it.operations.size == 1 && it.operations[0].method == "get" })
    }

    @Test
    fun `processRoutes should correctly determine responseType from cardinality`() {
        // Arrange
        val mockEndpointDecl = mockk<ApiEndpointDeclaration>(relaxed = true)
        every { mockEndpointDecl.simpleName.asString() } returns "TestEndpoint"

        val opMany = mockOperation("/many", "Test Many", "Desc", "get", "many", "op.many")
        val opOne = mockOperation("/one", "Test One", "Desc", "get", "one", "op.one")
        val opUnknown = mockOperation("/unknown", "Test Unknown", "Desc", "get", "weird", "op.unknown")
        every { mockEndpointDecl.operations } returns listOf(opMany, opOne, opUnknown)

        every { ResponseBuilder.build(any(), any()) } returns emptyMap() // Generic mock for this test

        // Act
        processor.processRoutes(mockEndpointDecl)

        // Assert
        verify { ResponseBuilder.build("get", "array") } // For opMany
        verify { ResponseBuilder.build("get", "object") } // For opOne
        verify { ResponseBuilder.build("get", null) }     // For opUnknown
        verify { mockLogger.warn("Unknown cardinality 'weird' for operation op.unknown. Defaulting response type.") }
    }

    @Test
    fun `processRoutes should return empty list if no operations are declared`() {
        // Arrange
        val mockEndpointDecl = mockk<ApiEndpointDeclaration>(relaxed = true)
        every { mockEndpointDecl.simpleName.asString() } returns "TestEndpoint"
        every { mockEndpointDecl.operations } returns emptyList()

        // Act
        val routes = processor.processRoutes(mockEndpointDecl)

        // Assert
        assertTrue(routes.isEmpty())
        verify(exactly = 0) { ResponseBuilder.build(any(), any()) }
    }
}

 */