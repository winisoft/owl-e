package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.VersionSpecEntity
import com.wini.owle.repo.OwleRepoImpl

class VersionSpecRepository: OwleRepoImpl<Long, VersionSpecEntity>(VersionSpecEntity)