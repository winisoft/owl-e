package com.wini.owle.repo.workflows

import com.wini.owle.entity.workflows.WorkflowMetadataDao
import com.wini.owle.repo.OwleRepoImpl


class WorkflowMetadataRepository: OwleRepoImpl<Long, WorkflowMetadataDao>(WorkflowMetadataDao)