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
import com.wini.owle.schema.Schema
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp


object WorkflowMetadatas : ULongIdTable("workflow_metadata") {
    val workflow = reference("workflow_id", Workflows)
    val name = varchar("name", 255)
    val version = varchar("version", 20).default("1.0.0")
    val copyrightInformation = text("copyright_information").nullable()
    val contactInformation = text("contact_information").nullable()
    val thirdPartyLicenses = text("third_party_licenses").nullable()
    val discretionAdvisedContent = bool("discretion_advised_content").default(false)
    val commercialUsage = enumerationByName("commercial_usage", 32, CommercialUse::class)
    val creditNotation = enumerationByName("credit_notation", 32, AuthorshipCredit::class)
    val allowRedistribution = bool("allow_redistribution").default(false)
    val modification = enumerationByName("modification", 64, ModificationTerms::class)
    val distributionVersion = varchar("distribution_version", 20).default("1.0.0")
    val distributionAcceptedAt = timestamp("distribution_accepted_at")
    val distributionUpdatedAt = timestamp("distribution_updated_at")
}


object WorkflowMetadataContributors : CompositeIdTable("workflow_metadata_contributors") {
    val metadata = reference("metadata_id", WorkflowMetadatas)
    val contributor = reference("contributor_user_id", Users)
    override val primaryKey = PrimaryKey(metadata, contributor)
}

object DataPoints : ULongIdTable("data_points") {
    val workflow = reference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val presetValue = text("preset_value").nullable()
    val storeMethod = enumerationByName("store_method", 10, StoreMethod::class).default(StoreMethod.TEXT)
    val typeDeclaration = reference("type_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val description = text("description").nullable()
    val createdAt = timestamp("createdAt").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updatedAt").defaultExpression(CurrentTimestamp)
}

object Phases : ULongIdTable("phases") {
    val name = varchar("name", 1024)
    val description = varchar("description", 8192).nullable()
    val displayOrder = integer("display_order")
    val parentPhase = reference("parent_phase_id", Phases, onDelete = ReferenceOption.CASCADE).nullable()
    val workflow = reference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
}

object PhaseRelations : CompositeIdTable("phase_relations") {
    val parent = reference("parent_id", Phases)
    val child = reference("child_id", Phases)
    override val primaryKey = PrimaryKey(parent, child)
}

object Steps : ULongIdTable("steps") {
    val workflow = reference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val tool = reference("tool_id", Tools, onDelete = ReferenceOption.CASCADE)
    val parentPhase = reference("parent_phase_id", Phases, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
    val isWorkflowHead = bool("is_workflow_head")
    val displayOrder = integer("display_order")
    val defaultNext = reference("default_next_id", Steps)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
}

object StepDefaultRoutes : CompositeIdTable("step_default_routes") {
    val refStep = reference("step_id", Steps)
    val defaultNext = reference("next_step_id", Steps)
    override val primaryKey = PrimaryKey(refStep, defaultNext)
}

object Workflows : ULongIdTable("workflows") {
    val name = varchar("name", Schema.Workflow.NAME_MAX_LENGTH)
    val user = reference("user", Users)
    val description = text("description").nullable()
    val pythonVersion = reference("python_version_id", PythonVersions)
    val parentFolder = optReference("parent_folder_id", WorkflowFolders)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
}

object Metadatas : ULongIdTable("metadata") {
    val workflow = reference("workflow_id", Workflows, ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val version = varchar("version", 20)
    val copyRightInfo = text("copyright_information")
    val contactInformation = text("contact_information")
    val thirdPartLicenses = text("third_party_licenses")
    val discretionAdvisedContent = bool("discretion_advised_content")
    val commercialUsage = enumerationByName("commercial_usage", 20, CommercialUse::class)
    val creditNotation = enumerationByName("credit_notation", 20, AuthorshipCredit::class)
    val allowRedistribution = bool("allow_redistribution")
    val modification = enumerationByName("modification", 18, ModificationTerms::class)
    val distributionVersion = varchar("distribution_version", 255)
    val distributionAcceptedAt = timestamp("distribution_accepted_at")
    val distributionUpdatedAt = timestamp("distribution_updated_at")
}

object MetadataStepExclusions: CompositeIdTable("metadata_step_exclusions") {
    val metadata = reference("metadata_id", Metadatas)
    val excludedStep = reference("excluded_step_id", Steps)
}


object WorkflowFolders : ULongIdTable("workflow_folders") {
    val name = varchar("name", 255)
    val description = text("description").nullable()
    val parentFolder = reference("parent_folder_id", WorkflowFolders, onDelete = ReferenceOption.CASCADE).nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
}


object WorkflowFolderRelations : CompositeIdTable("workflow_folder_relations") {
    val parent = reference("parent_id", WorkflowFolders)
    val child = reference("child_id", WorkflowFolders)
    override val primaryKey = PrimaryKey(parent, child)
}

object WorkflowRequirements : CompositeIdTable("workflow_requirements") {
    val workflow = reference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val requiredDist = reference("required_dist", Distributions, onDelete = ReferenceOption.CASCADE)
    val requiredPackageVersion = reference("required_package_id", Versions, onDelete = ReferenceOption.CASCADE).nullable()
    val requiredExtra = varchar("required_extra_name", 255).nullable()
    override val primaryKey = PrimaryKey(workflow, requiredDist)
}



