package com.wini.owle.repo.runs.test

import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Collects overall benchmarking data for a specific workflow execution,
 * including execution time, step counts, and the distribution of AI and predetermined steps.
 */
abstract class BenchmarkReportRepoTest : ULongIdGenericRepoTest<BenchmarkReportEntity>() {

    override fun mockEntity(): BenchmarkReportEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): BenchmarkReportEntity {
        return any<BenchmarkReportEntity>()
    }

}

