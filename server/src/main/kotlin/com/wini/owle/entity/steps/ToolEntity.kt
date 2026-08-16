package com.wini.owle.entity.steps

import com.wini.owle.entity.typing.CallableSignatureEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * Represents a tool that can be utilized within a step.
 *
 * [body] holds the actual Python source. The call contract comes from [signature]; there is no
 * longer a separate per-tool parameter table — parameters are the signature's own.
 */
class ToolEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<ToolEntity>(Tools)

    /**
     * Name of the tool.
     */
    var name by Tools.name

    /**
     * Optional description of the tool.
     */
    var description by Tools.description

    /**
     * The main body or code of the tool.
     */
    var body by Tools.body

    /**
     * Typed call contract; its `parameters` are what a step's reads bind against.
     */
    var signature by CallableSignatureEntity optionalReferencedOn Tools.signature

    /**
     * The return type of the tool, if defined.
     */
    var returnTypeNode by TypeNodeEntity optionalReferencedOn Tools.returnType
}
