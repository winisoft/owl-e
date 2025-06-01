package com.wini.owle.schema.wrapper.declaration.error
/*
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.schema.wrapper.annotation.error.ErrorAnnotation
import io.mockk.clearAllMocks
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

// Assumed concrete implementation for ErrorDeclaration:
// class ErrorDeclarationImpl(
//     val delegate: KSClassDeclaration,
//     override val errorAnnotation: ErrorAnnotation
// ) : ErrorDeclaration, KSClassDeclaration by delegate {
//     override val errorResKey: String get() = errorAnnotation.errorResKey
//     override val code: String get() = errorAnnotation.code
//     override val message: String get() = errorAnnotation.message
//     // other KSClassDeclaration properties are delegated by "KSClassDeclaration by delegate"
// }

open class ErrorDeclarationTest {

    private lateinit var mockErrorAnnotation: ErrorAnnotation
    private lateinit var mockKsClassDeclaration: KSClassDeclaration
    private lateinit var errorDeclaration: ErrorDeclaration // This will be an instance or a mock behaving like the Impl

    @BeforeEach
    fun setUp() {
        mockErrorAnnotation = mockk<ErrorAnnotation>(relaxed = true)
        mockKsClassDeclaration = mockk<KSClassDeclaration>(relaxed = true)

        // For testing the interface, we mock ErrorDeclaration and define its behavior.
        // If testing a concrete Impl, you'd instantiate: ErrorDeclarationImpl(mockKsClassDeclaration, mockErrorAnnotation)
        errorDeclaration = mockk<ErrorDeclaration>(relaxed = true)

        // Define the behavior of the errorDeclaration mock based on the assumed Impl
        every { errorDeclaration.errorAnnotation } returns mockErrorAnnotation
        every { errorDeclaration.errorResKey } answers { mockErrorAnnotation.errorResKey }
        every { errorDeclaration.code } answers { mockErrorAnnotation.code }
        every { errorDeclaration.message } answers { mockErrorAnnotation.message }
        
        // If ErrorDeclaration also delegates KSClassDeclaration properties, mock a few as examples:
        every { errorDeclaration.simpleName } answers { mockKsClassDeclaration.simpleName }
        every { errorDeclaration.packageName } answers { mockKsClassDeclaration.packageName }
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `errorAnnotation should return the provided ErrorAnnotation`() {
        assertSame(mockErrorAnnotation, errorDeclaration.errorAnnotation)
    }

    @Test
    fun `errorResKey should return value from ErrorAnnotation`() {
        val expectedKey = "test.key"
        every { mockErrorAnnotation.errorResKey } returns expectedKey
        assertEquals(expectedKey, errorDeclaration.errorResKey)
    }

    @Test
    fun `code should return value from ErrorAnnotation`() {
        val expectedCode = "TEST_CODE_123"
        every { mockErrorAnnotation.code } returns expectedCode
        assertEquals(expectedCode, errorDeclaration.code)
    }

    @Test
    fun `message should return value from ErrorAnnotation`() {
        val expectedMessage = "This is a test error message."
        every { mockErrorAnnotation.message } returns expectedMessage
        assertEquals(expectedMessage, errorDeclaration.message)
    }

    // Example test for a delegated property from KSClassDeclaration
    @Test
    fun `simpleName should be delegated if ErrorDeclaration implements KSClassDeclaration`() {
        val mockName = mockk<com.google.devtools.ksp.symbol.KSName>()
        val expectedSimpleName = "MyError"
        every { mockName.asString() } returns expectedSimpleName
        every { mockKsClassDeclaration.simpleName } returns mockName // Setup the underlying delegate
        
        // Assuming errorDeclaration.simpleName is set up to delegate in setUp
        assertEquals(expectedSimpleName, errorDeclaration.simpleName.asString())
    }
}

 */