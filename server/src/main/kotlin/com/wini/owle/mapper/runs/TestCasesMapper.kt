package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestExpectation
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper


class TestCasesMapper(
    private val daoFactory: DaoFactory<TestCaseEntity, Long>,
    private val testInputMapper: TestInputsMapper,
    private val testExpectedOutcomeMapper: TestExpectedOutcomesMapper,
    private val testResultMapper: TestResultsMapper,
    private val usersMapper: UsersMapper,
    private val workflowsMapper: WorkflowsMapper,
    private val stepsMapper: StepsMapper
): DbMapper<TestCase, TestCaseEntity>() {

    override fun toEntity(dto: TestCase): TestCaseEntity {
        val applyProps: TestCaseEntity.() -> Unit = {
            userEntity = usersMapper.toEntity(dto.user)
            workflow = dto.workflow?.let { workflowsMapper.toEntity(it) }
            step = dto.step?.let { stepsMapper.toEntity(it) }
            name = dto.name
            description = dto.description
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else {
            TestCaseEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TestCase with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TestCaseEntity): TestCase {
        return TestCase(
            id = entity.id.value,
            user = usersMapper.toDto(entity.userEntity),
            name = entity.name,
            description = entity.description,
            workflow = entity.workflow?.let { workflowsMapper.toDto(it) },
            step = entity.step?.let { stepsMapper.toDto(it) },
            inputs = emptyList<TestInput>(),
            expectedOutcomes = emptyList<TestExpectation>(),
            results = emptyList<WorkflowTestResult>()
        )
    }
}