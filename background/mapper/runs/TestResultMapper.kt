package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestResultDto
import com.wini.owle.entity.runs.TestResultEntity
import com.wini.owle.mapper.BaseDbMapper

sealed class TestResultMapper : BaseDbMapper<TestResultDto, TestResultEntity>() {

    }