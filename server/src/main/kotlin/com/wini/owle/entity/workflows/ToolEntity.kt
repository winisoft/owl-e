package com.wini.owle.entity.workflows

import com.wini.owle.entity.ToolPackageDependencies
import com.wini.owle.entity.Tools
import com.wini.owle.entity.environs.ToolPyPackDepEntity
import com.wini.owle.entity.typing.TypeDeclarationEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a tool that can be utilized within a step.
 * Contains the tool's body, optional description, return type, and dependencies.
 */
class ToolEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ToolEntity>(Tools)

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
     * The return type of the tool, if defined.
     */
    var returnTypeDeclarationEntity by TypeDeclarationEntity optionalReferencedOn Tools.returnTypeDeclaration


    /**
     * The packages that use of this tool requires
     */
    val packageDependencies by ToolPyPackDepEntity referrersOn ToolPackageDependencies.tool
}