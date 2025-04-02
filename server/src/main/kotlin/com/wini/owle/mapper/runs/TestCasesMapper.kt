package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestCaseDto
import com.wini.owle.dto.TestExpectedOutcomeDto
import com.wini.owle.dto.TestInputDto
import com.wini.owle.dto.TestResultDto
import com.wini.owle.entity.*
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.entity.runs.TestResultDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper


class TestCasesMapper(
    private val daoFactory: DaoFactory<TestCaseDao, Long>,
    private val testInputMapper: TestInputsMapper,
    private val testExpectedOutcomeMapper: TestExpectedOutcomesMapper,
    private val testResultMapper: TestResultsMapper,
    private val usersMapper: UsersMapper,
    private val workflowsMapper: WorkflowsMapper,
    private val stepsMapper: StepsMapper
): DbMapper<TestCaseDto, TestCaseDao>() {

    override fun toEntity(dto: TestCaseDto): TestCaseDao {
        val applyProps: TestCaseDao.() -> Unit = {
            user = usersMapper.toEntity(dto.user)
            workflow = dto.workflow?.let { workflowsMapper.toEntity(it) }
            step = dto.step?.let { stepsMapper.toEntity(it) }
            name = dto.name
            description = dto.description
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else {
            TestCaseDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TestCase with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TestCaseDao): TestCaseDto {
        return TestCaseDto(
            id = entity.id.value,
            user = usersMapper.toDto(entity.user),
            name = entity.name,
            description = entity.description,
            workflow = entity.workflow?.let { workflowsMapper.toDto(it) },
            step = entity.step?.let { stepsMapper.toDto(it) },
            inputs = emptyList<TestInputDto>(),
            expectedOutcomes = emptyList<TestExpectedOutcomeDto>(),
            results = emptyList<TestResultDto>()
        )
    }
}