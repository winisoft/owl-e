package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.CompositeID

class TransformTargetTypeRepository: OwleRepoImpl<CompositeID, TransformTargetTypeEntity>(TransformTargetTypeEntity)