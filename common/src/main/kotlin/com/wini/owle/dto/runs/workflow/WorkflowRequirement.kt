package com.wini.owle.dto.runs.workflow

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * Represents a required package installation an environment will be inflated with to facilitate the successful
 * execution of the workflow.
 */
@Serializable
@ModelDefinition
@ApiEndpointDefinition("api.runs.workflow.workflowRequirement", ["executions"], "/workflow-requirement")
data class WorkflowRequirement(

    /**
     * The id of the configured workflow whose environment requires this installation
     */
    val workflowId: ULong,

    /**
     * Some unique installable package of a Python dependency for workflow runtime execution.
     */
    val requiredPackage: DistributionPackage,

    /**
     * Optionally, the name of a potential variant configuration install of the relevant package, identified by the
     * name of the extra that defines the distinct dependencies, features, requirements, etc. of the package offering
     */
    val requiredExtraName: String?

): CompositeDto {

    override val owleId: String
        get() = "workflowId=$workflowId, requiredPackage=${requiredPackage.owleId}"

}
