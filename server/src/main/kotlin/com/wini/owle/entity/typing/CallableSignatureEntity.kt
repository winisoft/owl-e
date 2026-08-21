package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * The call contract of a callable type node: its parameters and return type.
 *
 * Bound to the callable's own `type_nodes` row (one signature per node), not to a schema member —
 * a method's owning class is reached through [CallableOwners] instead.
 */
class CallableSignatureEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<CallableSignatureEntity>(CallableSignatures)

    /**
     * The callable's own type node. Unique — one signature per node.
     */
    var typeNode by TypeNodeEntity referencedOn CallableSignatures.typeNode

    /** Null when the callable declares no return annotation - see [CallableSignatures.returnTypeNode]. */
    var returnTypeNode by TypeNodeEntity optionalReferencedOn CallableSignatures.returnTypeNode

    var docstring by CallableSignatures.docString

    var isStatic by CallableSignatures.isStatic

    var isClassmethod by CallableSignatures.isClassMethod

    /**
     * Declared parameters in call order.
     */
    val parameters: SizedIterable<CallableParameterEntity>
        get() = CallableParameterEntity
            .find { CallableParameters.signature eq id }
            .orderBy(CallableParameters.position to SortOrder.ASC)
}
