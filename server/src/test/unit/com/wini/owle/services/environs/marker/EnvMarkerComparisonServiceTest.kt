package com.wini.owle.services.environs.marker

import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.dto.testing.EnvironsDtoFactory.envMarkerComparison
import com.wini.owle.entity.environs.marker.EnvMarkerComparisonEntity
import com.wini.owle.entity.testing.EnvironsMockkFactory
import com.wini.owle.services.ServiceTest
import io.mockk.MockKMatcherScope

open class EnvMarkerComparisonServiceTest: ServiceTest<ULong, EnvMarkerComparison, EnvMarkerComparisonEntity>() {

    override fun createSampleDto(): EnvMarkerComparison {
        return envMarkerComparison()
    }

    override fun createSampleEntity(): EnvMarkerComparisonEntity {
        return EnvironsMockkFactory.envMarkerComparison()
    }

    override fun createSampleId(): ULong {
        return EnvironsMockkFactory.envMarkerComparison().id.value
    }

    override fun MockKMatcherScope.anyID(): ULong {
        return any<ULong>()
    }
}