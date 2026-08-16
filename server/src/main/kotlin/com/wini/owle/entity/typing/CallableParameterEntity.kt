package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


class CallableParameterEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<CallableParameterEntity>(CallableParameters)

    /**
     * A callable signature this parameter exists for
     */
    var signature by CallableSignatureEntity referencedOn CallableParameters.signature

    /**
     * The TypeNode of this parameter's type
     */
    var paramTypeNode by TypeNodeEntity referencedOn CallableParameters.paramTypeNode

    /**
     * A 0-based index of parameter positions
     */
    var position by CallableParameters.position

    /**
     *  e.g. 'x', 'y', 'flags'
     */
    var paramName by CallableParameters.paramName

    /**
     * if *args
     */
    var isVarArg by CallableParameters.isVarArg

    /**
     * if **kwargs
     */
    var isKwarg by CallableParameters.isKwarg

    /**
     * default literal text, if any
     */
    var defaultValueText by CallableParameters.defaultValueText
}
