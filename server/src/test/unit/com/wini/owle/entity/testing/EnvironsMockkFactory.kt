package com.wini.owle.entity.testing

import com.wini.owle.entity.environs.EnvMarkerComparisons
import com.wini.owle.entity.environs.marker.EnvMarkerComparisonEntity
import com.wini.owle.entity.environs.marker.EnvMarkerExpressionEntity
import io.mockk.every
import io.mockk.mockk


/**
 * Canned Entity mockk instances for tests.
 *
 * One factory per entity package, composed by [EntityMockkFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object EnvironsMockkFactory {

    fun envMarkerComparison(): EnvMarkerComparisonEntity = mockk(relaxed = true) {
        every { this@mockk.expression } returns expression
        every { this@mockk.variable } returns variable
        every { this@mockk.operator } returns operator
        every { this@mockk.value } returns value
    }

}
