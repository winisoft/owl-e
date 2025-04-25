package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeSchemaRefEntity
import com.wini.owle.repo.OwleRepoImpl

class TypeSchemaRefRepository: OwleRepoImpl<Long, TypeSchemaRefEntity>(TypeSchemaRefEntity)