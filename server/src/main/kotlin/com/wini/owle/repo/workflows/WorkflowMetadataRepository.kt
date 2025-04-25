package com.wini.owle.repo.workflows

import com.wini.owle.entity.workflows.WorkflowMetadataEntity
import com.wini.owle.repo.OwleRepoImpl


class WorkflowMetadataRepository: OwleRepoImpl<Long, WorkflowMetadataEntity>(WorkflowMetadataEntity)