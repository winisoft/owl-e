package com.wini.owle.services.runs

import com.wini.owle.dto.runs.TestInputDto
import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.mapper.runs.TestInputsMapper
import com.wini.owle.repo.TestInputIdFactory
import com.wini.owle.repo.runs.TestInputRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class TestInputService(
    repository: TestInputRepository,
    mapper: TestInputsMapper,
    entityIdFactory: TestInputIdFactory
): BaseServiceImpl<Long, TestInputDto, TestInputDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)