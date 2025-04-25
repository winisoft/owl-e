package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.PyPackageRepoEntity
import com.wini.owle.repo.OwleRepoImpl


open class PackageRepoRepository: OwleRepoImpl<Long, PyPackageRepoEntity>(PyPackageRepoEntity)