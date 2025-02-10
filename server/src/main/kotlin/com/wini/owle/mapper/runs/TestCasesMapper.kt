package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestCaseDto
import com.wini.owle.entity.*
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.entity.runs.TestResultDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper
import org.jetbrains.exposed.dao.id.EntityID


class TestCasesMapper(
    private val testInput: TestInputsMapper,
    private val testExpectedOutcomeMapper: TestExpectedOutcomesMapper,
    private val testResultMapper: TestResultsMapper,
    private val usersMapper: UsersMapper,
    private val workflowsMapper: WorkflowsMapper,
    private val stepsMapper: StepsMapper
) : DbMapper<TestCaseDto, TestCaseDao>() {

    override val applyProps: TestCaseDao.(TestCaseDto) -> Unit = { dto ->
        user = usersMapper.run { dto.user.toEntity() }
        workflow = workflowsMapper.run { dto.workflow?.toEntity() }
        step = stepsMapper.run { dto.step?.toEntity() }
        name = dto.name
        description = dto.description
    }

    override fun TestCaseDto.toEntity(): TestCaseDao {
        return if (id == 0L) {
            TestCaseDao.new { applyProps(this@toEntity) }
        } else {
            TestCaseDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TestCase with id=$id not found")
        }
    }

    override fun TestCaseDao.toDto(): TestCaseDto {
        return TestCaseDto(
            id = id.value,
            user = usersMapper.run { user.toDto() },
            name = name,
            description = description,
            workflow = workflowsMapper.run { workflow?.toDto() },
            step = stepsMapper.run { step?.toDto() },
            inputs = TestInputDao.find { TestInputs.testCase eq id }.map { testInput.run { it.toDto() } },
            expectedOutcomes = TestExpectedOutcomeDao.find { TestExpectedOutcomes.testCase eq id }
                .map { testExpectedOutcomeMapper.run { it.toDto() } },
            results = TestResultDao.find { TestResults.testCase eq id }
                .map { testResultMapper.run { it.toDto() } }
        )
    }
}