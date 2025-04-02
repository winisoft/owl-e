package com.wini.owle.services.environs

import com.wini.owle.dto.environs.VersionSpecDto
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.mapper.environs.VersionSpecsMapper
import com.wini.owle.repo.environs.VersionSpecRepository
import com.wini.owle.services.BaseServiceImpl


class VersionSpecService(
    repository: VersionSpecRepository,
    mapper: VersionSpecsMapper
): BaseServiceImpl<Long, VersionSpecDto, VersionSpecDao>(repository, mapper)