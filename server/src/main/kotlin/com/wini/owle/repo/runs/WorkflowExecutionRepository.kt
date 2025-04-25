package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.repo.OwleRepoImpl

class WorkflowExecutionRepository: OwleRepoImpl<Long, WorkflowRunEntity>(WorkflowRunEntity)