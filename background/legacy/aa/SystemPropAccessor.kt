package com.wini.owle.air.domain.aa

import com.wini.owle.air.db.sysprop.SystemPropEntity
import com.wini.owle.air.db.library.sysprop.SystemPropRepository
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.domain.sysprop.SystemProperty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SystemPropAccessor(
    private val repository: SystemPropRepository
) : DomainAccessor<SystemProperty, SystemPropEntity>(repository) {

    fun getAll(): Flow<List<SystemProperty>> = repository.all().map { list ->
        list.map { each -> each.run {
                SystemProperty(id, key, DataType.fromEntity(dataType), description)
            }
        }
    }
    override fun SystemProperty.mapToEntity(): SystemPropEntity = SystemPropEntity(
        id, key, dataType.toEntity(), description
    )

}