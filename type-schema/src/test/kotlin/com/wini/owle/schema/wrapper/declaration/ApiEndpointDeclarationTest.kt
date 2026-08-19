package com.wini.owle.schema.wrapper.declaration

import io.mockk.every
import io.mockk.mockk

import com.google.devtools.ksp.symbol.*
import com.wini.owle.schema.api.ApiEndpointDefinition // For QUALIFIED_NAME
import com.wini.owle.schema.api.ApiOperationDefinition // For QUALIFIED_NAME
import com.wini.owle.schema.wrapper.annotation.ApiEndpointAnnotationImpl
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*


open class ApiEndpointDeclarationTest {

    private lateinit var mockKsClassDecl: KSClassDeclaration

    @BeforeEach
    fun setUp() {
        mockKsClassDecl = mockk<KSClassDeclaration>(relaxed = true)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    // Helper to mock a KSAnnotation with a specific qualified name
    private fun mockKSAnnotation(qualifiedName: String): KSAnnotation {
        val annotation = mockk<KSAnnotation>(relaxed = true) // relaxed = true for arguments if not specified
        val annotationType = mockk<KSTypeReference>()
        val resolvedType = mockk<KSType>()
        val declaration = mockk<KSClassDeclaration>()
        every { annotation.annotationType } returns annotationType
        every { annotationType.resolve() } returns resolvedType
        every { resolvedType.declaration } returns declaration
        every { declaration.qualifiedName?.asString() } returns qualifiedName
        return annotation
    }
/*
    @Test
    fun `apiEndpointAnnotation should correctly parse ApiEndpointDefinition annotation`() {
        // Arrange
        val endpointDefAnnotation = mockKSAnnotation(ApiEndpointDefinition.QUALIFIED_NAME)
        val otherAnnotation = mockKSAnnotation("com.example.OtherAnnotation")
        every { mockKsClassDecl.annotations } returns sequenceOf(otherAnnotation, endpointDefAnnotation)

        // Act
        val endpointDeclImpl = ApiEndpointDeclarationImpl(mockKsClassDecl)
        val actualAnnotation = endpointDeclImpl.apiEndpointAnnotation

        // Assert
        assertNotNull(actualAnnotation)
        assertTrue(actualAnnotation is ApiEndpointAnnotationImpl)
        assertEquals(endpointDefAnnotation, (actualAnnotation as ApiEndpointAnnotationImpl).delegate)
    }

    @Test
    fun `operations should contain ApiOperationDeclarations for annotated functions`() {
        // Arrange
        val func1 = mockk<KSFunctionDeclaration>(relaxed = true)
        val func2 = mockk<KSFunctionDeclaration>(relaxed = true) // Not annotated
        val func3 = mockk<KSFunctionDeclaration>(relaxed = true)

        val opAnnotationForFunc1 = mockKSAnnotation(ApiOperationDefinition.QUALIFIED_NAME)
        val opAnnotationForFunc3 = mockKSAnnotation(ApiOperationDefinition.QUALIFIED_NAME)

        every { func1.annotations } returns sequenceOf(opAnnotationForFunc1)
        every { func2.annotations } returns emptySequence() // No ApiOperationDefinition
        every { func3.annotations } returns sequenceOf(opAnnotationForFunc3)

        // Implementation uses getAllFunctions()
        every { mockKsClassDecl.getAllFunctions() } returns sequenceOf(func1, func2, func3)
        
        val endpointDefAnnotation = mockKSAnnotation(ApiEndpointDefinition.QUALIFIED_NAME)
        every { mockKsClassDecl.annotations } returns sequenceOf(endpointDefAnnotation)

        mockkConstructor(ApiOperationDeclarationImpl::class)

        // Act
        val endpointDeclImpl = ApiEndpointDeclarationImpl(mockKsClassDecl)
        val operations = endpointDeclImpl.operations

        // Assert
        assertEquals(2, operations.size)
        assertTrue(operations.all { it is ApiOperationDeclarationImpl })

        val opDelegates = operations.map { (it as ApiOperationDeclarationImpl).delegate }
        assertTrue(opDelegates.contains(func1))
        assertTrue(opDelegates.contains(func3))
        assertFalse(opDelegates.contains(func2))

        verify { constructedWith<ApiOperationDeclarationImpl>(EqMatcher(func1)) }
        verify { constructedWith<ApiOperationDeclarationImpl>(EqMatcher(func3)) }
        verify(exactly = 0) { constructedWith<ApiOperationDeclarationImpl>(EqMatcher(func2)) }
    }

    @Test
    fun `apiEndpointAnnotation should throw if ApiEndpointDefinition is missing`() {
        every { mockKsClassDecl.annotations } returns emptySequence() // No annotations
        val endpointDeclImpl = ApiEndpointDeclarationImpl(mockKsClassDecl)
        assertThrows(NoSuchElementException::class.java) {
            endpointDeclImpl.apiEndpointAnnotation // Accessing this will trigger .first
        }
    }


*/
}
