package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.entity.runs.workflow.TestResultEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.runs.TestOutcomeExpectations.dataPoint
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointMapper


class TestOutcomeActualMapper(
    private val daoDelegate: DaoDelegate<ULong, TestOutcomeActual, TestResultEntity>,
    private val dataPointMapper: DataPointMapper
): DbMapper<ULong, TestOutcomeActual, TestResultEntity> {

    override fun toEntity(dto: TestOutcomeActual): TestResultEntity {
        return daoDelegate.findOrCreate(dto) {
            passed = dto.passed
        }
    }

    override fun toDto(entity: TestResultEntity): TestOutcomeActual {
        return TestOutcomeActual(
            id = entity.id.value,
            passed = entity.passed,
        )
    }
}