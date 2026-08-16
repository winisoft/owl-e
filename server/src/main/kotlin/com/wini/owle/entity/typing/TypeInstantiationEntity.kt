package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * A generic applied to arguments: [originNode] is the generic (`List`), [instantiationNode] the
 * parameterized node (`List[int]`), and [args] the arguments in declaration order.
 */
class TypeInstantiationEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeInstantiationEntity>(TypeInstantiations)

    var instantiationNode by TypeNodeEntity referencedOn TypeInstantiations.instantiationNode

    var originNode by TypeNodeEntity referencedOn TypeInstantiations.originNode

    val args: SizedIterable<TypeInstantiationArgEntity>
        get() = TypeInstantiationArgEntity
            .find { TypeInstantiationArgs.instantiation eq id }
            .orderBy(TypeInstantiationArgs.paramPosition to SortOrder.ASC)
}
