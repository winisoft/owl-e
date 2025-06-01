package com.wini.owle.repo

import com.wini.owle.dto.transforms.Transform
import com.wini.owle.entity.transforms.TransformEntity


interface TransformRepo {
    suspend fun getEligibleForType(typeId: ULong): List<Transform>
}

class TransformRepoImpl(
    private val transformRepo: GenericRepoImpl<ULong, TransformEntity>
): TransformRepo, OwleRepo<ULong, TransformEntity> by transformRepo {

    override suspend fun getEligibleForType(typeId: ULong): List<Transform> {
        return transformRepo.getWhere { typeId in it.eligibleTypes }
    }
}