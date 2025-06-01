package com.wini.owle.entity.workflows

import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID

/**
 * Represents metadata for workflows or steps within the Owl-E application.
 * Includes information on authorship, usage permissions, and sharing configuration.
 */
class WorkflowMetadataEntity(id: EntityID<ULong>) : ULongEntity(id) {
    companion object : ULongEntityClass<WorkflowMetadataEntity>(WorkflowMetadatas)

    /**
     * The associated workflow entity.
     * Required. References the `workflows` table.
     */
    var workflow by WorkflowEntity referencedOn WorkflowMetadatas.workflow

    /**
     * The name of the entity (e.g., workflow or step).
     * Required.
     */
    var name by WorkflowMetadatas.name

    /**
     * The version of the entity, following semantic versioning (e.g., "1.0.0").
     * Defaults to "1.0.0" if not provided.
     */
    var version by WorkflowMetadatas.version

    /**
     * Optional copyright information for the entity.
     */
    var copyrightInformation by WorkflowMetadatas.copyrightInformation

    /**
     * Optional contact information for the author(s) of the entity.
     */
    var contactInformation by WorkflowMetadatas.contactInformation

    /**
     * Information about third-party licenses that apply to this entity.
     * Optional.
     */
    var thirdPartyLicenses by WorkflowMetadatas.thirdPartyLicenses

    /**
     * Advises users to exercise discretion when using this workflow or step.
     * Defaults to `false`.
     */
    var discretionAdvisedContent by WorkflowMetadatas.discretionAdvisedContent

    /**
     * Permits commercial use under defined terms:
     * - `PERSONAL_NON_PROFIT` (default): Personal, non-profit use only.
     * - `PERSONAL_PROFIT`: Personal use, regardless of profit.
     * - `CORPORATION`: Corporate use allowed.
     */
    var commercialUsage by WorkflowMetadatas.commercialUsage

    /**
     * Defines credit display requirements:
     * - `REQUIRED` (default): Credit must be displayed.
     * - `UNNECESSARY`: Credit display is not required.
     */
    var creditNotation by WorkflowMetadatas.creditNotation

    /**
     * Allows or disallows redistribution of the entity.
     * Defaults to `false`.
     */
    var allowRedistribution by WorkflowMetadatas.allowRedistribution

    /**
     * Defines modification permissions:
     * - `PROHIBITED` (default): Modifications are not allowed.
     * - `ALLOW_MODIFICATION`: Modifications are allowed but cannot be redistributed.
     * - `ALLOW_MODIFICATION_REDISTRIBUTION`: Modifications and redistribution allowed.
     */
    var modification by WorkflowMetadatas.modification

    /**
     * Tracks the version of the accepted distribution terms.
     * Defaults to "1.0.0".
     */
    var distributionVersion by WorkflowMetadatas.distributionVersion

    /**
     * Timestamp when the user initially accepted the distribution agreement.
     */
    var distributionAcceptedAt by WorkflowMetadatas.distributionAcceptedAt

    /**
     * Timestamp when the distribution agreement was last updated.
     */
    var distributionUpdatedAt by WorkflowMetadatas.distributionUpdatedAt

    /**
     * List of contributors (authors) for the entity.
     * Must have at least one author.
     * References the `users` table through the `workflow_metadata_contributors` link table.
     */
    //val contributors by UserDao via WorkflowMetadataContributors

    /**
     * List of step IDs that have been marked for exclusion from the shared content bundle.
     * References the `steps` table through the `metadata_step_exclusions` link table.
     * If no exclusions exist, all steps are included by default.
     */
    //val excludedSteps by StepDao via MetadataStepExclusions
}

