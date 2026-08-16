package com.wini.owle.entity.workflows

import com.wini.owle.dto.workflows.enums.AuthorshipCredit
import com.wini.owle.dto.workflows.enums.CommercialUse
import com.wini.owle.dto.workflows.enums.ModificationTerms
import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.entity.environs.Distributions
import com.wini.owle.entity.environs.PythonVersions
import com.wini.owle.entity.environs.Versions
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
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp).nullable()
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp).nullable()
}


object WorkflowFolders : ULongIdTable("workflow_folders") {
    val user = reference("user_id", Users)
    val parentFolder = reference("parent_folder_id", WorkflowFolders).nullable()
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp).nullable()
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp).nullable()
}


object WorkflowFolderRelations : CompositeIdTable("workflow_folder_relations") {
    val parent = reference("parent_id", WorkflowFolders)
    val child = reference("child_id", WorkflowFolders)
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
    val posX = double("pos_x").default(0.0)
    val posY = double("pos_y").default(0.0)
    val color = varchar("color", 7).nullable()
    val isCollapsed = bool("is_collapsed").default(false)
}


object Steps : ULongIdTable("steps") {
    val workflow = reference("workflow_id", Workflows)
    val parentGrouper = reference("parent_grouper_id", Groupers).nullable()
    val tool = reference("tool_id", Tools)
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
    val isWorkflowHead = bool("is_workflow_head").default(false)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp).nullable()
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp).nullable()
    val posX = double("pos_x").default(0.0)
    val posY = double("pos_y").default(0.0)
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
    val storeMethod = enumerationByName("store_method", 8, StoreMethod::class).nullable()
    val typeNode = reference("type_node_id", TypeNodes)
    val createdAt = timestamp("createdAt").defaultExpression(CurrentTimestamp).nullable()
    val updatedAt = timestamp("updatedAt").defaultExpression(CurrentTimestamp).nullable()
}


object Metadatas : ULongIdTable("metadata") {
    val workflow = reference("workflow_id", Workflows).uniqueIndex("uq_metadata_workflow")
    val name = varchar("name", 255)
    val version = varchar("version", 20).default("1.0.0")
    val copyrightInformation = text("copyright_information").nullable()
    val contactInformation = text("contact_information").nullable()
    val thirdPartyLicenses = text("third_party_licenses").nullable()
    val discretionAdvisedContent = bool("discretion_advised_content").default(false)
    val commercialUsage = enumerationByName("commercial_usage", 32, CommercialUse::class)
        .default(CommercialUse.PERSONAL_NON_PROFIT)
    val creditNotation = enumerationByName("credit_notation", 16, AuthorshipCredit::class)
        .default(AuthorshipCredit.REQUIRED)
    val allowRedistribution = bool("allow_redistribution").default(false)
    val modification = enumerationByName("modification", 40, ModificationTerms::class)
        .default(ModificationTerms.PROHIBITED)
    val distributionVersion = varchar("distribution_version", 255).default("1.0.0")
    val distributionAcceptedAt = datetime("distribution_accepted_at")
    val distributionUpdatedAt = datetime("distribution_updated_at")
}


object MetadataStepExclusions : CompositeIdTable("metadata_step_exclusions") {
    val metadata = reference("metadata_id", Metadatas, onDelete = ReferenceOption.CASCADE)
    val excludedStep = reference("excluded_step_id", Steps, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(metadata, excludedStep)
}


object WorkflowRequirements : CompositeIdTable("workflow_requirements") {
    val workflow = reference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val requiredDist = reference("required_dist", Distributions)
    val requiredPackageVersion = reference("required_package_id", Versions).nullable()
    val requiredExtraName = varchar("required_extra_name", 255).nullable()
    override val primaryKey = PrimaryKey(workflow, requiredDist)
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
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp).nullable()
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp).nullable()
}


object MultiflowWorkflows : CompositeIdTable("multiflow_workflows") {
    val multiflow = reference("multiflow_id", Multiflows, onDelete = ReferenceOption.CASCADE)
    val workflow = reference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val posX = double("pos_x").default(0.0)
    val posY = double("pos_y").default(0.0)
    val color = varchar("color", 7).nullable()
    val isCollapsed = bool("is_collapsed").default(false)
    override val primaryKey = PrimaryKey(multiflow, workflow)
}
