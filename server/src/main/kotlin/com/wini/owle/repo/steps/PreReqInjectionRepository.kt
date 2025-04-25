package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.InitialDataPointInjectorEntity
import com.wini.owle.repo.OwleRepoImpl

class PreReqInjectionRepository: OwleRepoImpl<Long, InitialDataPointInjectorEntity>(InitialDataPointInjectorEntity)