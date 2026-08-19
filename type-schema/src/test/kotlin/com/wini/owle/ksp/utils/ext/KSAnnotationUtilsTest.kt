package com.wini.owle.ksp.utils.ext

import io.mockk.every
import io.mockk.mockk

import com.google.devtools.ksp.symbol.*
import com.wini.owle.ksp.utils.ConstraintRegistry

//
//class KSAnnotationUtilsTest {
//
//    private lateinit var ksAnnotation: KSAnnotation
//    private lateinit var ksAnnotated: KSAnnotated
//    private lateinit var ksTypeReference: KSTypeReference
//    private lateinit var ksType: KSType
//    private lateinit var ksDeclaration: KSDeclaration
//    private lateinit var ksName: KSName
//    private lateinit var ksValueArgument: KSValueArgument
//
//    @BeforeEach
//    fun setUp() {
//        ksAnnotation = mockk()
//        ksAnnotated = mockk()
//        ksTypeReference = mockk()
//        ksType = mockk()
//        ksDeclaration = mockk()
//        ksName = mockk()
//        ksValueArgument = mockk()
//
//        every { ksAnnotation.annotationType } returns ksTypeReference
//        every { ksTypeReference.resolve() } returns ksType
//        every { ksType.declaration } returns ksDeclaration
//        every { ksDeclaration.qualifiedName } returns ksName
//    }
//
//    @AfterEach
//    fun tearDown() {
//        unmockkAll()
//    }
//
//    @Nested
//    inner class IsSkipValidationTest {
//        @Test
//        fun `isSkipValidation should return true when OwleModel and skipValidation is true`() {
//            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel" // isOwleModel = true
//            val argName = mockk<KSName>()
//            every { argName.asString() } returns "skipValidation"
//            every { ksValueArgument.name } returns argName
//            every { ksValueArgument.value } returns true
//            every { ksAnnotation.arguments } returns listOf(ksValueArgument)
//
//            assertTrue(ksAnnotation.isSkipValidation)
//        }
//
//        @Test
//        fun `isSkipValidation should return false when OwleModel and skipValidation is false`() {
//            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel" // isOwleModel = true
//            val argName = mockk<KSName>()
//            every { argName.asString() } returns "skipValidation"
//            every { ksValueArgument.name } returns argName
//            every { ksValueArgument.value } returns false
//            every { ksAnnotation.arguments } returns listOf(ksValueArgument)
//
//            assertFalse(ksAnnotation.isSkipValidation)
//        }
//
//        @Test
//        fun `isSkipValidation should return false when OwleModel and skipValidation argument is missing`() {
//            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel" // isOwleModel = true
//            every { ksAnnotation.arguments } returns emptyList()
//
//            assertFalse(ksAnnotation.isSkipValidation)
//        }
//
//        @Test
//        fun `isSkipValidation should return false when not OwleModel`() {
//            every { ksName.asString() } returns "com.example.OtherAnnotation" // isOwleModel = false
//            // Arguments don't matter if it's not an OwleModel
//            every { ksAnnotation.arguments } returns emptyList()
//            assertFalse(ksAnnotation.isSkipValidation)
//        }
//    }
//
//    @Nested
//    inner class IsNotSkipValidationTest {
//        @Test
//        fun `isNotSkipValidation should return false when isSkipValidation is true`() {
//            // Mock isSkipValidation to return true
//            // This is a bit indirect, but tests the logic correctly
//            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel"
//            val argName = mockk<KSName>()
//            every { argName.asString() } returns "skipValidation"
//            every { ksValueArgument.name } returns argName
//            every { ksValueArgument.value } returns true
//            every { ksAnnotation.arguments } returns listOf(ksValueArgument)
//
//            assertFalse(ksAnnotation.isNotSkipValidation)
//        }
//
//        @Test
//        fun `isNotSkipValidation should return true when isSkipValidation is false`() {
//            // Mock isSkipValidation to return false
//            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel"
//            every { ksAnnotation.arguments } returns emptyList() // No skipValidation argument
//
//            assertTrue(ksAnnotation.isNotSkipValidation)
//        }
//    }
//
//
//    @Nested
//    inner class GetTest {
//        @Test
//        fun `get should return value when argument name matches`() {
//            val argName = mockk<KSName>()
//            every { argName.asString() } returns "testArg"
//            every { ksValueArgument.name } returns argName
//            every { ksValueArgument.value } returns "testValue"
//            every { ksAnnotation.arguments } returns listOf(ksValueArgument)
//
//            assertEquals("testValue", ksAnnotation.get("testArg"))
//        }
//
//        @Test
//        fun `get should return null when argument name does not match`() {
//            val argName = mockk<KSName>()
//            every { argName.asString() } returns "otherArg"
//            every { ksValueArgument.name } returns argName
//            every { ksValueArgument.value } returns "testValue"
//            every { ksAnnotation.arguments } returns listOf(ksValueArgument)
//
//            assertNull(ksAnnotation.get("testArg"))
//        }
//
//        @Test
//        fun `get should return null when no arguments present`() {
//            every { ksAnnotation.arguments } returns emptyList()
//            assertNull(ksAnnotation.get("testArg"))
//        }
//    }
//
//    @Nested
//    inner class FindAnnotationTest {
//        @Test
//        fun `findAnnotation should return annotation when fqName matches`() {
//            val targetFqName = "com.example.TargetAnnotation"
//            every { ksName.asString() } returns targetFqName
//            // ksAnnotation is already set up with ksName
//            every { ksAnnotated.annotations } returns sequenceOf(ksAnnotation)
//
//            assertSame(ksAnnotation, ksAnnotated.findAnnotation(targetFqName))
//        }
//
//        @Test
//        fun `findAnnotation should return null when fqName does not match`() {
//            every { ksName.asString() } returns "com.example.OtherAnnotation"
//            every { ksAnnotated.annotations } returns sequenceOf(ksAnnotation)
//
//            assertNull(ksAnnotated.findAnnotation("com.example.TargetAnnotation"))
//        }
//
//        @Test
//        fun `findAnnotation should return null when no annotations present`() {
//            every { ksAnnotated.annotations } returns emptySequence()
//            assertNull(ksAnnotated.findAnnotation("com.example.TargetAnnotation"))
//        }
//
//        @Test
//        fun `findAnnotation should return null when declaration qualified name is null`() {
//            val otherAnnotation = mockk<KSAnnotation>()
//            val otherTypeRef = mockk<KSTypeReference>()
//            val otherType = mockk<KSType>()
//            val otherDeclaration = mockk<KSDeclaration>()
//
//            every { otherAnnotation.annotationType } returns otherTypeRef
//            every { otherTypeRef.resolve() } returns otherType
//            every { otherType.declaration } returns otherDeclaration
//            every { otherDeclaration.qualifiedName } returns null // This is the key part
//
//            every { ksAnnotated.annotations } returns sequenceOf(otherAnnotation)
//            assertNull(ksAnnotated.findAnnotation("com.example.TargetAnnotation"))
//        }
//    }
//
//    @Nested
//    inner class IsOwleApiAnnotationTest {
//        @Test
//        fun `isOwleApiAnnotation should return true for matching prefix`() {
//            every { ksName.asString() } returns "com.wini.owle.schema.api.OwleApiGet"
//            assertTrue(ksAnnotation.isOwleApiAnnotation())
//        }
//
//        @Test
//        fun `isOwleApiAnnotation should return false for non-matching prefix`() {
//            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel"
//            assertFalse(ksAnnotation.isOwleApiAnnotation())
//        }
//
//        @Test
//        fun `isOwleApiAnnotation should return false for different prefix`() {
//            every { ksName.asString() } returns "com.example.api.MyApi"
//            assertFalse(ksAnnotation.isOwleApiAnnotation())
//        }
//
//        @Test
//        fun `isOwleApiAnnotation should return false when qualified name is null`() {
//            every { ksDeclaration.qualifiedName } returns null
//            assertFalse(ksAnnotation.isOwleApiAnnotation())
//        }
//    }
//
//    @Nested
//    inner class IsOwleErrorTest {
//        @Test
//        fun `isOwleError should return true for matching prefix`() {
//            every { ksName.asString() } returns "com.wini.owle.schema.error.MyError"
//            assertTrue(ksAnnotation.isErrorDefinition())
//        }
//
//        @Test
//        fun `isOwleError should return false for non-matching prefix`() {
//            every { ksName.asString() } returns "com.wini.owle.schema.OwleModel"
//            assertFalse(ksAnnotation.isErrorDefinition())
//        }
//
//        @Test
//        fun `isOwleError should return false for different prefix`() {
//            every { ksName.asString() } returns "com.example.error.AnotherError"
//            assertFalse(ksAnnotation.isErrorDefinition())
//        }
//
//        @Test
//        fun `isOwleError should return false when qualified name is null`() {
//            every { ksDeclaration.qualifiedName } returns null
//            assertFalse(ksAnnotation.isErrorDefinition())
//        }
//    }
//
//    @Nested
//    inner class GetArgumentTest {
//        @Test
//        fun `getArgument should return typed value when argument name matches and type is correct`() {
//            val argName = mockk<KSName>()
//            every { argName.asString() } returns "testArg"
//            every { ksValueArgument.name } returns argName
//            every { ksValueArgument.value } returns "testValue"
//            every { ksAnnotation.arguments } returns listOf(ksValueArgument)
//
//            assertEquals("testValue", ksAnnotation.getArgument<String>("testArg"))
//        }
//
//        @Test
//        fun `getArgument should return null when argument name matches but type is incorrect`() {
//            val argName = mockk<KSName>()
//            every { argName.asString() } returns "testArg"
//            every { ksValueArgument.name } returns argName
//            every { ksValueArgument.value } returns 123 // Integer value
//            every { ksAnnotation.arguments } returns listOf(ksValueArgument)
//
//            assertNull(ksAnnotation.getArgument<String>("testArg")) // Expecting String
//        }
//
//        @Test
//        fun `getArgument should return null when argument name does not match`() {
//            val argName = mockk<KSName>()
//            every { argName.asString() } returns "otherArg"
//            every { ksValueArgument.name } returns argName
//            every { ksValueArgument.value } returns "testValue"
//            every { ksAnnotation.arguments } returns listOf(ksValueArgument)
//
//            assertNull(ksAnnotation.getArgument<String>("testArg"))
//        }
//
//        @Test
//        fun `getArgument should return null when no arguments present`() {
//            every { ksAnnotation.arguments } returns emptyList()
//            assertNull(ksAnnotation.getArgument<String>("testArg"))
//        }
//
//        @Test
//        fun `getArgument should work with different types`() {
//            val argName = mockk<KSName>()
//            every { argName.asString() } returns "intArg"
//            every { ksValueArgument.name } returns argName
//            every { ksValueArgument.value } returns 42
//            every { ksAnnotation.arguments } returns listOf(ksValueArgument)
//
//            assertEquals(42, ksAnnotation.getArgument<Int>("intArg"))
//        }
//    }
//
//}