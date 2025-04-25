package com.wini.owle.repo.workflows

import com.wini.owle.entity.workflows.ToolEntity
import com.wini.owle.repo.OwleRepoImpl

class ToolRepository: OwleRepoImpl<Long, ToolEntity>(ToolEntity)