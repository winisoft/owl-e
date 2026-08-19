package com.wini.owle.entity.delegate

import com.wini.owle.dto.CompositeDto
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Base for delegates over composite-keyed entities.
 *
 * [columnsByRequestParam] and [buildId] used to be abstract, and every one of the twenty-four
 * hand-written subclasses had to restate the key. Nineteen of them got it wrong in the same way —
 * an empty `CompositeID { }` body, which compiles and then throws
 * "must be initialized with at least one key column mapping" the first time anything calls it.
 * Since `columnsByRequestParam` is what `CompositeIdEndpointImpl` reads to turn request parameters
 * into an id, that left composite routes unable to resolve one at all.
 *
 * Both are now derived from `IdTable.idColumns`, which is the same information the table already
 * declares in its `primaryKey`. Nothing restates it, so nothing can restate it wrongly.
 *
 * [extractId] cannot be derived this way — going from a DTO to a key needs to know which DTO
 * property corresponds to which key column — so it stays abstract and is generated per type.
 */
abstract class CompositeIdDaoDelegate<DTO : CompositeDto, E : CompositeEntity>(
    private val entityClass: CompositeEntityClass<E>
) : DaoDelegate<CompositeID, DTO, E> {

    /** The key columns, addressed by their database column name. */
    override val columnsByRequestParam: Map<String, Column<out Any>>
        get() = entityClass.table.idColumns.associateBy { it.name }

    /**
     * Builds a key from request parameters, keyed by column name.
     *
     * A parameter missing for any key column is an error rather than a partial id: a
     * `CompositeID` short of a column cannot address a row, and failing here names the missing
     * column instead of surfacing later as a confusing miss.
     */
    @Suppress("UNCHECKED_CAST")
    open fun buildId(params: Map<String, Any?>): CompositeID = CompositeID {
        entityClass.table.idColumns.forEach { column ->
            val value = params[column.name]
                ?: error("no value supplied for key column '${column.name}' of ${entityClass.table.tableName}")
            it[column as Column<EntityID<Comparable<Any>>>] = value as Comparable<Any>
        }
    }

    override fun buildId(dto: DTO): EntityID<CompositeID> =
        EntityID(extractId(dto), entityClass.table)

    override fun buildEntityId(id: CompositeID): EntityID<CompositeID> =
        EntityID(id, entityClass.table)

    override fun findOrCreate(dto: DTO, applyProps: E.() -> Unit): E {
        val id = extractId(dto)
        return entityClass.findById(id) ?: entityClass.new(id) { applyProps() }
    }
}
