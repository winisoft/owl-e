package com.wini.owle.data.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSNode
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Stream

class PythonVersionsTest {

    // Use MockK to create relaxed stubs for the external interfaces we do not interact with directly
    private val codeGen: CodeGenerator = mockk(relaxed = true)
    private val logger: KSPLogger = mockk(relaxed = true)
    private val generator = PythonVersions(codeGen, logger)

    
    @Test
    fun `parses edge-case version strings (epoch, v-prefix, trailing punctuation, single-letter suffixes)`() {
        val edgeCases = listOf(
            "1!3.1.0",
            "1!3.0.0",
            "1!1.2.2",
            "1!13.17.0",
            "2004a",
            "v1.0.0",
            "1!2.0.2",
            "1!1.2.0",
            "1!10.11.0",
            "5!4.2.2",
            "1!2020.1.30",
            "2012c",
            "2013b",
            "2010h",
            "5!4.2.1",
            "1!6.0.0",
            "1!1.2.1",
            "3!1.7",
            "1746458771!10.1.0",
            "1746458685!11.4.0"
        )

        val (_, _, failures) = generator.parseVersions(edgeCases, 0UL, logger)

        assertTrue(
            failures.isEmpty(),
            "Edge-case version strings should not fail to parse, but got: ${failures}"
        )
    }
}