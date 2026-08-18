package com.wini.owle.entity.tags

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
    val tagSource = enumerationByName("source", 8, TagSource::class).nullable()
    val user = reference("user_id", Users).nullable()
}

object TagRelations : CompositeIdTable("tag_relations") {
    val parentTag = reference("parent_tag_id", Tags, onDelete = ReferenceOption.CASCADE)
    val childTag = reference("child_tag_id", Tags, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(parentTag, childTag)
}

object DistributionTags : CompositeIdTable("tagged_distributions") {
    val distribution = reference("distribution", Distributions, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(distribution, tag)
}

object GrouperTags : CompositeIdTable("tagged_groupers") {
    val grouper = reference("grouper_id", Groupers)
    val tag = reference("tag_id", Tags)
    override val primaryKey = PrimaryKey(grouper, tag)
}

object PackageTags : CompositeIdTable("tagged_packages") {
    val distribution = reference("distribution", Distributions)

    /** Version half of the package key; the pair below is the constraint the database has. */
    val packageVersion = ulong("package_id").entityId()
    val tag = reference("tag_id", Tags)
    override val primaryKey = PrimaryKey(distribution, packageVersion, tag)

    init {
        foreignKey(
            distribution to DistributionPackages.distribution,
            packageVersion to DistributionPackages.version,
            name = "tagged_packages_ibfk_1"
        )
    }
}

object StepTags : CompositeIdTable("tagged_steps") {
    val step = reference("step_id", Steps)
    val tag = reference("tag_id", Tags)
    override val primaryKey = PrimaryKey(step, tag)
}

object ToolTags : CompositeIdTable("tagged_tools") {
    val tool = reference("tool_id", Tools)
    val tag = reference("tag_id", Tags)
    override val primaryKey = PrimaryKey(tool, tag)
}

object TransformTags : CompositeIdTable("tagged_transforms") {
    val transform = reference("transform_id", Transforms)
    val tag = reference("tag_id", Tags)
    override val primaryKey = PrimaryKey(transform, tag)
}

object WorkflowFolderTags : CompositeIdTable("tagged_workflow_folders") {
    val workflowFolder = reference("workflow_folder_id", WorkflowFolders)
    val tag = reference("tag_id", Tags)
    override val primaryKey = PrimaryKey(workflowFolder, tag)
}

object WorkflowTags : CompositeIdTable("tagged_workflows") {
    val workflow = reference("workflow_id", Workflows)
    val tag = reference("tag_id", Tags)
    override val primaryKey = PrimaryKey(workflow, tag)
}
