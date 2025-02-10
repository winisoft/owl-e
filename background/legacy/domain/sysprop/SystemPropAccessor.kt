package com.wini.owle.air.domain.sysprop

import com.wini.owle.air.db.sysprop.SystemPropEntity
import com.wini.owle.air.db.library.sysprop.SystemPropRepository
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.domain.repo.DomainAccessor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SystemPropAccessor(
    private val repository: SystemPropRepository
) : DomainAccessor<SystemProp, SystemPropEntity>(repository) {

    fun getAll(): Flow<List<SystemProp>> = repository.all().map { list ->
        list.map { each -> each.run {
                SystemProp(id, key, DataType.fromEntity(dataType), description)
            }
        }
    }
    override fun SystemProp.mapToEntity(): SystemPropEntity = SystemPropEntity(
        id, key, dataType.toEntity(), description
    )

}