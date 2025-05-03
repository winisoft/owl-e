package com.wini.owle.mapper

import kotlinx.datetime.Instant


object DateTimeConverter {

    /**
     * Converts an Instant to a UTC-formatted ISO 8601 string.
     * Example: "2025-04-04T13:26:00Z"
     */
    fun toString(timestamp: Instant): String {
        return timestamp.toString()
    }

    /**
     * Parses a UTC-formatted ISO 8601 string into an Instant.
     * @throws IllegalArgumentException for bad (non-ISO-8601-compliant) formats
     */
    fun fromString(timestamp: String): Instant {
        return try {
            Instant.parse(timestamp)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid timestamp format: '$timestamp'", e)
        }
    }

    fun isChronologicallyCoherent(timestamp: String): Boolean = runCatching { fromString(timestamp) }.getOrNull() != null

}
