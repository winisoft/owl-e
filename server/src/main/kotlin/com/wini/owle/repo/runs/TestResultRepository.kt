package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.workflow.WorkflowTestResultEntity
import com.wini.owle.repo.OwleRepoImpl

class TestResultRepository: OwleRepoImpl<Long, WorkflowTestResultEntity>(WorkflowTestResultEntity)