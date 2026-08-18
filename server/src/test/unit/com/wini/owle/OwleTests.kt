package com.wini.owle

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


/**
 * The one root. `:server:test` runs this and nothing else is discoverable on its own, so every
 * test executes exactly once and the report reads as the nested tree the display names describe:
 * suite, then module, then subject, then test.
 *
 * Adding a layer means adding a `@Nested` entry here or in one of the two suites. Anything it
 * points at must be `abstract`, or JUnit will find it a second time as a class in its own right.
 */
@DisplayName("Owl-E")
class OwleTest {

    @Nested
    inner class Unit : OwleUnitTestSuite()

    @Nested
    inner class Integration : OwleIntegrationTestSuite()
}
