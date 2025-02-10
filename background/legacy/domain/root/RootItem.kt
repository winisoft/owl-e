package com.wini.owle.air.domain.root

import arrow.core.Either
import com.wini.owle.air.db.LoRootItemEntity
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.db.root.RootRepository
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.model.item.LoRootItem
import com.wini.owle.air.domain.repo.EntityToModelMapper
import com.wini.owle.air.domain.repo.LoError
import com.wini.owle.air.domain.repo.ModelToEntityMapper
import com.wini.owle.air.domain.model.prop.RunningState
import com.wini.owle.air.domain.model.prop.RunningState.Companion.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf


/**
 * Unlike the other domain-layer data accessors, the [LoRootItem] is unique in that does not require create or
 * delete functions. It does, however, require updates to inform of isExpanded and isEnabled changes. However,
 * the variety of queries the data access layer needs to facilitate is so predictable and unique, there
 * is nothing to be gained from a base class in this case.
 */
class RootItem(
    private val rootRepository: RootRepository
): ModelToEntityMapper<LoRootItem, LoRootItemEntity>,
   EntityToModelMapper<LoRootItemEntity, LoRootItem> {

    fun all(): Flow<List<LoItem>> = flowOf(rootRepository.queries.selectWhereId0().executeAsList()).mapToModel()
    fun get(): LoRootItem = rootRepository.queries.selectWhereId0().executeAsOne().mapToModel()

    fun collapse(): Flow<Either<LoError, Unit>> {
        return rootRepository.setIsExpandedFalse().map { result ->
            result.mapLeft {
                LoError.fromThrowable(it)
            }
        }
    }

    suspend fun save(rootItem: LoRootItem): Flow<Either<LoError, Unit>> = rootItem.mapToEntity().run {
        rootRepository.update(this)
    }.map { result ->
        result.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    fun setRunningState(runningState: RunningState): Flow<Either<LoError, Unit>> {
        return rootRepository.updateSetRunningState(runningState.toEntity()).map { result ->
            result .mapLeft { cause ->
                LoError.fromThrowable(cause)
            }
        }
    }

    override fun LoRootItem.mapToEntity(): LoRootItemEntity {
        return LoRootItemEntity(id, runningState.toEntity(), isExpanded)
    }

    override fun LoRootItemEntity.mapToModel(): LoRootItem {
        return LoRootItem(RunningState.fromEntity(runningState), isExpanded)
    }
}