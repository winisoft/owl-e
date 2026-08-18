package com.wini.owle.repo.runs.test

import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Collects overall benchmarking data for a specific workflow execution,
 * including execution time, step counts, and the distribution of AI and predetermined steps.
 */
open class BenchmarkReportRepoTest : ULongIdGenericRepoTest<BenchmarkReportEntity>() {

    override fun mockEntity(): BenchmarkReportEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): BenchmarkReportEntity {
        return any<BenchmarkReportEntity>()
    }

    

}

