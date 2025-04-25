package com.wini.owle.services.environs

import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.entity.environs.PyPackageRepoEntity
import com.wini.owle.mapper.environs.PackageRepoMapper
import com.wini.owle.repo.PackageRepoIdFactory
import com.wini.owle.repo.environs.PackageRepoRepository
import com.wini.owle.services.BaseServiceImpl


class PackageRepoService(
    repository: PackageRepoRepository,
    mapper: PackageRepoMapper,
    entityIdFactory: PackageRepoIdFactory
): BaseServiceImpl<Long, PyPackageRepo, PyPackageRepoEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)