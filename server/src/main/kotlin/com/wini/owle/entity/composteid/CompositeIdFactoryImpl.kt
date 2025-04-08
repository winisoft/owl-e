package com.wini.owle.entity.composteid

import com.wini.owle.dto.BaseDto
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column


abstract class CompositeIdFactoryImpl<T: BaseDto>: CompositeIdFactory<T> {

    abstract val fkReferences: Map<Column<EntityID<Long>>, String>

    override fun fromRequestParams(params: Map<String, List<String>>): CompositeID? {
        return fkReferences.values.associateWith {
            params[it]
        }.mapNotNull { (key: String, values: List<String>?) ->
            if (values == null || values.firstOrNull() == null || values.first().toLongOrNull() == null)
                return null
            else
                key to values[0].toLong()
        }.takeIf {
            it.size == fkReferences.size
        }?.associate {
            it.first to it.second
        }?.let { validatedParams: Map<String, Long> ->
            CompositeID {
                for ((colEntityId, constituentId) in fkReferences) {
                    it[colEntityId] = validatedParams[constituentId]!!
                }
            }
        }
    }
}