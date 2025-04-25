package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ErrorWatchFnEntity
import com.wini.owle.repo.OwleRepoImpl

class ErrorWatchFnRepository: OwleRepoImpl<Long, ErrorWatchFnEntity>(ErrorWatchFnEntity)