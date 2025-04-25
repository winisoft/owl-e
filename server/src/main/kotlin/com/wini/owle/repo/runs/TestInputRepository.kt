package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.repo.OwleRepoImpl

class TestInputRepository: OwleRepoImpl<Long, TestInputEntity>(TestInputEntity)