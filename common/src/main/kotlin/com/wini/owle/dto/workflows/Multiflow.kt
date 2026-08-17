package com.wini.owle.dto.workflows

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.user.User
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * A root workflow plus the closure of workflows reachable from it through junctions.
 */
@ModelDefinition
@Serializable
data class Multiflow(

    override val id: ULong,

    val user: User?,

    val name: String,

    val description: String?,

    /** The workflow execution enters at. */
    val rootWorkflow: Workflow,

    val createdAt: String,

    val updatedAt: String,

) : ULongDto
