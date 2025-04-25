package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.repo.OwleRepoImpl

class TypeDeclarationRepository: OwleRepoImpl<Long, TypeDeclarationEntity>(TypeDeclarationEntity)