package com.wini.owle.services.environs

import com.wini.owle.dto.environs.PyPackage
import com.wini.owle.entity.environs.PyPackageEntity
import com.wini.owle.mapper.environs.PackagesMapper
import com.wini.owle.repo.PackageIdFactory
import com.wini.owle.repo.environs.PackageRepository
import com.wini.owle.services.BaseServiceImpl


class PackageService(
    repository: PackageRepository,
    mapper: PackagesMapper,
    entityIdFactory: PackageIdFactory
): BaseServiceImpl<Long, PyPackage, PyPackageEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)