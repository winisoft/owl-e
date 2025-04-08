package com.wini.owle.entity.composteid

import com.wini.owle.dto.BaseDto
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column


abstract class CompositeIdFactoryImpl<T: BaseDto>: CompositeIdFactory<T> {

    /**
     * A map of Columns found in the exposed ORM Entity whose values contribute to CompositeId Primary Key of that type
     * (which corresponds to this Factory's generic parameter BaseDto type via cross-layer mapping) and the underlying
     * persistence layer tables as the key corresponding to a String value whose content is the database column's name.
     */
    abstract val fkReferences: Map<Column<EntityID<Long>>, String>

    /**
     * Reads Ktor's representation of a request's query parameters and produces from them a CompositeId suitable for
     * identifying the type represented by the classes generic parameter type, returning null if the required parameters
     * are either not found or malformed.
     */
    override fun fromRequestParams(params: Map<String, List<String>>): CompositeID? {
        // map the names of the relevant db table columns to values found under that key in the request query params
        return fkReferences.values.associateWith {
            params[it]
        // return null if the query parameters come in an unexpected format
        }.mapNotNull { (key: String, values: List<String>?) ->
            if (values == null || values.firstOrNull() == null || values.first().toLongOrNull() == null)
                return null
            else
                key to values[0].toLong()
        }.takeIf {
            // logically 'short-circuits' further consideration of the already-determined invalid data provided
            it.size == fkReferences.size
        }?.associate {
            // map the key under which the parameter was provided (its db column name) to the valid Long value it offered
            it.first to it.second
        }?.let { validatedParams: Map<String, Long> ->
            CompositeID {
                // construct the CompositeId by assigning to its columns the valid Long value collected from query params
                for ((entityIdColumn, columnName) in fkReferences) {
                    it[entityIdColumn] = validatedParams[columnName]!!
                }
            }
        }
    }
}