package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.test.TestOutcomeExpectation
import com.wini.owle.entity.runs.test.TestOutcomeExpectationEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointMapper


class TestExpectationMapper(
    private val daoDelegate: DaoDelegate<ULong, TestOutcomeExpectation, TestOutcomeExpectationEntity>,
    private val dataPointMapper: DataPointMapper
): DbMapper<ULong, TestOutcomeExpectation, TestOutcomeExpectationEntity> {

    override fun toEntity(dto: TestOutcomeExpectation): TestOutcomeExpectationEntity {
        return daoDelegate.findOrCreate(dto) {
            assertion = dto.assertion
        }
    }

    override fun toDto(entity: TestOutcomeExpectationEntity): TestOutcomeExpectation {
        return TestOutcomeExpectation(
            id = entity.id.value,
            dataPoint = dataPointMapper.toDto(entity.dataPoint),
            assertion = entity.assertion
        )
    }
}