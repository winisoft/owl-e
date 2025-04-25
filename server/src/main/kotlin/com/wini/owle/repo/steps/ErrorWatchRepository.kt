package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.repo.OwleRepoImpl

class ErrorWatchRepository: OwleRepoImpl<Long, ErrorWatchEntity>(ErrorWatchEntity)