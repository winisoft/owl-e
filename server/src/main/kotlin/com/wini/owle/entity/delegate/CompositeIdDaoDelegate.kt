package com.wini.owle.entity.delegate

import com.wini.owle.dto.CompositeDto
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


abstract class CompositeIdDaoDelegate<DTO: CompositeDto, E: CompositeEntity>(
    private val entityClass: CompositeEntityClass<E>
): DaoDelegate<CompositeID, DTO, E> {

    override fun buildId(dto: DTO): EntityID<CompositeID> {
        return EntityID(extractId(dto), entityClass.table)
    }

    override fun buildEntityId(id: CompositeID): EntityID<CompositeID> {
        return EntityID(id, entityClass.table)
    }

    abstract fun buildId(params: Map<String, Any?>): CompositeID

    /**
     * Creates a new entity using the given DTO if one doesn't already exist with the derived CompositeID.
     *
     * @param dto The DTO from which to derive the composite ID.
     * @param applyProps Lambda to apply DTO fields to the new entity.
     * @return The existing or newly created entity.
     */
    override fun findOrCreate(dto: DTO, applyProps: E.() -> Unit): E {
        return extractId(dto).let { id ->
            entityClass.findById(id)
        } ?: entityClass.new {
            applyProps()
        }
    }


}