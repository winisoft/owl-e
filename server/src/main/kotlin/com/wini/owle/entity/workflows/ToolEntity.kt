package com.wini.owle.entity.workflows

import com.wini.owle.entity.steps.Tools
import com.wini.owle.entity.typing.CallableSignatureEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a tool that can be utilized within a step.
 * Contains the tool's body, optional description, return type, and dependencies.
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

    var signatureNode by CallableSignatureEntity optionalReferencedOn Tools.signatureNode

    /**
     * The return type of the tool, if defined.
     */
    var returnTypeNode by TypeNodeEntity optionalReferencedOn Tools.returnType


}