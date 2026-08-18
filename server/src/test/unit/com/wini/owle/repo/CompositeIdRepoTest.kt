package com.wini.owle.repo

import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.dao.CompositeEntity


/**
 * [GenericRepoTest] for entities keyed by a [CompositeID].
 *
 * The two ids are mocks held as fields rather than built per call. A real `CompositeID` cannot be
 * constructed here - it requires at least one key column mapping and this class deliberately does
 * not know its table - and a fresh mock each call would never compare equal to itself.
 */
abstract class CompositeIdRepoTest<T : CompositeEntity> : GenericRepoTest<CompositeID, T>() {

    private val presentId: CompositeID = mockk(relaxed = true)
    private val absentId: CompositeID = mockk(relaxed = true)

    override fun mockID(): CompositeID = presentId

    override fun otherID(): CompositeID = absentId

    override fun MockKMatcherScope.anyID(): CompositeID = any()
}
