package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.repo.OwleRepoImpl

class BenchmarkingDataRepository: OwleRepoImpl<Long, BenchmarkReportEntity>(BenchmarkReportEntity)