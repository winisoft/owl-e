package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestCaseDto
import com.wini.owle.entity.runs.TestCaseEntity
import com.wini.owle.mapper.BaseDbMapper

sealed class TestCaseMapper : BaseDbMapper<TestCaseDto, TestCaseEntity>() {

    }