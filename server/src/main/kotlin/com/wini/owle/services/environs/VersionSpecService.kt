package com.wini.owle.services.environs

import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.entity.environs.VersionSpecEntity
import com.wini.owle.mapper.environs.VersionSpecsMapper
import com.wini.owle.repo.VersionSpecIdFactory
import com.wini.owle.repo.environs.VersionSpecRepository
import com.wini.owle.services.BaseServiceImpl


class VersionSpecService(
    repository: VersionSpecRepository,
    mapper: VersionSpecsMapper,
    entityIdFactory: VersionSpecIdFactory
): BaseServiceImpl<Long, VersionSpec, VersionSpecEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)