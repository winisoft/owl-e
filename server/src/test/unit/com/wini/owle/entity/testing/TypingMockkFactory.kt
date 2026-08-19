package com.wini.owle.entity.testing

import com.wini.owle.entity.typing.TypeNodeEntity
import io.mockk.every
import io.mockk.mockk


/**
 * Canned  Entity mockk instances for tests.
 *
 * One factory per entity package, composed by [EntityMockkFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object TypingMockkFactory {

    fun typeNodeDao(
        namespace: String? = "builtins",
        name: String? = "List",
        alias: String? = "CustomType"
    ): TypeNodeEntity = mockk(relaxed = true) {
        every { this@mockk.namespace } returns namespace
        every { this@mockk.name } returns name
        every { this@mockk.alias } returns alias
    }

}
