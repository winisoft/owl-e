package com.wini.owle.mapper

import com.wini.owle.dto.ULongDto
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass

/**
 * Factory interface for Exposed DAOs backed by ULong primary keys.
 *
 * @param E The type of the DAO entity.
 * @param C The Exposed entity class companion (used for lookup/creation).
 */
interface ULongIdDaoFactory<E : ULongEntity, C : ULongEntityClass<E>> {

    /**
     * Finds an existing entity by its ULong ID, or creates a new one if dto.id == 0UL.
     *
     * @param entityClass The entity class companion.
     * @param dto The DTO from which to extract the ID.
     * @param applyProps Lambda that applies field values from the DTO to the entity.
     * @return The existing or newly created entity.
     */
    fun findOrCreate(
        dto: ULongDto,
        applyProps: E.() -> Unit
    ): E

}

/**
 * Implementation of ULongIdDaoFactory for managing Exposed DAOs with ULong primary keys.
 *
 * @param entityClass The Exposed companion object for the entity (e.g., `TypeNodeEntity`).
 */
class ULongIdDaoFactoryImpl<E : ULongEntity, C : ULongEntityClass<E>>(
    private val entityClass: C
) : ULongIdDaoFactory<E, C> {

    /**
     * Returns an entity matching the ID in the DTO or creates a new one if the ID is zero.
     * Throws if a non-zero ID is provided but no such entity is found.
     */
    override fun findOrCreate(
        dto: ULongDto,
        applyProps: E.() -> Unit
    ): E {
        return if (dto.id == 0UL)
            entityClass.new { applyProps() }
        else
            entityClass.findById(dto.id)?.apply(applyProps)
                ?: throw IllegalStateException("${dto::class.simpleName} with id=${dto.id} not found")
    }

}
