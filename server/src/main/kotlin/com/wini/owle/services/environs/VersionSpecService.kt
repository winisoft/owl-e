package com.wini.owle.services.environs

import com.wini.owle.dto.environs.VersionSpecDto
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.mapper.environs.VersionSpecsMapper
import com.wini.owle.repo.VersionSpecIdFactory
import com.wini.owle.repo.environs.VersionSpecRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class VersionSpecService(
    repository: VersionSpecRepository,
    mapper: VersionSpecsMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, VersionSpecDto, VersionSpecDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)