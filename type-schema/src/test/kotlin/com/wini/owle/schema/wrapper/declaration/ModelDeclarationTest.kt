package com.wini.owle.schema.wrapper.declaration

import com.google.devtools.ksp.symbol.*
import com.wini.owle.schema.wrapper.annotation.ModelAnnotation
import com.wini.owle.schema.wrapper.annotation.ModelAnnotationImpl
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotation
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotationImpl
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

open class ModelDeclarationTest {

    private lateinit var mockKsClassDecl: KSClassDeclaration
    private lateinit var modelDeclImpl: ModelDeclarationImpl

    @BeforeEach
    fun setUp() {
        mockKsClassDecl = mockk<KSClassDeclaration>(relaxed = true)
        // ModelDeclarationImpl is instantiated in each test where its specific state is arranged.
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    // Helper to mock a raw KSAnnotation (as KSP would provide it)
    private fun mockRawKSAnnotation(qualifiedNameStr: String, argumentsList: List<KSValueArgument> = emptyList(), simpleNameStr: String? = null): KSAnnotation {
        val annotation = mockk<KSAnnotation>(relaxed = true)
        val annotationTypeRef = mockk<KSTypeReference>(relaxed = true)
        val resolvedType = mockk<KSType>(relaxed = true)
        val declaration = mockk<KSClassDeclaration>(relaxed = true)
        
        val qName = mockk<KSName>(relaxed = true)
        every { qName.asString() } returns qualifiedNameStr
        every { declaration.qualifiedName } returns qName

        val actualSimpleName = simpleNameStr ?: qualifiedNameStr.substringAfterLast('.')
        val sName = mockk<KSName>(relaxed = true)
        every { sName.asString() } returns actualSimpleName
        every { declaration.simpleName } returns sName
        
        every { resolvedType.declaration } returns declaration
        every { annotationTypeRef.resolve() } returns resolvedType
        every { annotation.annotationType } returns annotationTypeRef
        every { annotation.arguments } returns argumentsList
        every { annotation.shortName } returns sName // KSAnnotation has shortName
        return annotation
    }

    // Helper to mock a KSValueArgument
    private fun mockKSValueArgument(nameStr: String, argValue: Any?): KSValueArgument {
        val argument = mockk<KSValueArgument>(relaxed = true)
        val argName = mockk<KSName>(relaxed = true)
        every { argName.asString() } returns nameStr
        every { argument.name } returns argName
        every { argument.value } returns argValue
        return argument
    }

    /**
     * **Test: `modelAnnotation` property correctly parses and wraps the `@ModelDefinition` annotation.**
     *
     * 1.  **Arrange (Initial Conditions & Use Case):**
     *     A `KSClassDeclaration` (mocked as `mockKsClassDecl`) is set up to have one specific annotation that
     *     matches `ModelAnnotation.QUALIFIED_NAME` (i.e., it's a `@ModelDefinition`).
     *     This simulates KSP providing the raw declaration of a DTO class.
     *
     * 2.  **Act (Action & Impact):**
     *     An instance of `ModelDeclarationImpl` is created, passing the `mockKsClassDecl`. During its
     *     initialization, `ModelDeclarationImpl` is expected to find the raw `@ModelDefinition` `KSAnnotation`
     *     and wrap it in a `ModelAnnotationImpl` instance.
     *     The test then accesses `modelDeclImpl.modelAnnotation`.
     *
     * 3.  **Assert (Observable Results & Confirmation):**
     *     - It confirms that `modelDeclImpl.modelAnnotation` is not null.
     *     - It verifies that the returned object is an instance of `ModelAnnotationImpl`.
     *     - It checks that the `delegate` property of the `ModelAnnotationImpl` is the exact raw `KSAnnotation`
     *       that was originally provided on `mockKsClassDecl`, confirming correct wrapping.
     */
    @Test
    fun `modelAnnotation should correctly parse ModelDefinition annotation`() {
        val modelDefRawAnnotation = mockRawKSAnnotation(ModelAnnotation.QUALIFIED_NAME)
        every { mockKsClassDecl.annotations } returns sequenceOf(modelDefRawAnnotation)

        modelDeclImpl = ModelDeclarationImpl(mockKsClassDecl)
        val actualModelAnnotation = modelDeclImpl.modelAnnotation

        assertNotNull(actualModelAnnotation)
        assertTrue(actualModelAnnotation is ModelAnnotationImpl)
        assertEquals(modelDefRawAnnotation, (actualModelAnnotation as ModelAnnotationImpl).delegate)
    }

    /**
     * **Test: `isValidatorGenTarget` and `isNotValidatorGenTarget` reflect `skipValidation` argument.**
     *
     * 1.  **Arrange (Initial Conditions & Use Case):**
     *     Three scenarios for `mockKsClassDecl` are set up:
     *     a) Its `@ModelDefinition` annotation has a `skipValidation` argument set to `true`.
     *     b) Its `@ModelDefinition` annotation has `skipValidation` set to `false`.
     *     c) Its `@ModelDefinition` annotation has no `skipValidation` argument (should default to not skipping).
     *     This covers all ways the `skipValidation` flag can influence validator generation for a model.
     *
     * 2.  **Act (Action & Impact):**
     *     For each scenario, `ModelDeclarationImpl` is instantiated. The test then accesses
     *     `modelDeclImpl.isValidatorGenTarget` and `modelDeclImpl.isNotValidatorGenTarget`.
     *     These properties rely on the `ModelAnnotationImpl` correctly parsing the `skipValidation` argument.
     *
     * 3.  **Assert (Observable Results & Confirmation):**
     *     - When `skipValidation` is true: `isNotValidatorGenTarget` is true, `isValidatorGenTarget` is false.
     *     - When `skipValidation` is false: `isNotValidatorGenTarget` is false, `isValidatorGenTarget` is true.
     *     - When `skipValidation` is absent: `isNotValidatorGenTarget` is false, `isValidatorGenTarget` is true.
     *     This confirms the properties accurately reflect the intent to generate (or not generate) a validator.
     */
    @Test
    fun `isValidatorGenTarget and isNotValidatorGenTarget should reflect skipValidation argument`() {
        // Test case 1: skipValidation = true
        val skipTrueArg = mockKSValueArgument("skipValidation", true)
        val modelDefSkipTrue = mockRawKSAnnotation(ModelAnnotation.QUALIFIED_NAME, listOf(skipTrueArg))
        // Must mock annotations sequence for the class itself for ModelAnnotationImpl to initialize
        every { mockKsClassDecl.annotations } returns sequenceOf(modelDefSkipTrue)
        var currentModelDecl = ModelDeclarationImpl(mockKsClassDecl)

        assertTrue(currentModelDecl.isNotValidatorGenTarget)
        assertFalse(currentModelDecl.isValidatorGenTarget)

        // Test case 2: skipValidation = false
        val skipFalseArg = mockKSValueArgument("skipValidation", false)
        val modelDefSkipFalse = mockRawKSAnnotation(ModelAnnotation.QUALIFIED_NAME, listOf(skipFalseArg))
        every { mockKsClassDecl.annotations } returns sequenceOf(modelDefSkipFalse) // Re-mock for this specific case
        currentModelDecl = ModelDeclarationImpl(mockKsClassDecl)

        assertFalse(currentModelDecl.isNotValidatorGenTarget)
        assertTrue(currentModelDecl.isValidatorGenTarget)

        // Test case 3: skipValidation argument missing (defaults to false behavior)
        val modelDefNoSkipArg = mockRawKSAnnotation(ModelAnnotation.QUALIFIED_NAME, emptyList())
        every { mockKsClassDecl.annotations } returns sequenceOf(modelDefNoSkipArg) // Re-mock for this specific case
        currentModelDecl = ModelDeclarationImpl(mockKsClassDecl)

        assertFalse(currentModelDecl.isNotValidatorGenTarget) 
        assertTrue(currentModelDecl.isValidatorGenTarget)
    }

    /**
     * **Test: `constrainedFields` correctly identifies, wraps, and groups constraint annotations for properties.**
     *
     * 1.  **Arrange (Initial Conditions & Use Case):**
     *     - `mockKsClassDecl` is set up with a standard `@ModelDefinition` annotation (required for `ModelDeclarationImpl` instantiation).
     *     - It's configured via `getAllProperties()` to return a list of mocked `KSPropertyDeclaration`s:
     *       `propWithConstraints1`, `propWithConstraints2`, and `propWithoutConstraints`.
     *     - Each of these `KSPropertyDeclaration` mocks is then given a specific sequence of raw `KSAnnotation` mocks
     *       (created by `mockRawKSAnnotation`) via its `annotations` property:
     *       - `propWithConstraints1` gets two annotations whose FQNs start with `ConstraintAnnotation.FQN_STARTS_WITH`
     *         (e.g., `@...ConstraintA`, `@...ConstraintB`) and one unrelated annotation.
     *       - `propWithConstraints2` gets one annotation starting with `ConstraintAnnotation.FQN_STARTS_WITH` (e.g., `@...ConstraintC`).
     *       - `propWithoutConstraints` gets only an unrelated annotation.
     *     This setup comprehensively simulates a typical DTO class with fields that have varying numbers and types
     *     of annotations, including multiple validation rules on some fields.
     *
     * 2.  **Act (Action & Impact):**
     *     An instance of `ModelDeclarationImpl` (`modelDeclImpl`) is created using the fully configured `mockKsClassDecl`.
     *     The test then accesses `modelDeclImpl.constrainedFields`. This triggers the core logic within `ModelDeclarationImpl`:
     *       - It iterates through the `KSPropertyDeclaration`s provided by `mockKsClassDecl.getAllProperties()`.
     *       - For each `KSPropertyDeclaration`, it instantiates a `ConstrainedFieldImpl`.
     *       - Each `ConstrainedFieldImpl` in turn processes the raw `KSAnnotation`s on its delegated `KSPropertyDeclaration`.
     *         It filters these to find annotations matching `ConstraintAnnotation.FQN_STARTS_WITH` and then instantiates
     *         `ConstraintAnnotationImpl` for each one found.
     *       - Finally, `ModelDeclarationImpl` collects only those `ConstrainedFieldImpl` instances that actually have
     *         one or more `ConstraintAnnotationImpl`s and creates a map where the key is the `ConstrainedFieldImpl`
     *         and the value is its `List<ConstraintAnnotationImpl>`.
     *
     * 3.  **Assert (Observable Results & Confirmation):**
     *     The assertions verify that the entire process of identification, wrapping, and grouping was successful:
     *     - The size of the `actualConstrainedFieldsMap` map must be 2, indicating that `propWithoutConstraints` was correctly filtered out.
     *     - For `propWithConstraints1` (identified as "field1"):
     *       - The map must contain an entry where the key is a `ConstrainedFieldImpl` whose `delegate` is the original `propWithConstraints1` mock.
     *       - The value associated with this key must be a list of two `ConstraintAnnotationImpl`s.
     *       - Each of these `ConstraintAnnotationImpl`s must have the correct corresponding raw `KSAnnotation` (`rawConstraintAnnoA` or `rawConstraintAnnoB`) as its `delegate`,
     *         confirmed by checking properties like `shortName`.
     *     - For `propWithConstraints2` (identified as "field2"):
     *       - Similar checks are performed: the key must be a `ConstrainedFieldImpl` delegating to `propWithConstraints2`.
     *       - The value list must contain one `ConstraintAnnotationImpl`.
     *       - This `ConstraintAnnotationImpl` must delegate to `rawConstraintAnnoC`, verified via `shortName` or direct delegate comparison.
     *     - It explicitly confirms that no entry exists in the map for `propWithoutConstraints`.
     *     These checks ensure that the `ModelDeclarationImpl` correctly orchestrates the creation and filtering of its constituent wrapper types
     *     based on the raw KSP metadata.
     */
    @Test
    fun `constrainedFields should correctly identify and group constraint annotations`() {
        // Arrange: Top-level ModelDefinition annotation for the class itself is crucial for ModelDeclarationImpl
        val modelDefAnnotation = mockRawKSAnnotation(ModelAnnotation.QUALIFIED_NAME)
        every { mockKsClassDecl.annotations } returns sequenceOf(modelDefAnnotation)

        // Arrange: Mock KSPropertyDeclarations
        val propWithConstraints1 = mockk<KSPropertyDeclaration>(name = "propWithConstraints1", relaxed = true)
        val prop1SimpleName = mockk<KSName>(name = "prop1SimpleName", relaxed = true) { every { asString() } returns "field1" }
        every { propWithConstraints1.simpleName } returns prop1SimpleName

        val propWithConstraints2 = mockk<KSPropertyDeclaration>(name = "propWithConstraints2", relaxed = true)
        val prop2SimpleName = mockk<KSName>(name = "prop2SimpleName", relaxed = true) { every { asString() } returns "field2" }
        every { propWithConstraints2.simpleName } returns prop2SimpleName

        val propWithoutConstraints = mockk<KSPropertyDeclaration>(name = "propWithoutConstraints", relaxed = true)
        val prop3SimpleName = mockk<KSName>(name = "prop3SimpleName", relaxed = true) { every { asString() } returns "field3" }
        every { propWithoutConstraints.simpleName } returns prop3SimpleName

        // Arrange: Mock raw KSAnnotations that these properties will have
        val rawConstraintAnnoA = mockRawKSAnnotation("${FieldConstraintAnnotation.FQN_STARTS_WITH}.ConstraintA", simpleNameStr = "ConstraintA")
        val rawConstraintAnnoB = mockRawKSAnnotation("${FieldConstraintAnnotation.FQN_STARTS_WITH}.ConstraintB", simpleNameStr = "ConstraintB")
        val rawNonConstraintAnno = mockRawKSAnnotation("com.example.OtherAnnotation", simpleNameStr = "OtherAnnotation")
        val rawConstraintAnnoC = mockRawKSAnnotation("${FieldConstraintAnnotation.FQN_STARTS_WITH}.ConstraintC", simpleNameStr = "ConstraintC")

        every { propWithConstraints1.annotations } returns sequenceOf(rawConstraintAnnoA, rawNonConstraintAnno, rawConstraintAnnoB)
        every { propWithConstraints2.annotations } returns sequenceOf(rawConstraintAnnoC)
        every { propWithoutConstraints.annotations } returns sequenceOf(rawNonConstraintAnno)

        // Link properties to the class declaration
        every { mockKsClassDecl.getAllProperties() } returns sequenceOf(propWithConstraints1, propWithConstraints2, propWithoutConstraints)

        // Act: Instantiate the class under test
        modelDeclImpl = ModelDeclarationImpl(mockKsClassDecl)
        val actualConstrainedFieldsMap = modelDeclImpl.constrainedFields

        // Assert: Overall map structure
        assertEquals(2, actualConstrainedFieldsMap.size, "Map should only contain properties with constraints.")

        // Assert: Content for propWithConstraints1 ("field1")
        val field1Wrapper = actualConstrainedFieldsMap.keys.find { it.simpleName.asString() == "field1" }
        assertNotNull(field1Wrapper, "ConstrainedField wrapper for 'field1' should be present.")
        assertTrue(field1Wrapper is ConstrainedFieldImpl, "Wrapper for 'field1' should be a ConstrainedFieldImpl.")
        assertEquals(propWithConstraints1, (field1Wrapper as ConstrainedFieldImpl).delegate, "Delegate of 'field1' wrapper should be the original KSPropertyDeclaration.")

        val field1Annotations = actualConstrainedFieldsMap[field1Wrapper]
        assertNotNull(field1Annotations, "Annotations list for 'field1' should not be null.")
        assertEquals(2, field1Annotations!!.size, "'field1' should have two constraint annotations.")
        assertTrue(field1Annotations.all { it is FieldConstraintAnnotationImpl }, "All annotations for 'field1' should be ConstraintAnnotationImpl instances.")
        
        val field1AnnoDelegates = field1Annotations.map { (it as FieldConstraintAnnotationImpl).delegate }
        assertTrue(field1AnnoDelegates.contains(rawConstraintAnnoA), "Raw annotation A should be a delegate for one of 'field1' annotations.")
        assertTrue(field1AnnoDelegates.contains(rawConstraintAnnoB), "Raw annotation B should be a delegate for one of 'field1' annotations.")
        val field1AnnoShortNames = field1Annotations.map { it.shortName.asString() }
        assertTrue(field1AnnoShortNames.contains("ConstraintA"))
        assertTrue(field1AnnoShortNames.contains("ConstraintB"))


        // Assert: Content for propWithConstraints2 ("field2")
        val field2Wrapper = actualConstrainedFieldsMap.keys.find { it.simpleName.asString() == "field2" }
        assertNotNull(field2Wrapper, "ConstrainedField wrapper for 'field2' should be present.")
        assertTrue(field2Wrapper is ConstrainedFieldImpl, "Wrapper for 'field2' should be a ConstrainedFieldImpl.")
        assertEquals(propWithConstraints2, (field2Wrapper as ConstrainedFieldImpl).delegate, "Delegate of 'field2' wrapper should be the original KSPropertyDeclaration.")

        val field2Annotations = actualConstrainedFieldsMap[field2Wrapper]
        assertNotNull(field2Annotations, "Annotations list for 'field2' should not be null.")
        assertEquals(1, field2Annotations!!.size, "'field2' should have one constraint annotation.")
        assertTrue(field2Annotations.all { it is FieldConstraintAnnotationImpl }, "All annotations for 'field2' should be ConstraintAnnotationImpl instances.")
        
        val field2AnnoImpl = field2Annotations.first() as FieldConstraintAnnotationImpl
        assertEquals(rawConstraintAnnoC, field2AnnoImpl.delegate, "Delegate of 'field2' annotation should be raw annotation C.")
        assertEquals("ConstraintC", field2AnnoImpl.shortName.asString())

        // Assert: propWithoutConstraints ("field3") is not present
        val field3WrapperOrNull = actualConstrainedFieldsMap.keys.find { it.simpleName.asString() == "field3" }
        assertNull(field3WrapperOrNull, "ConstrainedField wrapper for 'field3' (which has no constraints) should not be present.")
    }
}