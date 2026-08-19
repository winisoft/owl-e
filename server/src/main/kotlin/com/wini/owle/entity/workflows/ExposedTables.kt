package com.wini.owle.entity.workflows

import com.wini.owle.entity.OwleCompositeIdTable

import com.wini.owle.entity.dbEnum

import com.wini.owle.dto.workflows.enums.AuthorshipCredit
import com.wini.owle.dto.workflows.enums.CommercialUse
import com.wini.owle.dto.workflows.enums.ModificationTerms
import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.entity.environs.Distributions
import com.wini.owle.entity.environs.PythonVersions
import com.wini.owle.entity.environs.DistributionPackages
import com.wini.owle.entity.steps.Tools
import com.wini.owle.entity.typing.TypeNodes
import com.wini.owle.entity.user.Users
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.datetime.timestamp


object Workflows : ULongIdTable("workflows") {
    val user = reference("user_id", Users).nullable()
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
    val pythonVersion = reference("python_version_id", PythonVersions)
    val parentFolder = reference("parent_folder_id", WorkflowFolders).nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
}


object WorkflowFolders : ULongIdTable("workflow_folders") {
    val user = reference("user_id", Users)
    val parentFolder = reference("parent_folder_id", WorkflowFolders).nullable()
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
}


object WorkflowFolderRelations : OwleCompositeIdTable("workflow_folder_relations") {
    val parent = keyReference("parent_id", WorkflowFolders)
    val child = keyReference("child_id", WorkflowFolders)
    override val primaryKey = PrimaryKey(parent, child)
}


/**
 * Canvas nesting for steps. A grouper positions and collapses a region of the editor; it carries
 * no sequencing — that lives entirely in step routes.
 */
object Groupers : ULongIdTable("groupers") {
    val workflow = reference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val parentGrouper = reference("parent_grouper_id", Groupers, onDelete = ReferenceOption.CASCADE).nullable()
    val name = varchar("name", 1024)
    val description = varchar("description", 8192).nullable()
    /**
     * Canvas position. `groupers_chk_pos` in the database bounds both axes to +/- 1,000,000; see
     * `Schema.Canvas` for why the plane stops there rather than anywhere larger.
     */
    val posX = double("pos_x").default(0.0)
    val posY = double("pos_y").default(0.0)
    /** `#RRGGBB`, enforced by `groupers_chk_color` in the database and by `HexColorFormat` in the DTO. */
    val color = varchar("color", 7).nullable()
    val isCollapsed = bool("is_collapsed").default(false)
}


object Steps : ULongIdTable("steps") {
    val workflow = reference("workflow_id", Workflows)
    val parentGrouper = reference("parent_grouper_id", Groupers).nullable()
    val tool = reference("tool_id", Tools)

    /**
     * The step that follows this one when nothing has redirected. Null means terminal.
     *
     * A composite FK against `uq_step_workflow` keeps the target inside the same workflow -
     * the only integrity the separate `step_default_routes` table was providing, for a property
     * that is relevant every time a step is considered at all.
     */
    val defaultNextStep = reference("default_next_step_id", Steps).nullable()
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
    val isWorkflowHead = bool("is_workflow_head").default(false)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
    /**
     * Canvas position. `steps_chk_pos` in the database bounds both axes to +/- 1,000,000; see
     * `Schema.Canvas` for why the plane stops there rather than anywhere larger.
     */
    val posX = double("pos_x").default(0.0)
    val posY = double("pos_y").default(0.0)
    /** `#RRGGBB`, enforced by `steps_chk_color` in the database and by `HexColorFormat` in the DTO. */
    val color = varchar("color", 7).nullable()

    init {
        uniqueIndex("uq_step_workflow", workflow, id)
    }
}


object DataPoints : ULongIdTable("data_points") {
    val workflow = reference("workflow_id", Workflows)
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()

    /** Marks a data point that must be supplied as a run input rather than produced by a step. */
    val isPrerequisite = bool("is_prerequisite").nullable()
    val presetValue = text("preset_value").nullable()
    val storeMethod = dbEnum<StoreMethod>("store_method", 8).nullable()
    val typeNode = reference("type_node_id", TypeNodes)
    val createdAt = timestamp("createdAt").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updatedAt").defaultExpression(CurrentTimestamp)
}


object Metadatas : ULongIdTable("metadata") {
    val workflow = reference("workflow_id", Workflows).uniqueIndex("uq_metadata_workflow")
    val name = varchar("name", 255)
    val version = varchar("version", 20).default("1.0.0")
    val copyrightInformation = text("copyright_information").nullable()
    val contactInformation = text("contact_information").nullable()
    val thirdPartyLicenses = text("third_party_licenses").nullable()
    val discretionAdvisedContent = bool("discretion_advised_content").default(false)
    val commercialUsage = dbEnum<CommercialUse>("commercial_usage", 32)
        .default(CommercialUse.PERSONAL_NON_PROFIT)
    val creditNotation = dbEnum<AuthorshipCredit>("credit_notation", 16)
        .default(AuthorshipCredit.REQUIRED)
    val allowRedistribution = bool("allow_redistribution").default(false)
    val modification = dbEnum<ModificationTerms>("modification", 40)
        .default(ModificationTerms.PROHIBITED)
    val distributionVersion = varchar("distribution_version", 255).default("1.0.0")
    val distributionAcceptedAt = datetime("distribution_accepted_at")
    val distributionUpdatedAt = datetime("distribution_updated_at")
}


object MetadataStepExclusions : OwleCompositeIdTable("metadata_step_exclusions") {
    val metadata = keyReference("metadata_id", Metadatas, onDelete = ReferenceOption.CASCADE)
    val excludedStep = keyReference("excluded_step_id", Steps, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(metadata, excludedStep)
}


object WorkflowRequirements : OwleCompositeIdTable("workflow_requirements") {
    val workflow = keyReference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val requiredDist = keyReference("required_dist", Distributions)

    /**
     * Null means "no version was pinned" - install the distribution as-is (`pip install numpy`).
     * Not a reference to `versions`: a release is identified by (distribution, version) together,
     * which is what the compound foreign key below enforces.
     */
    val requiredPackageVersion = ulong("required_package_id").nullable()
    val requiredExtraName = varchar("required_extra_name", 255).nullable()
    override val primaryKey = PrimaryKey(workflow, requiredDist)

    init {
        foreignKey(
            requiredDist to DistributionPackages.distribution,
            requiredPackageVersion to DistributionPackages.version,
            name = "workflow_requirements_ibfk_2"
        )
    }
}


/**
 * A root workflow plus the closure of workflows reachable from it through junctions.
 *
 * Junction reachability can't belong to a single reusable Workflow, so it is named here instead.
 * Every member shares one container and one environment, which is what makes an intra-Multiflow
 * junction an in-process call rather than a new sandbox.
 */
object Multiflows : ULongIdTable("multiflows") {
    val user = reference("user_id", Users).nullable()
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
    val rootWorkflow = reference("root_workflow_id", Workflows)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
}


object MultiflowWorkflows : OwleCompositeIdTable("multiflow_workflows") {
    val multiflow = keyReference("multiflow_id", Multiflows, onDelete = ReferenceOption.CASCADE)
    val workflow = keyReference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val posX = double("pos_x").default(0.0)
    val posY = double("pos_y").default(0.0)
    val color = varchar("color", 7).nullable()
    val isCollapsed = bool("is_collapsed").default(false)
    override val primaryKey = PrimaryKey(multiflow, workflow)
}
