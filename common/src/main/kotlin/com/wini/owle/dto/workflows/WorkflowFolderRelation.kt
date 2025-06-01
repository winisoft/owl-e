package com.wini.owle.dto.workflows

import com.wini.owle.dto.CompositeDto


data class WorkflowFolderRelation(
    val parent: WorkflowFolder,
    val child: WorkflowFolder
): CompositeDto {

    override val owleId: String
        get() = TODO("Not yet implemented")

    override val owleTypedId: String
        get() = super.owleTypedId

}