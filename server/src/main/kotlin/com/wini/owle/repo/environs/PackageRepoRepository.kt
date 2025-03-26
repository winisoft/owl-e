package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.repo.OwleRepoImpl


open class PackageRepoRepository: OwleRepoImpl<Long, PackageRepoDao>(PackageRepoDao)