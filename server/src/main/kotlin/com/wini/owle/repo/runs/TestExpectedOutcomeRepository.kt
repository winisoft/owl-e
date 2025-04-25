package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.test.TestExpectationEntity
import com.wini.owle.repo.OwleRepoImpl

class TestExpectedOutcomeRepository: OwleRepoImpl<Long, TestExpectationEntity>(TestExpectationEntity)