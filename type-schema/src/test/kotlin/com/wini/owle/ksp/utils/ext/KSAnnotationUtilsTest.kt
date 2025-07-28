package com.wini.owle.ksp.utils.ext

import com.google.devtools.ksp.symbol.*
import com.wini.owle.ksp.utils.ConstraintRegistry
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class KSAnnotationUtilsTest {

    private lateinit var ksAnnotation: KSAnnotation
    private lateinit var ksAnnotated: KSAnnotated
    private lateinit var ksTypeReference: KSTypeReference
    private lateinit var ksType: KSType
    private lateinit var ksDeclaration: KSDeclaration
    private lateinit var ksName: KSName
    private lateinit var ksValueArgument: KSValueArgument

    @BeforeEach
    fun setUp() {
        ksAnnotation = mockk()
        ksAnnotated = mockk()
        ksTypeReference = mockk()
        ksType = mockk()
        ksDeclaration = mockk()
        ksName = mockk()
        ksValueArgument = mockk()

        every { ksAnnotation.annotationType } returns ksTypeReference
        every { ksTypeReference.resolve() } returns ksType
        every { ksType.declaration } returns ksDeclaration
        every { ksDeclaration.qualifiedName } returns ksName
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class IsOwleModelTest {
        @Test
        fun `isOwleModel should return true when qualified name matches`() {
            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel"
            assertTrue(ksAnnotation.isOwleModel)
        }

        @Test
        fun `isOwleModel should return false when qualified name does not match`() {
            every { ksName.asString() } returns "com.example.OtherAnnotation"
            assertFalse(ksAnnotation.isOwleModel)
        }

        @Test
        fun `isOwleModel should return false when qualified name is null`() {
            every { ksDeclaration.qualifiedName } returns null
            assertFalse(ksAnnotation.isOwleModel)
        }
    }

    @Nested
    inner class IsSkipValidationTest {
        @Test
        fun `isSkipValidation should return true when OwleModel and skipValidation is true`() {
            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel" // isOwleModel = true
            val argName = mockk<KSName>()
            every { argName.asString() } returns "skipValidation"
            every { ksValueArgument.name } returns argName
            every { ksValueArgument.value } returns true
            every { ksAnnotation.arguments } returns listOf(ksValueArgument)

            assertTrue(ksAnnotation.isSkipValidation)
        }

        @Test
        fun `isSkipValidation should return false when OwleModel and skipValidation is false`() {
            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel" // isOwleModel = true
            val argName = mockk<KSName>()
            every { argName.asString() } returns "skipValidation"
            every { ksValueArgument.name } returns argName
            every { ksValueArgument.value } returns false
            every { ksAnnotation.arguments } returns listOf(ksValueArgument)

            assertFalse(ksAnnotation.isSkipValidation)
        }

        @Test
        fun `isSkipValidation should return false when OwleModel and skipValidation argument is missing`() {
            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel" // isOwleModel = true
            every { ksAnnotation.arguments } returns emptyList()

            assertFalse(ksAnnotation.isSkipValidation)
        }

        @Test
        fun `isSkipValidation should return false when not OwleModel`() {
            every { ksName.asString() } returns "com.example.OtherAnnotation" // isOwleModel = false
            // Arguments don't matter if it's not an OwleModel
            every { ksAnnotation.arguments } returns emptyList()
            assertFalse(ksAnnotation.isSkipValidation)
        }
    }

    @Nested
    inner class IsNotSkipValidationTest {
        @Test
        fun `isNotSkipValidation should return false when isSkipValidation is true`() {
            // Mock isSkipValidation to return true
            // This is a bit indirect, but tests the logic correctly
            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel"
            val argName = mockk<KSName>()
            every { argName.asString() } returns "skipValidation"
            every { ksValueArgument.name } returns argName
            every { ksValueArgument.value } returns true
            every { ksAnnotation.arguments } returns listOf(ksValueArgument)

            assertFalse(ksAnnotation.isNotSkipValidation)
        }

        @Test
        fun `isNotSkipValidation should return true when isSkipValidation is false`() {
            // Mock isSkipValidation to return false
            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel"
            every { ksAnnotation.arguments } returns emptyList() // No skipValidation argument

            assertTrue(ksAnnotation.isNotSkipValidation)
        }
    }


    @Nested
    inner class GetTest {
        @Test
        fun `get should return value when argument name matches`() {
            val argName = mockk<KSName>()
            every { argName.asString() } returns "testArg"
            every { ksValueArgument.name } returns argName
            every { ksValueArgument.value } returns "testValue"
            every { ksAnnotation.arguments } returns listOf(ksValueArgument)

            assertEquals("testValue", ksAnnotation.get("testArg"))
        }

        @Test
        fun `get should return null when argument name does not match`() {
            val argName = mockk<KSName>()
            every { argName.asString() } returns "otherArg"
            every { ksValueArgument.name } returns argName
            every { ksValueArgument.value } returns "testValue"
            every { ksAnnotation.arguments } returns listOf(ksValueArgument)

            assertNull(ksAnnotation.get("testArg"))
        }

        @Test
        fun `get should return null when no arguments present`() {
            every { ksAnnotation.arguments } returns emptyList()
            assertNull(ksAnnotation.get("testArg"))
        }
    }

    @Nested
    inner class FindAnnotationTest {
        @Test
        fun `findAnnotation should return annotation when fqName matches`() {
            val targetFqName = "com.example.TargetAnnotation"
            every { ksName.asString() } returns targetFqName
            // ksAnnotation is already set up with ksName
            every { ksAnnotated.annotations } returns sequenceOf(ksAnnotation)

            assertSame(ksAnnotation, ksAnnotated.findAnnotation(targetFqName))
        }

        @Test
        fun `findAnnotation should return null when fqName does not match`() {
            every { ksName.asString() } returns "com.example.OtherAnnotation"
            every { ksAnnotated.annotations } returns sequenceOf(ksAnnotation)

            assertNull(ksAnnotated.findAnnotation("com.example.TargetAnnotation"))
        }

        @Test
        fun `findAnnotation should return null when no annotations present`() {
            every { ksAnnotated.annotations } returns emptySequence()
            assertNull(ksAnnotated.findAnnotation("com.example.TargetAnnotation"))
        }

        @Test
        fun `findAnnotation should return null when declaration qualified name is null`() {
            val otherAnnotation = mockk<KSAnnotation>()
            val otherTypeRef = mockk<KSTypeReference>()
            val otherType = mockk<KSType>()
            val otherDeclaration = mockk<KSDeclaration>()

            every { otherAnnotation.annotationType } returns otherTypeRef
            every { otherTypeRef.resolve() } returns otherType
            every { otherType.declaration } returns otherDeclaration
            every { otherDeclaration.qualifiedName } returns null // This is the key part

            every { ksAnnotated.annotations } returns sequenceOf(otherAnnotation)
            assertNull(ksAnnotated.findAnnotation("com.example.TargetAnnotation"))
        }
    }

    @Nested
    inner class IsOwleApiAnnotationTest {
        @Test
        fun `isOwleApiAnnotation should return true for matching prefix`() {
            every { ksName.asString() } returns "com.wini.owle.schema.api.OwleApiGet"
            assertTrue(ksAnnotation.isOwleApiAnnotation())
        }

        @Test
        fun `isOwleApiAnnotation should return false for non-matching prefix`() {
            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel"
            assertFalse(ksAnnotation.isOwleApiAnnotation())
        }

        @Test
        fun `isOwleApiAnnotation should return false for different prefix`() {
            every { ksName.asString() } returns "com.example.api.MyApi"
            assertFalse(ksAnnotation.isOwleApiAnnotation())
        }

        @Test
        fun `isOwleApiAnnotation should return false when qualified name is null`() {
            every { ksDeclaration.qualifiedName } returns null
            assertFalse(ksAnnotation.isOwleApiAnnotation())
        }
    }

    @Nested
    inner class IsOwleErrorTest {
        @Test
        fun `isOwleError should return true for matching prefix`() {
            every { ksName.asString() } returns "com.wini.owle.schema.error.MyError"
            assertTrue(ksAnnotation.isOwleError())
        }

        @Test
        fun `isOwleError should return false for non-matching prefix`() {
            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel"
            assertFalse(ksAnnotation.isOwleError())
        }

        @Test
        fun `isOwleError should return false for different prefix`() {
            every { ksName.asString() } returns "com.example.error.AnotherError"
            assertFalse(ksAnnotation.isOwleError())
        }

        @Test
        fun `isOwleError should return false when qualified name is null`() {
            every { ksDeclaration.qualifiedName } returns null
            assertFalse(ksAnnotation.isOwleError())
        }
    }

    @Nested
    inner class GetArgumentTest {
        @Test
        fun `getArgument should return typed value when argument name matches and type is correct`() {
            val argName = mockk<KSName>()
            every { argName.asString() } returns "testArg"
            every { ksValueArgument.name } returns argName
            every { ksValueArgument.value } returns "testValue"
            every { ksAnnotation.arguments } returns listOf(ksValueArgument)

            assertEquals("testValue", ksAnnotation.getArgument<String>("testArg"))
        }

        @Test
        fun `getArgument should return null when argument name matches but type is incorrect`() {
            val argName = mockk<KSName>()
            every { argName.asString() } returns "testArg"
            every { ksValueArgument.name } returns argName
            every { ksValueArgument.value } returns 123 // Integer value
            every { ksAnnotation.arguments } returns listOf(ksValueArgument)

            assertNull(ksAnnotation.getArgument<String>("testArg")) // Expecting String
        }

        @Test
        fun `getArgument should return null when argument name does not match`() {
            val argName = mockk<KSName>()
            every { argName.asString() } returns "otherArg"
            every { ksValueArgument.name } returns argName
            every { ksValueArgument.value } returns "testValue"
            every { ksAnnotation.arguments } returns listOf(ksValueArgument)

            assertNull(ksAnnotation.getArgument<String>("testArg"))
        }

        @Test
        fun `getArgument should return null when no arguments present`() {
            every { ksAnnotation.arguments } returns emptyList()
            assertNull(ksAnnotation.getArgument<String>("testArg"))
        }

        @Test
        fun `getArgument should work with different types`() {
            val argName = mockk<KSName>()
            every { argName.asString() } returns "intArg"
            every { ksValueArgument.name } returns argName
            every { ksValueArgument.value } returns 42
            every { ksAnnotation.arguments } returns listOf(ksValueArgument)

            assertEquals(42, ksAnnotation.getArgument<Int>("intArg"))
        }
    }

    @Nested
    inner class FilterIsFieldConstraintTest {
        @Test
        fun `filterIsFieldConstraint should return only constraint annotations`() {
            val constraintAnnotation = mockk<KSAnnotation>("constraintAnnotation")
            val nonConstraintAnnotation = mockk<KSAnnotation>("nonConstraintAnnotation")

            val constraintTypeRef = mockk<KSTypeReference>()
            val constraintType = mockk<KSType>()
            val constraintDeclaration = mockk<KSDeclaration>()
            every { constraintAnnotation.annotationType } returns constraintTypeRef
            every { constraintTypeRef.resolve() } returns constraintType
            every { constraintType.declaration } returns constraintDeclaration
            every { constraintDeclaration.isConstraintDecor } returns true // Key mock

            val nonConstraintTypeRef = mockk<KSTypeReference>()
            val nonConstraintType = mockk<KSType>()
            val nonConstraintDeclaration = mockk<KSDeclaration>()
            every { nonConstraintAnnotation.annotationType } returns nonConstraintTypeRef
            every { nonConstraintTypeRef.resolve() } returns nonConstraintType
            every { nonConstraintType.declaration } returns nonConstraintDeclaration
            every { nonConstraintDeclaration.isConstraintDecor } returns false // Key mock

            val annotations = listOf(constraintAnnotation, nonConstraintAnnotation)
            val filtered = annotations.filterIsFieldConstraint()

            assertEquals(1, filtered.size)
            assertSame(constraintAnnotation, filtered.first())
        }

        @Test
        fun `filterIsFieldConstraint should return empty list if no constraint annotations`() {
            val nonConstraintAnnotation1 = mockk<KSAnnotation>("nonConstraintAnnotation1")
            val nonConstraintAnnotation2 = mockk<KSAnnotation>("nonConstraintAnnotation2")

            // Mock both to not be constraint decors
            val typeRef1 = mockk<KSTypeReference>()
            val type1 = mockk<KSType>()
            val declaration1 = mockk<KSDeclaration>()
            every { nonConstraintAnnotation1.annotationType } returns typeRef1
            every { typeRef1.resolve() } returns type1
            every { type1.declaration } returns declaration1
            every { declaration1.isConstraintDecor } returns false

            val typeRef2 = mockk<KSTypeReference>()
            val type2 = mockk<KSType>()
            val declaration2 = mockk<KSDeclaration>()
            every { nonConstraintAnnotation2.annotationType } returns typeRef2
            every { typeRef2.resolve() } returns type2
            every { type2.declaration } returns declaration2
            every { declaration2.isConstraintDecor } returns false


            val annotations = listOf(nonConstraintAnnotation1, nonConstraintAnnotation2)
            val filtered = annotations.filterIsFieldConstraint()

            assertTrue(filtered.isEmpty())
        }

        @Test
        fun `filterIsFieldConstraint should return empty list for empty input`() {
            val annotations = emptyList<KSAnnotation>()
            val filtered = annotations.filterIsFieldConstraint()
            assertTrue(filtered.isEmpty())
        }
    }

    @Nested
    inner class FindConstraintRaisedErrorIdTest {
        private lateinit var constraintAnnotation: KSAnnotation
        private lateinit var errorClassDeclaration: KSClassDeclaration
        private lateinit var errorAnnotation: KSAnnotation
        private lateinit var constraintKsName: KSName
        private lateinit var errorKsName: KSName
        private lateinit var errorIdArgName: KSName
        private lateinit var constraintDeclaration: KSDeclaration


        @BeforeEach
        fun nestedSetUp() {
            constraintAnnotation = mockk(name = "constraintAnnotation")
            errorClassDeclaration = mockk(name = "errorClassDeclaration")
            errorAnnotation = mockk(name = "errorAnnotation")
            constraintKsName = mockk(name = "constraintKsName")
            errorKsName = mockk(name = "errorKsName")
            errorIdArgName = mockk(name = "errorIdArgName")
            constraintDeclaration = mockk(name = "constraintDeclaration") // Use KSDeclaration

            val constraintTypeRef = mockk<KSTypeReference>(name = "constraintTypeRef")
            val constraintType = mockk<KSType>(name = "constraintType")


            every { constraintAnnotation.annotationType } returns constraintTypeRef
            every { constraintTypeRef.resolve() } returns constraintType
            every { constraintType.declaration } returns constraintDeclaration // Now a KSDeclaration
            every { constraintDeclaration.qualifiedName } returns constraintKsName


            val errorClassQualifiedName = mockk<KSName>(name = "errorClassQualifiedName")
            every { errorClassDeclaration.qualifiedName } returns errorClassQualifiedName
            every { errorClassDeclaration.annotations } returns sequenceOf(errorAnnotation)

            val errorAnnotationTypeRef = mockk<KSTypeReference>(name = "errorAnnotationTypeRef")
            val errorAnnotationType = mockk<KSType>(name = "errorAnnotationType")
            val errorAnnotationDeclaration = mockk<KSDeclaration>(name = "errorAnnotationDeclaration")
            val errorAnnotationQualifiedName = mockk<KSName>(name = "errorAnnotationQualifiedName")

            every { errorAnnotation.annotationType } returns errorAnnotationTypeRef
            every { errorAnnotationTypeRef.resolve() } returns errorAnnotationType
            every { errorAnnotationType.declaration } returns errorAnnotationDeclaration
            every { errorAnnotationDeclaration.qualifiedName } returns errorAnnotationQualifiedName
            every { errorAnnotationQualifiedName.asString() } returns "com.wini.owle.schema.error.SomeError" // isOwleError = true

            val errorIdValueArgument = mockk<KSValueArgument>(name = "errorIdValueArgument")
            every { errorIdValueArgument.name } returns errorIdArgName
            every { errorIdArgName.asString() } returns "id"
            every { errorIdValueArgument.value } returns "ERROR_ID_123"
            every { errorAnnotation.arguments } returns listOf(errorIdValueArgument)

            // Mock ConstraintRegistry
            mockkObject(ConstraintRegistry)
        }

        @AfterEach
        fun nestedTearDown() {
            clearAllMocks() // Clears object mocks too
        }

        @Test
        fun `should return error ID when all conditions are met`() {
            val constraintFqn = "com.example.Constraint"
            val errorFqn = "com.wini.owle.schema.error.SomeError"

            every { constraintKsName.asString() } returns constraintFqn
            every { ConstraintRegistry.getErrorQualifiedName(constraintFqn) } returns errorFqn
            every { errorClassDeclaration.qualifiedName?.asString() } returns errorFqn // Make sure this matches


            val classDeclarations = listOf(errorClassDeclaration)
            val errorId = classDeclarations.findConstraintRaisedErrorId(constraintAnnotation)

            assertEquals("ERROR_ID_123", errorId)
        }

        @Test
        fun `should throw IllegalStateException when constraint FQN is null`() {
            every { constraintDeclaration.qualifiedName } returns null // Constraint FQN is null

            val classDeclarations = listOf(errorClassDeclaration)
            val exception = assertThrows(IllegalStateException::class.java) {
                classDeclarations.findConstraintRaisedErrorId(constraintAnnotation)
            }
            assertEquals("Could not find a KSClassDeclaration for a registered FieldConstraint Anno & raised OwleError pair", exception.message)
        }

        @Test
        fun `should throw IllegalStateException when error FQN is not registered`() {
            val constraintFqn = "com.example.UnregisteredConstraint"
            every { constraintKsName.asString() } returns constraintFqn
            every { ConstraintRegistry.getErrorQualifiedName(constraintFqn) } returns null // Not registered

            val classDeclarations = listOf(errorClassDeclaration)
            val exception = assertThrows(IllegalStateException::class.java) {
                classDeclarations.findConstraintRaisedErrorId(constraintAnnotation)
            }
            assertEquals("Could not find a KSClassDeclaration for a registered FieldConstraint Anno & raised OwleError pair", exception.message)
        }

        @Test
        fun `should throw IllegalStateException when matching error class not found`() {
            val constraintFqn = "com.example.Constraint"
            val errorFqn = "com.wini.owle.schema.error.SomeError"
            val otherErrorFqn = "com.wini.owle.schema.error.OtherError"


            every { constraintKsName.asString() } returns constraintFqn
            every { ConstraintRegistry.getErrorQualifiedName(constraintFqn) } returns errorFqn
            // Mock errorClassDeclaration to have a different FQN
             every { errorClassDeclaration.qualifiedName?.asString() } returns otherErrorFqn


            val classDeclarations = listOf(errorClassDeclaration) // Contains a class that doesn't match errorFqn
            val exception = assertThrows(IllegalStateException::class.java) {
                classDeclarations.findConstraintRaisedErrorId(constraintAnnotation)
            }
            assertEquals("Could not find a KSClassDeclaration for a registered FieldConstraint Anno & raised OwleError pair", exception.message)
        }

        @Test
        fun `should throw IllegalStateException when error class has no OwleError annotation`() {
            val constraintFqn = "com.example.Constraint"
            val errorFqn = "com.wini.owle.schema.error.SomeError"

            every { constraintKsName.asString() } returns constraintFqn
            every { ConstraintRegistry.getErrorQualifiedName(constraintFqn) } returns errorFqn
            every { errorClassDeclaration.qualifiedName?.asString() } returns errorFqn
            every { errorClassDeclaration.annotations } returns emptySequence() // No OwleError annotation

            val classDeclarations = listOf(errorClassDeclaration)
            val exception = assertThrows(IllegalStateException::class.java) {
                classDeclarations.findConstraintRaisedErrorId(constraintAnnotation)
            }
            assertEquals("Could not find a KSClassDeclaration for a registered FieldConstraint Anno & raised OwleError pair", exception.message)
        }

        @Test
        fun `should throw IllegalStateException when OwleError annotation has no id argument`() {
            val constraintFqn = "com.example.Constraint"
            val errorFqn = "com.wini.owle.schema.error.SomeError"

            every { constraintKsName.asString() } returns constraintFqn
            every { ConstraintRegistry.getErrorQualifiedName(constraintFqn) } returns errorFqn
            every { errorClassDeclaration.qualifiedName?.asString() } returns errorFqn
            every { errorAnnotation.arguments } returns emptyList() // No 'id' argument

            val classDeclarations = listOf(errorClassDeclaration)
            val exception = assertThrows(IllegalStateException::class.java) {
                classDeclarations.findConstraintRaisedErrorId(constraintAnnotation)
            }
            assertEquals("Could not find a KSClassDeclaration for a registered FieldConstraint Anno & raised OwleError pair", exception.message)
        }
         @Test
        fun `should throw IllegalStateException when OwleError annotation id argument is null`() {
            val constraintFqn = "com.example.Constraint"
            val errorFqn = "com.wini.owle.schema.error.SomeError"

            every { constraintKsName.asString() } returns constraintFqn
            every { ConstraintRegistry.getErrorQualifiedName(constraintFqn) } returns errorFqn
            every { errorClassDeclaration.qualifiedName?.asString() } returns errorFqn
            
            val errorIdValueArgument = mockk<KSValueArgument>(name = "errorIdValueArgument")
            val argName = mockk<KSName>()
            every { argName.asString() } returns "id"
            every { errorIdValueArgument.name } returns argName
            every { errorIdValueArgument.value } returns null // Value is null
            every { errorAnnotation.arguments } returns listOf(errorIdValueArgument)


            val classDeclarations = listOf(errorClassDeclaration)
            val exception = assertThrows(IllegalStateException::class.java) {
                classDeclarations.findConstraintRaisedErrorId(constraintAnnotation)
            }
            assertEquals("Could not find a KSClassDeclaration for a registered FieldConstraint Anno & raised OwleError pair", exception.message)
        }
    }
}