package com.wini.owle.entity.workflows

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * Sharing and licensing terms for a workflow that is distributed to other users.
 * At most one row per workflow.
 */
class WorkflowMetadataEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<WorkflowMetadataEntity>(Metadatas)

    /**
     * The associated workflow. Unique — one metadata record per workflow.
     */
    var workflow by WorkflowEntity referencedOn Metadatas.workflow

    /**
     * The name the workflow is distributed under.
     */
    var name by Metadatas.name

    /**
     * The version of the entity, following semantic versioning (e.g., "1.0.0").
     */
    var version by Metadatas.version

    /**
     * Optional copyright information for the entity.
     */
    var copyrightInformation by Metadatas.copyrightInformation

    /**
     * Optional contact information for the author(s) of the entity.
     */
    var contactInformation by Metadatas.contactInformation

    /**
     * Information about third-party licenses that apply to this entity.
     */
    var thirdPartyLicenses by Metadatas.thirdPartyLicenses

    /**
     * Advises users to exercise discretion when using this workflow.
     */
    var discretionAdvisedContent by Metadatas.discretionAdvisedContent

    /**
     * Permits commercial use under defined terms:
     * - `PERSONAL_NON_PROFIT` (default): Personal, non-profit use only.
     * - `PERSONAL_PROFIT`: Personal use, regardless of profit.
     * - `CORPORATION`: Corporate use allowed.
     */
    var commercialUsage by Metadatas.commercialUsage

    /**
     * Defines credit display requirements:
     * - `REQUIRED` (default): Credit must be displayed.
     * - `UNNECESSARY`: Credit display is not required.
     */
    var creditNotation by Metadatas.creditNotation

    /**
     * Allows or disallows redistribution of the entity.
     */
    var allowRedistribution by Metadatas.allowRedistribution

    /**
     * Defines modification permissions:
     * - `PROHIBITED` (default): Modifications are not allowed.
     * - `ALLOW_MODIFICATION`: Modifications are allowed but cannot be redistributed.
     * - `ALLOW_MODIFICATION_REDISTRIBUTION`: Modifications and redistribution allowed.
     */
    var modification by Metadatas.modification

    /**
     * Tracks the version of the accepted distribution terms.
     */
    var distributionVersion by Metadatas.distributionVersion

    /**
     * Timestamp when the user initially accepted the distribution agreement.
     */
    var distributionAcceptedAt by Metadatas.distributionAcceptedAt

    /**
     * Timestamp when the distribution agreement was last updated.
     */
    var distributionUpdatedAt by Metadatas.distributionUpdatedAt

    /**
     * Steps withheld from the shared bundle. No rows means every step ships.
     */
    val excludedSteps: SizedIterable<StepEntity>
            by StepEntity.via(MetadataStepExclusions.metadata, MetadataStepExclusions.excludedStep)
}
