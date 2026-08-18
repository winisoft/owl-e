package com.wini.owle.repo

import io.mockk.MockKMatcherScope
import org.jetbrains.exposed.v1.dao.Entity


/** [GenericRepoTest] for entities keyed by `ULong`. */
abstract class ULongIdGenericRepoTest<T : Entity<ULong>> : GenericRepoTest<ULong, T>() {

    override fun mockID(): ULong = 1uL

    override fun otherID(): ULong = 999uL

    override fun MockKMatcherScope.anyID(): ULong = any()
}
