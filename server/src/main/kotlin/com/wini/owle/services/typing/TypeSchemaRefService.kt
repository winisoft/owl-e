package com.wini.owle.services.typing

import com.wini.owle.dto.TypeSchemaRefDto
import com.wini.owle.entity.typing.TypeSchemaRefDao
import com.wini.owle.mapper.typing.TypeSchemaRefsMapper
import com.wini.owle.repo.typing.TypeSchemaRefRepository
import com.wini.owle.services.BaseServiceImpl


class TypeSchemaRefService(
    repository: TypeSchemaRefRepository,
    mapper: TypeSchemaRefsMapper
): BaseServiceImpl<Long, TypeSchemaRefDto, TypeSchemaRefDao>(repository, mapper)