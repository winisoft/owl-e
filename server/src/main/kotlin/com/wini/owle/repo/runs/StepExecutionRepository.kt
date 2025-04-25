package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.step.StepRunEntity
import com.wini.owle.repo.OwleRepoImpl

class StepExecutionRepository: OwleRepoImpl<Long, StepRunEntity>(StepRunEntity)