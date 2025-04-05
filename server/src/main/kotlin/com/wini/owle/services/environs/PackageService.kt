package com.wini.owle.services.environs

import com.wini.owle.dto.environs.PackageDto
import com.wini.owle.entity.environs.PackageDao
import com.wini.owle.mapper.environs.PackagesMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.PackageIdFactory
import com.wini.owle.repo.environs.PackageRepository
import com.wini.owle.services.BaseServiceImpl


class PackageService(
    repository: PackageRepository,
    mapper: PackagesMapper,
    entityIdFactory: PackageIdFactory
): BaseServiceImpl<Long, PackageDto, PackageDao>(
    repository,
    mapper,
    entityIdFactory
)