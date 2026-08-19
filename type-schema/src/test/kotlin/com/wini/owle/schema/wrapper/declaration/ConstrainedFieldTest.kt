package com.wini.owle.schema.wrapper.declaration

import io.mockk.every
import io.mockk.mockk

import com.google.devtools.ksp.symbol.KSAnnotation // Specific import
import com.google.devtools.ksp.symbol.KSClassDeclaration // Specific import
import com.google.devtools.ksp.symbol.KSName // Specific import
import com.google.devtools.ksp.symbol.KSPropertyDeclaration // Specific import
import com.google.devtools.ksp.symbol.KSType // Specific import
import com.google.devtools.ksp.symbol.KSTypeReference // Specific import
import com.google.devtools.ksp.symbol.KSValueArgument // Specific import
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotation


/*
open class ConstrainedFieldTest {

    private lateinit var mockKsProperty: KSPropertyDeclaration // Represents the underlying KSP property
    private lateinit var constrainedField: ConstrainedField    // The interface we are testing

    // Helper to mock KSAnnotation (representing annotations on mockKsProperty)
    private fun mockRawKsAnnotation(
        qualifiedName: String,
        arguments: List<KSValueArgument> = emptyList()
    ): KSAnnotation {
        val annotation = mockk<KSAnnotation>(relaxed = true)
        val annotationTypeRef = mockk<KSTypeReference>(relaxed = true)
        val resolvedType = mockk<KSType>(relaxed = true)
        val declaration = mockk<KSClassDeclaration>(relaxed = true)

        val qName = mockk<KSName>(relaxed = true)
        every { qName.asString() } returns qualifiedName
        every { declaration.qualifiedName } returns qName

        val sName = mockk<KSName>(relaxed = true)
        val simpleNameStr = qualifiedName.substringAfterLast('.')
        every { sName.asString() } returns simpleNameStr
        every { declaration.simpleName } returns sName // KSP annotation.shortName calls annotationType.resolve().declaration.simpleName

        every { resolvedType.declaration } returns declaration
        every { annotationTypeRef.resolve() } returns resolvedType
        every { annotation.annotationType } returns annotationTypeRef
        every { annotation.arguments } returns arguments
        // KSAnnotation.shortName is a property that KSP provides by delegating to the declaration's simpleName.
        // We mock it here based on how KSP would derive it.
        every { annotation.shortName } returns sName
        return annotation
    }

    // Helper to mock a fully-formed ConstraintAnnotation (which constrainedField.constraintAnnotations would return)
    private fun mockConstraintAnnotation(
        errorFqns: List<String> = emptyList(),
        attrs: Map<String, KSValueArgument> = emptyMap(),
        // originalAnnotation is the raw KSAnnotation that this ConstraintAnnotation would wrap
        originalAnnotation: KSAnnotation = mockRawKsAnnotation("dummy.Constraint")
    ): FieldConstraintAnnotation {
        return mockk<FieldConstraintAnnotation>(relaxed = true).apply {
            every { this@apply.errorFqNames } returns errorFqns
            every { attributes } returns attrs
            // The wrapped ConstraintAnnotation should expose the shortName of its underlying KSAnnotation delegate
            every { shortName } returns originalAnnotation.shortName
            every { annotationType } returns originalAnnotation.annotationType
            every { arguments } returns originalAnnotation.arguments
        }
    }

    private fun mockValueArgument(name: String, value: Any?): KSValueArgument {
        val argument = mockk<KSValueArgument>(relaxed = true)
        val ksName = mockk<KSName>(relaxed = true)
        every { ksName.asString() } returns name
        every { argument.name } returns ksName
        every { argument.value } returns value
        return argument
    }


    @BeforeEach
    fun setUp() {
        mockKsProperty = mockk<KSPropertyDeclaration>(relaxed = true)
        constrainedField = mockk<ConstrainedField>(relaxed = true)

        // Define default behavior for properties derived from constraintAnnotations.
        // Individual tests will mock constrainedField.constraintAnnotations directly.
        every { constrainedField.hasConstraints } answers { constrainedField.fieldConstraintAnnotations.isNotEmpty() }
        every { constrainedField.hasNoConstraints } answers { constrainedField.fieldConstraintAnnotations.isEmpty() }

        every { constrainedField.raisedErrorNames } answers {
            constrainedField.fieldConstraintAnnotations.flatMap { it.errorFqNames }
        }

        every { constrainedField.constraintAttributes } answers {
            val aggregatedAttributes = mutableMapOf<String, KSValueArgument>()
            constrainedField.fieldConstraintAnnotations.forEach { annotationMock ->
                aggregatedAttributes.putAll(annotationMock.attributes)
            }
            aggregatedAttributes
        }

        // If ConstrainedField delegates KSPropertyDeclaration methods, they can be mocked here too if needed.
        // every { constrainedField.simpleName } answers { mockKsProperty.simpleName }
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }


    /**
     * GIVEN A ConstrainedField member of a ModelDeclaration is present reflecting a KSProperty that was Constraint annotated.
     *     It has unrelated annotations in addition to its ConstraintAnnotation. 2 Constraints are applied, and 1 unrelated.
     * WHEN Access of `constrainedField.constraintAnnotations` should filter out the unrelated annotations and return a
     *     list of ConstraintAnnotation
     * THEN - constraintAnnotations should have 2 items, not 3
     *      - The correct member property was accessed
     */
    @Test
    fun `constraintAnnotations property should filter non-constraint raw annotations and map to ConstraintAnnotation mocks`() {
        // This test specifically checks the logic of filtering and mapping if it were part of ConstrainedFieldImpl
        // So, we set up the `answers` block for `constrainedField.constraintAnnotations` here to simulate that logic.
        every { constrainedField.fieldConstraintAnnotations } answers {
            mockKsProperty.annotations // These are KSAnnotation mocks from mockRawKsAnnotation
                .filter {
                    val fqn = it.annotationType.resolve().declaration.qualifiedName?.asString()
                    fqn?.startsWith(FieldConstraintAnnotation.FQN_STARTS_WITH) == true
                }
                .map { ksAnnotationDelegate ->
                    // Simulate the mapping to a ConstraintAnnotation wrapper/mock
                    mockConstraintAnnotation(originalAnnotation = ksAnnotationDelegate)
                }
                .toList()
        }

        val constraintRawKsAnno1 = mockRawKsAnnotation("${FieldConstraintAnnotation.FQN_STARTS_WITH}.MyConstraint1")
        val nonConstraintRawKsAnno = mockRawKsAnnotation("com.example.NotAConstraint")
        val constraintRawKsAnno2 = mockRawKsAnnotation("${FieldConstraintAnnotation.FQN_STARTS_WITH}.MyConstraint2")

        every { mockKsProperty.annotations } returns sequenceOf(
            constraintRawKsAnno1,
            nonConstraintRawKsAnno,
            constraintRawKsAnno2
        )

        val mappedAnnotations = constrainedField.fieldConstraintAnnotations
        assertEquals(2, mappedAnnotations.size)
        // assertTrue(mappedAnnotations.all { it is ConstraintAnnotation }) // Redundant: type system enforces this for List<ConstraintAnnotation>

        // Verify that the mapping correctly utilized data (like shortName) from the original raw KSAnnotations
        assertTrue(
            mappedAnnotations.any { it.shortName.asString() == "MyConstraint1" },
            "Constraint with shortName MyConstraint1 should be present"
        )
        assertTrue(
            mappedAnnotations.any { it.shortName.asString() == "MyConstraint2" },
            "Constraint with shortName MyConstraint2 should be present"
        )
    }

    /**
     * **Test: `hasConstraints` and `hasNoConstraints` correctly reflect the presence of `ConstraintAnnotation`s.**
     *
     * 1.  **Arrange (Initial Conditions & Use Case):**
     *     Sets up `constrainedField.constraintAnnotations` to return different lists representing various states:
     *     a) A list containing one or more `ConstraintAnnotation` mocks (field has constraints).
     *     b) An empty list (field has no constraints).
     *     This simulates a property that either has validation rules applied or does not.
     *
     * 2.  **Act (Action & Impact):**
     *     The test accesses `constrainedField.hasConstraints` and `constrainedField.hasNoConstraints` properties.
     *     These properties are expected to derive their boolean value directly from whether the
     *     `constraintAnnotations` list is empty or not (as defined in the `setUp` method).
     *
     * 3.  **Assert (Observable Results & Confirmation):**
     *     - When `constraintAnnotations` is non-empty, `hasConstraints` should be `true` and `hasNoConstraints` `false`.
     *     - When `constraintAnnotations` is empty, `hasConstraints` should be `false` and `hasNoConstraints` `true`.
     *     This confirms these boolean flags accurately represent the presence of validation rules.
     */
    @Test
    fun `hasConstraints and hasNoConstraints reflect presence of constraint annotations`() {
        // Case 1: Has constraints
        val mockAnno = mockConstraintAnnotation() // A single mocked ConstraintAnnotation
        every { constrainedField.fieldConstraintAnnotations } returns listOf(mockAnno)
        assertTrue(constrainedField.hasConstraints)
        assertFalse(constrainedField.hasNoConstraints)

        // Case 2: No constraints (empty list)
        every { constrainedField.fieldConstraintAnnotations } returns emptyList()
        assertFalse(constrainedField.hasConstraints)
        assertTrue(constrainedField.hasNoConstraints)
    }

    /**
     * **Test: `raisedErrorNames` correctly aggregates FQNs from all `ConstraintAnnotation`s.**
     *
     * 1.  **Arrange (Initial Conditions & Use Case):**
     *     Prepares a list of `ConstraintAnnotation` mocks (`mockAnno1`, `mockAnno2`). Each mock is configured
     *     via `mockConstraintAnnotation()` to return a specific list of error fully qualified names (FQNs)
     *     when its `errorFqNames` property is accessed. This simulates a field with multiple constraint
     *     annotations, each potentially declaring one or more error types it can raise.
     *     `constrainedField.constraintAnnotations` is then mocked to return this prepared list.
     *
     * 2.  **Act (Action & Impact):**
     *     The test accesses the `constrainedField.raisedErrorNames` property. This property is expected
     *     (as per `setUp`) to iterate through the list returned by `constrainedField.constraintAnnotations`
     *     and collect all strings from each annotation's `errorFqNames` list into a single flat list.
     *
     * 3.  **Assert (Observable Results & Confirmation):**
     *     - The resulting `errorNames` list should contain all FQNs from `mockAnno1` and `mockAnno2` combined.
     *     - The size of `errorNames` and its contents are checked to confirm that the `flatMap` operation
     *       correctly aggregated the FQNs from all underlying `ConstraintAnnotation` mocks.
     */
    @Test
    fun `raisedErrorNames aggregates FQNs from all constraint annotations`() {
        // 1. Prepare individual ConstraintAnnotation mocks with specific FQNs
        val mockAnno1 = mockConstraintAnnotation(errorFqns = listOf("error.FQN1", "error.FQN2"))
        val mockAnno2 = mockConstraintAnnotation(errorFqns = listOf("error.FQN3"))

        // 2. Mock constrainedField.constraintAnnotations to return these pre-configured mocks
        every { constrainedField.fieldConstraintAnnotations } returns listOf(mockAnno1, mockAnno2)

        // 3. Act: Call the property that uses the mocked list
        val errorNames = constrainedField.raisedErrorNames

        // 4. Assert: Check if the flatMap logic worked as expected
        assertEquals(3, errorNames.size)
        assertTrue(errorNames.containsAll(listOf("error.FQN1", "error.FQN2", "error.FQN3")))
    }

    /**
     * **Test: `constraintAttributes` correctly aggregates attributes from all `ConstraintAnnotation`s.**
     *
     * 1.  **Arrange (Initial Conditions & Use Case):**
     *     Prepares `ConstraintAnnotation` mocks (`mockAnno1`, `mockAnno2`), each configured with a distinct
     *     set of attributes (maps of argument name to `KSValueArgument`). This simulates a field with multiple
     *     constraint annotations, each having its own specific configuration arguments (e.g., `maxLength`, `pattern`).
     *     `constrainedField.constraintAnnotations` is mocked to return this list of prepared annotations.
     *
     * 2.  **Act (Action & Impact):**
     *     The test accesses `constrainedField.constraintAttributes`. This property (as per `setUp`) should
     *     iterate through all `ConstraintAnnotation` mocks provided by `constrainedField.constraintAnnotations`
     *     and merge their individual `attributes` maps into a single, comprehensive map.
     *
     * 3.  **Assert (Observable Results & Confirmation):**
     *     - The final `attributesMap` should contain all arguments from both `mockAnno1` and `mockAnno2`.
     *     - The size and the presence/values of specific keys are checked to confirm that attributes from all
     *       source annotations have been correctly collected into the resulting map.
     */
    @Test
    fun `constraintAttributes aggregates attributes from all constraint annotations`() {
        val arg1Name = "max"
        val arg1Val = 10
        val arg1 = mockValueArgument(arg1Name, arg1Val)

        val arg2Name = "message"
        val arg2Val = "hello"
        val arg2 = mockValueArgument(arg2Name, arg2Val)

        val arg3Name = "groups"
        val arg3Val = "ui"
        val arg3 = mockValueArgument(arg3Name, arg3Val)

        // 1. Prepare ConstraintAnnotation mocks with specific attributes
        val mockAnno1 = mockConstraintAnnotation(attrs = mapOf(arg1Name to arg1))
        val mockAnno2 = mockConstraintAnnotation(attrs = mapOf(arg2Name to arg2, arg3Name to arg3))

        // 2. Mock constrainedField.constraintAnnotations to return these
        every { constrainedField.fieldConstraintAnnotations } returns listOf(mockAnno1, mockAnno2)

        // 3. Act
        val attributesMap = constrainedField.constraintAttributes

        // 4. Assert
        assertEquals(3, attributesMap.size)
        assertEquals(arg1Val, attributesMap[arg1Name]?.value)
        assertEquals(arg2Val, attributesMap[arg2Name]?.value)
        assertEquals(arg3Val, attributesMap[arg3Name]?.value)
    }

}

 */