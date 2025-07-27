package com.wini.owle.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSName
import com.wini.owle.ksp.doc.oas.ApiEndpointDoc
import com.wini.owle.ksp.processor.ApiInterfaceProcessor
import com.wini.owle.ksp.utils.OwleSymbolCollector
import com.wini.owle.ksp.writer.OpenApiSpecFileWriter
import com.wini.owle.schema.wrapper.declaration.ApiEndpointDeclaration
import com.wini.owle.schema.wrapper.declaration.ModelDeclaration
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclaration
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class OwleApiDocProcessorTest {

    private lateinit var mockCodeGenerator: CodeGenerator
    private lateinit var mockLogger: KSPLogger
    private lateinit var mockResolver: Resolver
    private lateinit var processor: OwleApiDocProcessor

    // For capturing the lambda passed to OwleSymbolCollector.collect
    // The lambda in OwleSymbolCollector.collect takes a Triple.
    private lateinit var capturedCollectorLambda: CapturingSlot<(Triple<List<ModelDeclaration>, List<ErrorDeclaration>, List<ApiEndpointDeclaration>>) -> Unit>

    @BeforeEach
    fun setUp() {
        mockCodeGenerator = mockk(relaxed = true)
        mockLogger = mockk(relaxed = true)
        mockResolver = mockk(relaxed = true)

        mockkObject(OwleSymbolCollector)
        capturedCollectorLambda = slot()
        every { OwleSymbolCollector.collect(any(), any(), capture(capturedCollectorLambda)) } just runs

        mockkConstructor(ApiInterfaceProcessor::class)
        mockkConstructor(OpenApiSpecFileWriter::class)

        processor = OwleApiDocProcessor(mockCodeGenerator, mockLogger)
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(OwleSymbolCollector)
        clearAllMocks()
    }

    @Test
    fun `process should call OwleSymbolCollector and trigger processing via lambda`() {
        // Arrange
        val mockEndpointDecl1 = mockk<ApiEndpointDeclaration>()
        val mockEndpointDecl2 = mockk<ApiEndpointDeclaration>()
        val mockModelDeclList = listOf(mockk<ModelDeclaration>()) // Ensure it's a list
        val mockErrorDeclList = listOf(mockk<ErrorDeclaration>())   // Ensure it's a list
        val mockEndpointDeclList = listOf(mockEndpointDecl1, mockEndpointDecl2)

        val mockEndpointDoc1 = mockk<ApiEndpointDoc>()

        every { anyConstructed<ApiInterfaceProcessor>().process(mockEndpointDecl1) } returns mockEndpointDoc1
        every { anyConstructed<ApiInterfaceProcessor>().process(mockEndpointDecl2) } returns null

        val mockModelQN = mockk<KSName>()
        every { mockModelDeclList[0].qualifiedName } returns mockModelQN // Access first element for stubbing
        every { mockModelQN.asString() } returns "com.example.Model"
        
        every { anyConstructed<OpenApiSpecFileWriter>().writeSpec(any()) } just runs

        // Act: First call to processor.process()
        processor.process(mockResolver)

        verify { OwleSymbolCollector.collect(mockResolver, mockLogger, any()) }

        assertTrue(capturedCollectorLambda.isCaptured)
        // Invoke with a Triple
        capturedCollectorLambda.captured.invoke(
            Triple(mockModelDeclList, mockErrorDeclList, mockEndpointDeclList)
        )

        val expectedModelsMap = mapOf("com.example.Model" to mockModelDeclList[0])
        // Verify constructor calls with specific arguments
        verify { constructedWith<ApiInterfaceProcessor>(EqMatcher(mockResolver), EqMatcher(mockLogger), EqMatcher(expectedModelsMap)) }
        verify { anyConstructed<ApiInterfaceProcessor>().process(mockEndpointDecl1) }
        verify { anyConstructed<ApiInterfaceProcessor>().process(mockEndpointDecl2) }

        verify { constructedWith<OpenApiSpecFileWriter>(EqMatcher(mockCodeGenerator), EqMatcher(mockLogger)) }
        verify { anyConstructed<OpenApiSpecFileWriter>().writeSpec(listOf(mockEndpointDoc1)) }
    }

    @Test
    fun `process should only generate once`() {
        processor.process(mockResolver)
        verify { OwleSymbolCollector.collect(any(), any(), any()) }

        val result = processor.process(mockResolver)

        verify(exactly = 1) { OwleSymbolCollector.collect(any(), any(), any()) }
        assertTrue(result.isEmpty(), "Process should return empty list on subsequent calls")
        
        if (capturedCollectorLambda.isCaptured) {
            val mockEndpointDecl1 = mockk<ApiEndpointDeclaration>()
            val mockModelDecl = mockk<ModelDeclaration>()
            every { mockModelDecl.qualifiedName?.asString() } returns "com.example.Model2"
            
            // To ensure no new constructor calls, we check counts *before* this hypothetical second invoke.
            // This part of the test is to ensure the lambda invocation path is also guarded by hasGenerated effectively.
            // The hasGenerated flag in OwleApiDocProcessor itself prevents processSpec from being called again.
            // So, verifying no *new* calls to constructors inside processSpec is the goal.
            // We need to capture current counts or ensure they don't increment.

            // Alternative: Simply trust that if OwleSymbolCollector.collect isn't called again,
            // the lambda won't be invoked again by the processor's logic.
            // The current test structure (verify exactly = 1 for collect) covers this adequately for hasGenerated flag.
            // The original test for `process should only generate once` is sufficient.
            // The part about re-invoking lambda is more for testing the lambda path itself, not `hasGenerated`.
        }
    }
}