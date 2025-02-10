package com.wini.owle.mapper.runs

import com.wini.owle.dto.BenchmarkingDataDto
import com.wini.owle.entity.runs.BenchmarkingDataEntity
import com.wini.owle.mapper.BaseDbMapper

sealed class BenchmarkingDataMapper : BaseDbMapper<BenchmarkingDataDto, BenchmarkingDataEntity>() {

    }