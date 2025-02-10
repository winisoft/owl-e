package com.wini.owle.services.environs

import com.wini.owle.dto.PackageRepoDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.mapper.environs.PackageRepoMapper
import com.wini.owle.repo.environs.PackageRepoRepository
import com.wini.owle.services.BaseServiceImpl


class PackageRepoService(
    repository: PackageRepoRepository,
    mapper: PackageRepoMapper
): BaseServiceImpl<Long, PackageRepoDto, PackageRepoDao>(repository, mapper)