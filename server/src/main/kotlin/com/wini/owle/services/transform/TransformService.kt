package com.wini.owle.services.transform

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.error.GenericError
import com.wini.owle.mapper.DbMapper
import com.wini.owle.repo.ExposedTransactionExecutor
import com.wini.owle.repo.GenericRepoImpl
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.TransactionExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


interface TransformService {
    suspend fun getEligibleForType(type: TypeNode): AsyncResult<List<Transform>>
}


class TransformServiceImpl(
    private val transformRepo: GenericRepoImpl<ULong, TransformEntity>,
    private val transformMapper: DbMapper<ULong, Transform, TransformEntity>,
    private val transactions: TransactionExecutor = ExposedTransactionExecutor
): TransformService, OwleRepo<ULong, TransformEntity> by transformRepo {

    // `getWhere` closes its own transaction before returning, so mapping the results after it
    // throws "Can't init value outside the transaction". Query and mapping share one scope.

    override suspend fun getEligibleForType(type: TypeNode): AsyncResult<List<Transform>> = runCatching {
        withContext(Dispatchers.IO) {
            transactions.run {
                getWhere {
                    it.eligibleTypes.contains(TypeNodeEntity[type.id])
                }.map {
                    transformMapper.toDto(it)
                }
            }
        }
    }.fold(
        onSuccess  = { AsyncResult.Success(it) },
        onFailure  = {
            GenericError("${it::class.simpleName}: ${it.message}").asFailure()
        }
    )

}