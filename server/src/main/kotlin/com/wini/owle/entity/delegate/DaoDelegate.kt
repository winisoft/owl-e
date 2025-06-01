package com.wini.owle.entity.delegate

import com.wini.owle.dto.BaseDto
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.Entity


interface DaoDelegate<ID: Any, DTO: BaseDto, E: Entity<ID>> {

    val columnsByRequestParam: Map<String, Column<out Any>>

    /**
     * Builds an EntityID type from the DTO type by accessing the relevant property
     */
    fun extractId(dto: DTO): ID

    fun buildEntityId(id: ID): EntityID<ID>

    /**
     * Finds or Creates an entity with the relevant ID properties for a given DTO
     */
    fun findOrCreate(dto: DTO, applyProps: E.() -> Unit): E

    fun buildId(dto: DTO): EntityID<ID>

}