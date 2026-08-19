package com.wini.owle.schema.wrapper.annotation

import io.mockk.every
import io.mockk.mockk

import com.google.devtools.ksp.symbol.*
import com.wini.owle.ksp.utils.LocalizedStringResolver
import com.wini.owle.schema.wrapper.declaration.ApiOperationDeclarationImpl
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

open class ApiOperationAnnotationTest {

    private lateinit var mockKsAnnotation: KSAnnotation

    @BeforeEach
    fun setUp() {
        mockKsAnnotation = mockk<KSAnnotation>(relaxed = true)
        mockkObject(LocalizedStringResolver)
        // For the constructor test, we might need to mock the constructor of ApiOperationDeclarationImpl
        // or ensure its apiOpsAnnotation can be mocked when it's constructed.
        // For now, we focus on the primary constructor that takes KSAnnotation.
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

    @Test
    fun `primary constructor should correctly extract and resolve properties`() {
        // Arrange
        val opResKeyVal = "test.op.key"
        val methodVal = "com.wini.owle.schema.opmethod.GET"
        val resultVal = "com.wini.owle.schema.opresult.MANY"

        every { mockKsAnnotation.arguments } returns listOf(
            mockArgument("opResKey", opResKeyVal),
            mockArgument("method", methodVal),
            mockArgument("result", resultVal)
        )

        val expectedHttpMethod = "get"
        val expectedCardinality = "many"
        val expectedKey = "$opResKeyVal.$expectedHttpMethod.$expectedCardinality"

        val expectedPath = "/items"
        val expectedSummary = "Get all items"
        val expectedDescription = "Retrieves a list of all items."

        every { LocalizedStringResolver.resolve("$expectedKey.path") } returns expectedPath
        every { LocalizedStringResolver.resolve("$expectedKey.summary") } returns expectedSummary
        every { LocalizedStringResolver.resolve("$expectedKey.description") } returns expectedDescription

        // Act
        val annotationImpl = ApiOperationAnnotationImpl(mockKsAnnotation)

        // Assert
        assertEquals(opResKeyVal, annotationImpl.opResKey)
        assertEquals(expectedHttpMethod, annotationImpl.httpMethod)
        assertEquals(expectedCardinality, annotationImpl.cardinality)
        assertEquals(expectedPath, annotationImpl.path)
        assertEquals(expectedSummary, annotationImpl.summary)
        assertEquals(expectedDescription, annotationImpl.description)

        verify { LocalizedStringResolver.resolve("$expectedKey.path") }
        verify { LocalizedStringResolver.resolve("$expectedKey.summary") }
        verify { LocalizedStringResolver.resolve("$expectedKey.description") }
    }

    @Test
    fun `constructor with KSFunctionDeclaration should delegate to primary constructor`() {
        // This test is more complex due to the chain: KSFunctionDeclaration -> ApiOperationDeclarationImpl -> its KSAnnotation
        // We need to ensure that the KSAnnotation ultimately processed by the primary constructor is the one from the function.

        val mockFuncDecl = mockk<KSFunctionDeclaration>()
        val mockUnderlyingAnnotation = mockk<KSAnnotation>(relaxed = true)

        mockkConstructor(ApiOperationDeclarationImpl::class)
        // We need ApiOperationDeclarationImpl(mockFuncDecl).apiOpsAnnotation to return mockUnderlyingAnnotation
        // This means when ApiOperationDeclarationImpl is constructed, its .apiOpsAnnotation must be stubbed.
        // The most straightforward way is to ensure the mockFuncDecl, when processed by ApiOperationDeclarationImpl,
        // yields mockUnderlyingAnnotation.

        // ApiOperationDeclarationImpl internally does:
        // delegate.annotations.first { it.annotationType.resolve().declaration.qualifiedName?.asString() == ApiOperationDefinition.QUALIFIED_NAME }
        // So, mockFuncDecl.annotations should return a sequence containing mockUnderlyingAnnotation,
        // and mockUnderlyingAnnotation should match the qualified name.

        val targetAnnotationTypeRef = mockk<KSTypeReference>() // KSTypeReference
        val resolvedTargetType = mockk<KSType>()               // KSType returned by resolve()
        val targetAnnotationDecl = mockk<KSClassDeclaration>()

        every { mockUnderlyingAnnotation.annotationType } returns targetAnnotationTypeRef
        every { targetAnnotationTypeRef.resolve() } returns resolvedTargetType
        every { resolvedTargetType.declaration } returns targetAnnotationDecl
        // The QUALIFIED_NAME was removed from ApiOperationAnnotation. Using the actual string.
        // This string is used by ApiOperationDeclarationImpl to find the right annotation.
        every { targetAnnotationDecl.qualifiedName?.asString() } returns "com.wini.owle.schema.api.ApiOperationDefinition"

        every { mockFuncDecl.annotations } returns sequenceOf(mockUnderlyingAnnotation)

        // Setup mockUnderlyingAnnotation as in the primary constructor test to verify property access
        val opResKeyVal = "func.op.key"
        val methodVal = "com.wini.owle.schema.opmethod.POST"
        val resultVal = "com.wini.owle.schema.opresult.ONE"

        every { mockUnderlyingAnnotation.arguments } returns listOf(
            mockArgument("opResKey", opResKeyVal),
            mockArgument("method", methodVal),
            mockArgument("result", resultVal)
        )
        // No need to mock LocalizedStringResolver here as we're just testing if opResKey is read.

        // Act
        val annotationImplViaFunc = ApiOperationAnnotationImpl(mockFuncDecl)

        // Assert
        assertEquals(opResKeyVal, annotationImplViaFunc.opResKey)
        assertEquals("post", annotationImplViaFunc.httpMethod) // Also check a transformed property

        // Verify that the constructor of ApiOperationDeclarationImpl was called with mockFuncDecl
        verify { constructedWith<ApiOperationDeclarationImpl>(EqMatcher(mockFuncDecl)) }
    }
/*
    @Test
    fun `properties should throw NoSuchElementException if required arguments are missing`() {
        every { mockKsAnnotation.arguments } returns emptyList()
        val annotationImpl = ApiOperationAnnotationImpl(mockKsAnnotation)

        assertThrows(NoSuchElementException::class.java) { annotationImpl.opResKey }
        assertThrows(NoSuchElementException::class.java) { annotationImpl.httpMethod }
        assertThrows(NoSuchElementException::class.java) { annotationImpl.cardinality }

        // Path, summary, description will also fail because their key construction will fail due to above.
        // Test one of them to confirm.
        assertThrows(NoSuchElementException::class.java) { annotationImpl.path }
    }


*/
}
