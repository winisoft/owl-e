package com.wini.owle.entity.delegate

import com.wini.owle.dto.ULongDto
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.Column


open class ULongIdDaoDelegate<DTO: ULongDto, E: ULongEntity>(
    private val entityClass: ULongEntityClass<E>
): DaoDelegate<ULong, DTO, E> {

    override val columnsByRequestParam: Map<String, Column<out Any>>
        get() = entityClass.table.idColumns.associateBy { "id" }

    override fun extractId(dto: DTO): ULong {
        return dto.id
    }

    override fun buildEntityId(id: ULong): EntityID<ULong> {
        return EntityID(id, entityClass.table)
    }

    override fun buildId(dto: DTO): EntityID<ULong> {
        return EntityID(extractId(dto), entityClass.table)
    }

    /**
     * Returns an entity matching the ID in the DTO or creates a new one if the ID is zero.
     * Throws if a non-zero ID is provided but no such entity is found.
     */
    override fun findOrCreate(dto: DTO, applyProps: E.() -> Unit): E {
        return if (dto.id == 0UL)
            entityClass.new { applyProps() }
        else
            entityClass.findById(dto.id)?.apply(applyProps)
                ?: throw IllegalStateException("${dto::class.simpleName} with id=${dto.id} not found")
    }
}