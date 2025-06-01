package com.wini.owle.schema.wrapper.annotation

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSValueArgument
import com.wini.owle.schema.wrapper.declaration.ModelDeclaration

/*
open class ModelAnnotationTest {

    private lateinit var modelAnnotation: ModelAnnotation
    private lateinit var modelDeclaration: ModelDeclaration

    @BeforeEach
    fun setUp() {
        modelAnnotation = mockk()
        modelDeclaration = mockk()

        every { modelDeclaration.modelAnnotation } returns modelAnnotation

    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test something`() {

    }

    private fun mockArgument(name: String, value: Any?): KSValueArgument {
        val arg = mockk<KSValueArgument>()
        every { arg.name?.asString() } returns name
        every { arg.value } returns value
        return arg
    }

    @Test
    fun `isSkipValidation should be true if skipValidation argument is true`() {
        val mockKsAnnotation = mockk<KSAnnotation>()
        val skipValidationArg = mockArgument("skipValidation", true)
        every { mockKsAnnotation.arguments } returns listOf(skipValidationArg)

        val modelAnnotation = ModelAnnotationImpl(mockKsAnnotation)

        assertTrue(modelAnnotation.isSkipValidation)
        assertFalse(modelAnnotation.isNotSkipValidation)
    }

    @Test
    fun `isSkipValidation should be false if skipValidation argument is false`() {
        val mockKsAnnotation = mockk<KSAnnotation>()
        val skipValidationArg = mockArgument("skipValidation", false)
        every { mockKsAnnotation.arguments } returns listOf(skipValidationArg)

        val modelAnnotation = ModelAnnotationImpl(mockKsAnnotation)

        assertFalse(modelAnnotation.isSkipValidation)
        assertTrue(modelAnnotation.isNotSkipValidation)
    }

    @Test
    fun `isSkipValidation should be false if skipValidation argument is not boolean true`() {
        val mockKsAnnotation = mockk<KSAnnotation>()
        val skipValidationArg = mockArgument("skipValidation", "not-true") // Different type
        every { mockKsAnnotation.arguments } returns listOf(skipValidationArg)

        val modelAnnotation = ModelAnnotationImpl(mockKsAnnotation)

        assertFalse(modelAnnotation.isSkipValidation)
        assertTrue(modelAnnotation.isNotSkipValidation)
    }

    @Test
    fun `isSkipValidation should be false if skipValidation argument is missing`() {
        val mockKsAnnotation = mockk<KSAnnotation>()
        every { mockKsAnnotation.arguments } returns emptyList() // No arguments

        val modelAnnotation = ModelAnnotationImpl(mockKsAnnotation)

        assertFalse(modelAnnotation.isSkipValidation)
        assertTrue(modelAnnotation.isNotSkipValidation)
    }

    @Test
    fun `isSkipValidation should be false if arguments list does not contain skipValidation`() {
        val mockKsAnnotation = mockk<KSAnnotation>()
        val otherArg = mockArgument("otherArg", true)
        every { mockKsAnnotation.arguments } returns listOf(otherArg) // skipValidation not present

        val modelAnnotation = ModelAnnotationImpl(mockKsAnnotation)

        assertFalse(modelAnnotation.isSkipValidation)
        assertTrue(modelAnnotation.isNotSkipValidation)
    }
}

 */