package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ToolParamDataPointEntity
import com.wini.owle.repo.OwleRepoImpl

class ProvisionRepository: OwleRepoImpl<Long, ToolParamDataPointEntity>(ToolParamDataPointEntity)