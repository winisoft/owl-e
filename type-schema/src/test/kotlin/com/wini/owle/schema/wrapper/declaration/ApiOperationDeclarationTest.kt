package com.wini.owle.schema.wrapper.declaration

import com.wini.owle.schema.wrapper.annotation.ApiOperationAnnotation
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


open class ApiOperationDeclarationTest {

    private lateinit var apiOpsAnnotation: ApiOperationAnnotation
    private lateinit var apiOpsDeclaration: ApiOperationDeclaration

    @BeforeEach
    fun setUp() {
        apiOpsAnnotation = mockk()
        apiOpsDeclaration = mockk()

        every { apiOpsDeclaration.apiOpsAnnotation } returns apiOpsAnnotation

    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test something`() {

    }



}
