package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeVarianceEntity
import com.wini.owle.repo.OwleRepoImpl

class TypeVarianceRepository: OwleRepoImpl<Long, TypeVarianceEntity>(TypeVarianceEntity)