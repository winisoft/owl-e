package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeDefinitionEntity
import com.wini.owle.repo.OwleRepoImpl


class TypeDefinitionRepository: OwleRepoImpl<Long, TypeDefinitionEntity>(TypeDefinitionEntity)