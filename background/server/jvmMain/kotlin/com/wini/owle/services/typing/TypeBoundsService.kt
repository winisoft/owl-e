package com.wini.owle.services.typing

import com.wini.owle.dto.TypeBoundsDto
import com.wini.owle.entity.typing.TypeBoundDao
import com.wini.owle.mapper.typing.TypeBoundsMapper
import com.wini.owle.repo.typing.TypeBoundRepository
import com.wini.owle.services.BaseServiceImpl


class TypeBoundsService(
    repository: TypeBoundRepository,
    mapper: TypeBoundsMapper
): BaseServiceImpl<Long, TypeBoundsDto, TypeBoundDao>(repository, mapper)