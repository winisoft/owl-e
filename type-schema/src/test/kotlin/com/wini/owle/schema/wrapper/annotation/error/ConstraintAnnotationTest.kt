package com.wini.owle.schema.wrapper.annotation.error

import com.google.devtools.ksp.symbol.*
import com.wini.owle.schema.wrapper.declaration.ConstrainedField
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

open class ConstraintAnnotationTest {

    private lateinit var fieldConstraintAnnotation: FieldConstraintAnnotation
    private lateinit var constrainedField: ConstrainedField

    @BeforeEach
    fun setUp() {
        fieldConstraintAnnotation = mockk()
        constrainedField = mockk()

        every { constrainedField.fieldConstraintAnnotations } returns listOf(fieldConstraintAnnotation)

    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test something`() {

    }

    private fun mockArgument(name: String?, value: Any?): KSValueArgument {
        val arg = mockk<KSValueArgument>()
        val ksName = if (name != null) mockk<KSName> { every { asString() } returns name } else null
        every { arg.name } returns ksName
        every { arg.value } returns value
        return arg
    }

    private fun mockKSType(qualifiedNameValue: String): KSType {
        val ksType = mockk<KSType>()
        val ksDeclaration = mockk<KSClassDeclaration>()
        val ksName = mockk<KSName>()
        every { ksType.declaration } returns ksDeclaration
        every { ksDeclaration.qualifiedName } returns ksName
        every { ksName.asString() } returns qualifiedNameValue
        return ksType
    }
/*
    @Test
    fun `attributes should contain all named arguments`() {
        val mockKsAnnotation = mockk<KSAnnotation>()
        val arg1 = mockArgument("message", "Error message")
        val arg2 = mockArgument("groups", emptyList<Any>())
        val unnamedArg = mockArgument(null, "someValue")

        every { mockKsAnnotation.arguments } returns listOf(arg1, arg2, unnamedArg)

        val constraintAnnotation = FieldConstraintAnnotationImpl(mockKsAnnotation)

        assertEquals(3, constraintAnnotation.attributes.size)
        assertEquals(arg1, constraintAnnotation.attributes["message"])
        assertEquals(arg2, constraintAnnotation.attributes["groups"])
        assertEquals(unnamedArg, constraintAnnotation.attributes["?"])
    }
*/
    @Test
    fun `allConstraintErrorFqns for simple constraint with errors argument`() {
        val mockKsAnnotation = mockk<KSAnnotation>()
        val errorType1 = mockKSType("com.example.Error1")
        val errorType2 = mockKSType("com.example.Error2")
        val errorsList = listOf(errorType1, "not-a-type", errorType2, null)
        val errorsArg = mockArgument("errors", errorsList)

        every { mockKsAnnotation.arguments } returns listOf(errorsArg)

        val constraintAnnotation = FieldConstraintAnnotationImpl(mockKsAnnotation)

        val fqns = constraintAnnotation.errorFqNames
        assertEquals(listOf("com.example.Error1", "com.example.Error2"), fqns)
    }

    @Test
    fun `allConstraintErrorFqns for constraint with single error argument`() {
        val mockKsAnnotation = mockk<KSAnnotation>()
        val errorType = mockKSType("com.example.SingleError")
        val errorArg = mockArgument("error", errorType)
        val otherArg = mockArgument("someOtherData", "data")

        every { mockKsAnnotation.arguments } returns listOf(errorArg, otherArg)

        val constraintAnnotation = FieldConstraintAnnotationImpl(mockKsAnnotation)

        val fqns = constraintAnnotation.errorFqNames
        assertEquals(listOf("com.example.SingleError"), fqns)
    }

    @Test
    fun `allConstraintErrorFqns should prioritize errors over error argument`() {
        val mockKsAnnotation = mockk<KSAnnotation>()
        val errorTypeSimple = mockKSType("com.example.SimpleError")
        val errorsList = listOf(errorTypeSimple)
        val errorsArg = mockArgument("errors", errorsList)

        val errorTypeSingle = mockKSType("com.example.SingleErrorShouldBeIgnored")
        val errorArg = mockArgument("error", errorTypeSingle)

        every { mockKsAnnotation.arguments } returns listOf(errorsArg, errorArg)

        val constraintAnnotation = FieldConstraintAnnotationImpl(mockKsAnnotation)
        val fqns = constraintAnnotation.errorFqNames
        assertEquals(listOf("com.example.SimpleError"), fqns, "Should use 'errors' if present")
    }
    /*
        @Test
        fun `allConstraintErrorFqns should return emptyList if no error or errors argument`() {
            val mockKsAnnotation = mockk<KSAnnotation>()
            val otherArg = mockArgument("message", "A message")
            every { mockKsAnnotation.arguments } returns listOf(otherArg)

            val constraintAnnotation = FieldConstraintAnnotationImpl(mockKsAnnotation)
            assertThrows(NoSuchElementException::class.java) {
                constraintAnnotation.errorFqNames
            }
        }

        @Test
        fun `allConstraintErrorFqns handles errors argument not being a list`() {
            val mockKsAnnotation = mockk<KSAnnotation>()
            val errorsArg = mockArgument("errors", "not-a-list")
            every { mockKsAnnotation.arguments } returns listOf(errorsArg)

            val constraintAnnotation = FieldConstraintAnnotationImpl(mockKsAnnotation)
            assertThrows(ClassCastException::class.java) {
                constraintAnnotation.errorFqNames
            }
        }
    */
    @Test
    fun `allConstraintErrorFqns handles error argument not being KSType`() {
        val mockKsAnnotation = mockk<KSAnnotation>()
        val errorArg = mockArgument("error", "not-a-kstype")
        every { mockKsAnnotation.arguments } returns listOf(errorArg)

        val constraintAnnotation = FieldConstraintAnnotationImpl(mockKsAnnotation)
        val fqns = constraintAnnotation.errorFqNames
        assertTrue(fqns.isEmpty(), "Should return empty list if single error value is not KSType or FQN is null")
    }
}
