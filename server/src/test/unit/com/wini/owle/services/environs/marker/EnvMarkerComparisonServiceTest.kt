package com.wini.owle.services.environs.marker

import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.dto.testing.EnvironsDtoFactory.envMarkerComparison
import com.wini.owle.entity.environs.marker.EnvMarkerComparisonEntity
import com.wini.owle.services.ServiceTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * [ServiceTest] against a `ULong`-keyed type.
 *
 * One instantiation, not one per type. `GenericServiceImpl` is a single hand-written class with no
 * per-type behaviour, and this suite mocks the repository, mapper and delegate it sits on — so
 * running it against a second type re-executes identical code with interchangeable doubles. What
 * *does* vary per type is the wiring, and that is asserted once, over the whole graph, in
 * `DiGraphTest`.
 */
abstract class EnvMarkerComparisonServiceTest :
    ServiceTest<ULong, EnvMarkerComparison, EnvMarkerComparisonEntity>() {

    override fun createSampleDto(): EnvMarkerComparison = envMarkerComparison()

    // relaxed, and deliberately not given an id: `Entity.getId()` is final and mockk cannot stub
    // it in this build, so anything reading an id off a mocked entity throws
    override fun createSampleEntity(): EnvMarkerComparisonEntity = mockk(relaxed = true)

    override fun createSampleId(): ULong = 1UL

    override fun MockKMatcherScope.anyID(): ULong = any()
}
