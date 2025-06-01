package com.wini.owle.schema.wrapper.annotation.error
/*
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSValueArgument
import com.wini.owle.ksp.utils.LocalizedStringResolver
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

open class ErrorAnnotationTest {

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

    @Test
    fun `properties should be correctly extracted and resolved`() {
        // Arrange
        val errorResKeyVal = "error.generic.notFound"
        val expectedCode = "ERR_404"
        val expectedMessage = "The requested resource was not found."

        every { mockKsAnnotation.arguments } returns listOf(
            mockArgument("errorResKey", errorResKeyVal)
        )

        every { LocalizedStringResolver.resolve("$errorResKeyVal.code") } returns expectedCode
        every { LocalizedStringResolver.resolve("$errorResKeyVal.message") } returns expectedMessage

        // Act
        val errorAnnotation = ErrorAnnotationImpl(mockKsAnnotation)

        // Assert
        assertEquals(errorResKeyVal, errorAnnotation.errorResKey)
        assertEquals(expectedCode, errorAnnotation.code)
        assertEquals(expectedMessage, errorAnnotation.message)

        verify { LocalizedStringResolver.resolve("$errorResKeyVal.code") }
        verify { LocalizedStringResolver.resolve("$errorResKeyVal.message") }
    }
/*
    @Test
    fun `errorResKey should throw NoSuchElementException if argument is missing`() {
        // Arrange
        every { mockKsAnnotation.arguments } returns emptyList()
        val errorAnnotation = ErrorAnnotationImpl(mockKsAnnotation)

        // Act & Assert
        assertThrows(NoSuchElementException::class.java) {
            errorAnnotation.errorResKey // Accessing this will trigger .first
        }
    }
*/
    @Test
    fun `code and message resolution should be lazy`() {
        // Arrange
        val errorResKeyVal = "error.lazy.test"
        every { mockKsAnnotation.arguments } returns listOf(
            mockArgument("errorResKey", errorResKeyVal)
        )

        // LocalizedStringResolver is not mocked yet for these specific keys
        val errorAnnotation = ErrorAnnotationImpl(mockKsAnnotation)

        // Assert that resolve hasn't been called yet
        verify(exactly = 0) { LocalizedStringResolver.resolve("$errorResKeyVal.code") }
        verify(exactly = 0) { LocalizedStringResolver.resolve("$errorResKeyVal.message") }

        // Act: Access properties
        every { LocalizedStringResolver.resolve("$errorResKeyVal.code") } returns "CODE"
        assertEquals("CODE", errorAnnotation.code) // First access
        assertEquals("CODE", errorAnnotation.code) // Second access (should use cached value)

        every { LocalizedStringResolver.resolve("$errorResKeyVal.message") } returns "MESSAGE"
        assertEquals("MESSAGE", errorAnnotation.message) // First access
        assertEquals("MESSAGE", errorAnnotation.message) // Second access

        // Verify that resolve was called only once for each
        verify(exactly = 1) { LocalizedStringResolver.resolve("$errorResKeyVal.code") }
        verify(exactly = 1) { LocalizedStringResolver.resolve("$errorResKeyVal.message") }
    }
}

 */