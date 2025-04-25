package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeBoundEntity
import com.wini.owle.repo.OwleRepoImpl

class TypeBoundRepository: OwleRepoImpl<Long, TypeBoundEntity>(TypeBoundEntity)