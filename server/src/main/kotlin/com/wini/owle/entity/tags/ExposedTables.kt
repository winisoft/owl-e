package com.wini.owle.entity.tags

import com.wini.owle.entity.OwleCompositeIdTable

import com.wini.owle.entity.dbEnum

import com.wini.owle.dto.tags.enums.TagSource
import com.wini.owle.entity.environs.Distributions
import com.wini.owle.entity.environs.DistributionPackages
import com.wini.owle.entity.steps.Tools
import com.wini.owle.entity.transforms.Transforms
import com.wini.owle.entity.user.Users
import com.wini.owle.entity.workflows.Groupers
import com.wini.owle.entity.workflows.Steps
import com.wini.owle.entity.workflows.WorkflowFolders
import com.wini.owle.entity.workflows.Workflows
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable


object Tags : ULongIdTable("tags") {
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
    val tagSource = dbEnum<TagSource>("source", 8).nullable()
    val user = reference("user_id", Users).nullable()
}

object TagRelations : OwleCompositeIdTable("tag_relations") {
    val parentTag = keyReference("parent_tag_id", Tags, onDelete = ReferenceOption.CASCADE)
    val childTag = keyReference("child_tag_id", Tags, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(parentTag, childTag)
}

object DistributionTags : OwleCompositeIdTable("tagged_distributions") {
    val distribution = keyReference("distribution", Distributions, onDelete = ReferenceOption.CASCADE)
    val tag = keyReference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(distribution, tag)
}

object GrouperTags : OwleCompositeIdTable("tagged_groupers") {
    val grouper = keyReference("grouper_id", Groupers)
    val tag = keyReference("tag_id", Tags)
    override val primaryKey = PrimaryKey(grouper, tag)
}

object PackageTags : OwleCompositeIdTable("tagged_packages") {
    val distribution = keyReference("distribution", Distributions)

    /** Version half of the package key; the pair below is the constraint the database has. */
    val packageVersion = ulong("package_id").entityId()
    val tag = keyReference("tag_id", Tags)
    override val primaryKey = PrimaryKey(distribution, packageVersion, tag)

    init {
        foreignKey(
            distribution to DistributionPackages.distribution,
            packageVersion to DistributionPackages.version,
            name = "tagged_packages_ibfk_1"
        )
    }
}

object StepTags : OwleCompositeIdTable("tagged_steps") {
    val step = keyReference("step_id", Steps)
    val tag = keyReference("tag_id", Tags)
    override val primaryKey = PrimaryKey(step, tag)
}

object ToolTags : OwleCompositeIdTable("tagged_tools") {
    val tool = keyReference("tool_id", Tools)
    val tag = keyReference("tag_id", Tags)
    override val primaryKey = PrimaryKey(tool, tag)
}

object TransformTags : OwleCompositeIdTable("tagged_transforms") {
    val transform = keyReference("transform_id", Transforms)
    val tag = keyReference("tag_id", Tags)
    override val primaryKey = PrimaryKey(transform, tag)
}

object WorkflowFolderTags : OwleCompositeIdTable("tagged_workflow_folders") {
    val workflowFolder = keyReference("workflow_folder_id", WorkflowFolders)
    val tag = keyReference("tag_id", Tags)
    override val primaryKey = PrimaryKey(workflowFolder, tag)
}

object WorkflowTags : OwleCompositeIdTable("tagged_workflows") {
    val workflow = keyReference("workflow_id", Workflows)
    val tag = keyReference("tag_id", Tags)
    override val primaryKey = PrimaryKey(workflow, tag)
}
