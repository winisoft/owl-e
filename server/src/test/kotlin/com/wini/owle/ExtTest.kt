package com.wini.owle

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


class IfNullExitTests {

    @Test
    fun `ifNullExit returns value when not null`() {
        val result = "value".ifNullExit { error("should not be called") }
        assertEquals("value", result)
    }

    @Test
    fun `ifNullExit throws when value is null`() {
        val exception = assertThrows<IllegalStateException> {
            val input: String? = null
            input.ifNullExit { error("value was null") }
        }
        assertEquals("value was null", exception.message)
    }
}
