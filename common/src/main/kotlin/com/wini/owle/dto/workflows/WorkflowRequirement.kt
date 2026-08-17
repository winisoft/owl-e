package com.wini.owle.dto.workflows

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.Schema
import com.wini.owle.schema.api.ApiEndpointDefinition
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable

/**
 * A distribution a workflow's environment must provide.
 *
 * A released package is identified by **(distribution, version) together** — `version_id` alone
 * does not name one, because many distributions share a version number. That is why the
 * database enforces a compound foreign key into `packages` rather than a reference to
 * `versions`, and why the two halves appear separately here.
 *
 * [requiredPackageVersion] being null is meaningful rather than missing: it says no version was
 * pinned, so the distribution installs as-is — `pip install numpy`.
 */
@ModelDefinition
@ApiEndpointDefinition("api.workflows.workflowRequirement", ["workflows"], "/workflow-requirement")
@Serializable
data class WorkflowRequirement(

    val workflow: Workflow,

    /**
     * The distribution to install. Always present — a requirement always names *what*, even when
     * it declines to say which release.
     */
    val requiredDist: Distribution,

    /**
     * The version half of the package key, or null for "no version pinned".
     *
     * Held as the raw id rather than a `Version`: the constraint is on the pair
     * (requiredDist, requiredPackageVersion), and resolving it to a `DistributionPackage`
     * requires both halves.
     */
    val requiredPackageVersion: ULong?,

    /**
     * The extra whose additional dependencies are also wanted — the `[dev]` in `requests[dev]`.
     */
    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.WorkflowRequirements.REQUIRED_EXTRA_NAME_MAX_LENGTH)
    val requiredExtraName: String?

) : CompositeDto {

    override val owleId: String
        get() = "workflow=${workflow.owleId},requiredDist=${requiredDist.owleId}," +
                "requiredPackageVersion=${requiredPackageVersion ?: "unpinned"}"
}
