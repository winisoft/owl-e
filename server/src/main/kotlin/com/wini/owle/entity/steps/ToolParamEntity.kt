package com.wini.owle.entity.steps

import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.workflows.ToolEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID

/**
 * Represents a parameter for a tool within a workflow.
 * Each parameter has a name, type, and can optionally have a default value.
 */
class ToolParamEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<ToolParamEntity>(ToolParams)

    /**
     * The tool associated with this parameter.
     */
    var tool by ToolEntity.Companion referencedOn ToolParams.tool

    /**
     * Name of the parameter.
     */
    var name by ToolParams.name

    /**
     * The type declaration associated with this parameter, if defined.
     */
    var typeNode by TypeNodeEntity.Companion referencedOn ToolParams.dataType

    var defaultValue by ToolParams.defaultValue

}