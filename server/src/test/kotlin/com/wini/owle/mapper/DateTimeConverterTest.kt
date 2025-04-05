package com.wini.owle.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlinx.datetime.Instant


open class DateTimeConverterTest {

    @Test
    fun `toString should correctly format Instant in UTC`() {
        val instant = Instant.parse("2025-04-04T12:34:56Z")
        val formatted = DateTimeConverter.toString(instant)
        assertEquals("2025-04-04T12:34:56Z", formatted)
    }

    @Test
    fun `fromString should correctly parse UTC timestamp`() {
        val timestamp = "2025-04-04T12:34:56Z"
        val parsedInstant = DateTimeConverter.fromString(timestamp)
        assertEquals(Instant.parse("2025-04-04T12:34:56Z"), parsedInstant)
    }

    @Test
    fun `fromString should throw exception for invalid timestamp format`() {
        val invalidTimestamp = "not-a-timestamp"
        val exception = assertThrows<IllegalArgumentException> {
            DateTimeConverter.fromString(invalidTimestamp)
        }
        assertTrue(exception.message!!.contains("Invalid timestamp format"))
    }

}
