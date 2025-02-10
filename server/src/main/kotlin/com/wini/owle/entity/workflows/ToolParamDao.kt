package com.wini.owle.entity.workflows

import com.wini.owle.entity.ToolParams
import com.wini.owle.entity.typing.TypeDeclarationDao
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a parameter for a tool within a workflow.
 * Each parameter has a name, type, and can optionally have a default value.
 */
class ToolParamDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ToolParamDao>(ToolParams)

    /**
     * The tool associated with this parameter.
     */
    var tool by ToolDao referencedOn ToolParams.tool

    /**
     * Name of the parameter.
     */
    var name by ToolParams.name

    /**
     * The type declaration associated with this parameter, if defined.
     */
    var typeDeclaration by TypeDeclarationDao referencedOn ToolParams.typeDeclaration

    /**
     * Whether this parameter has a default value.
     */
    var hasDefault by ToolParams.hasDefault
}