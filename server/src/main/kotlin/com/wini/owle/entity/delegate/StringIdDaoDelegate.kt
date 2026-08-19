package com.wini.owle.entity.delegate

import com.wini.owle.dto.StringDto
import com.wini.owle.entity.StringEntityClass
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.Column


open class StringIdDaoDelegate<DTO: StringDto, E: Entity<String>>(
    private val entityClass: StringEntityClass<E>
): DaoDelegate<String, DTO, E> {

    override val columnsByRequestParam: Map<String, Column<out Any>>
        get() = entityClass.table.idColumns.associateBy { "name" }

    override fun extractId(dto: DTO): String {
        return dto.name
    }

    override fun buildEntityId(id: String): EntityID<String> {
        return EntityID(id, entityClass.table)
    }

    override fun buildId(dto: DTO): EntityID<String> {
        return EntityID(extractId(dto), entityClass.table)
    }

    override fun findOrCreate(
        dto: DTO,
        applyProps: E.() -> Unit
    ): E {
        return if (dto.name == "")
            entityClass.new { applyProps() }
        else
            entityClass.findById(dto.name)?.apply(applyProps)
                ?: throw IllegalStateException("${dto::class.simpleName} with name=${dto.name} not found")
    }

}