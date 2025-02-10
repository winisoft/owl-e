package com.wini.owle.air.domain.prefs

import com.wini.owle.air.db.prefs.SharedPrefEntity
import com.wini.owle.air.db.library.prefs.SharedPrefRepository
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.domain.repo.ModelToEntityMapper
import com.wini.owle.air.domain.sysprop.SystemProp
import com.wini.owle.air.inlines.SharedPrefRepoId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SharedPrefAccessor(
    private val repository: SharedPrefRepository
): DomainAccessor<SharedPref, SharedPrefEntity>(repository),
   ModelToEntityMapper<SharedPref, SharedPrefEntity> by object:
   ModelToEntityMapper<SharedPref, SharedPrefEntity> {
       override fun SharedPref.mapToEntity(): SharedPrefEntity {
           return SharedPrefEntity(
               sharedPrefId,
               SharedPrefRepoId(source),
               key,
               defaultValue,
               description,
               dataType.toEntity()
           )
       }
   } {

    fun getAll(): Flow<List<SharedPref>> = repository.all().map { list ->
        list.map { each ->
            each.run {
                SharedPref(id, sharedPrefRepoId.value, key, defaultValue, DataType.fromEntity(dataType), description)
            }
        }
    }
}