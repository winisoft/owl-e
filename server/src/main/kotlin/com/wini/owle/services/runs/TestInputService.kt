package com.wini.owle.services.runs

import com.wini.owle.dto.runs.TestInputDto
import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.mapper.runs.TestInputsMapper
import com.wini.owle.repo.runs.TestInputRepository
import com.wini.owle.services.BaseServiceImpl


class TestInputService(
    repository: TestInputRepository,
    mapper: TestInputsMapper
): BaseServiceImpl<Long, TestInputDto, TestInputDao>(repository, mapper)