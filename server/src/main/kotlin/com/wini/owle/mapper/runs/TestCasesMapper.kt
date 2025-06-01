package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestOutcomeExpectation
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.WorkflowMapper


class TestCasesMapper(
    private val daoDelegate: DaoDelegate<ULong, TestCase, TestCaseEntity>,
    private val testInputMapper: TestInputMapper,
    private val testExpectationMapper: TestExpectationMapper,
    private val testOutcomeActualMapper: TestOutcomeActualMapper,
    private val usersMapper: UsersMapper,
    private val workflowMapper: WorkflowMapper,
    private val stepsMapper: StepsMapper
): DbMapper<ULong, TestCase, TestCaseEntity> {

    override fun toEntity(dto: TestCase): TestCaseEntity {
        return daoDelegate.findOrCreate(dto) {
            workflow = dto.workflow?.let { workflowMapper.toEntity(it) }
            step = dto.step?.let { stepsMapper.toEntity(it) }
            name = dto.name
            description = dto.description
        }
    }

    override fun toDto(entity: TestCaseEntity): TestCase {
        return TestCase(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            workflow = entity.workflow?.let { workflowMapper.toDto(it) },
            step = entity.step?.let { stepsMapper.toDto(it) },
            inputs = emptyList<TestInput>(),
            expectedOutcomes = emptyList<TestOutcomeExpectation>(),
            results = emptyList<TestOutcomeActual>()
        )
    }
}