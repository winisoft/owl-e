package com.wini.owle.services.typing

import com.wini.owle.dto.TypeVarianceDto
import com.wini.owle.entity.typing.TypeVarianceDao
import com.wini.owle.mapper.typing.TypeVariancesMapper
import com.wini.owle.repo.typing.TypeVarianceRepository
import com.wini.owle.services.BaseServiceImpl


class TypeVarianceService(
    repository: TypeVarianceRepository,
    mapper: TypeVariancesMapper
): BaseServiceImpl<Long, TypeVarianceDto, TypeVarianceDao>(repository, mapper)