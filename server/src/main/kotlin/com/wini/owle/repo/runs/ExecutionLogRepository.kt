package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.step.StepRunLogLineEntity
import com.wini.owle.repo.OwleRepoImpl

class ExecutionLogRepository: OwleRepoImpl<Long, StepRunLogLineEntity>(StepRunLogLineEntity)