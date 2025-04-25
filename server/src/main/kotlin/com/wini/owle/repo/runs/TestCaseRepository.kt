package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.repo.OwleRepoImpl

class TestCaseRepository: OwleRepoImpl<Long, TestCaseEntity>(TestCaseEntity)