package com.wini.owle.entity.tags

import com.wini.owle.dto.tags.enums.TagSource
import com.wini.owle.entity.environs.pkg.Distributions
import com.wini.owle.entity.environs.Versions
import com.wini.owle.entity.steps.Tools
import com.wini.owle.entity.transforms.Transforms
import com.wini.owle.entity.typing.TypeNodes
import com.wini.owle.entity.user.Users
import com.wini.owle.entity.workflows.Phases
import com.wini.owle.entity.workflows.Steps
import com.wini.owle.entity.workflows.WorkflowFolders
import com.wini.owle.entity.workflows.Workflows
import com.wini.owle.schema.Schema
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable

object Tags : ULongIdTable("tags") {
    val name = varchar("name", Schema.SizeLimits.LEN_255)
    val description = varchar("description", Schema.SizeLimits.LEN_1024).nullable()
    val tagSource = enumerationByName("source", 6, TagSource::class)
    val user = reference("user_id", Users, onDelete = ReferenceOption.CASCADE).nullable()
}


object TagRelations : CompositeIdTable("tag_relations") {
    val parentTag = reference("parent_tag_id", Tags, onDelete = ReferenceOption.CASCADE)
    val childTag = reference("child_tag_id", Tags, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(parentTag, childTag)
}

object WorkflowTags: CompositeIdTable("tagged_workflows") {
    val workflow = reference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
}


object WorkflowFolderTags: CompositeIdTable("tagged_workflow_folders") {
    val workflowFolder = reference("workflow_folder_id", WorkflowFolders, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
}


object StepTags: CompositeIdTable("tagged_steps") {
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
}


object PhaseTags: CompositeIdTable("tagged_phases") {
    val phase = reference("phase_id", Phases, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
}


object ToolTags: CompositeIdTable("tagged_tools") {
    val tool = reference("tool_id", Tools, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
}


object TransformTags: CompositeIdTable("tagged_transforms") {
    val transform = reference("transformId", Transforms, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
}

object TypeNodeTags: CompositeIdTable("tagged_type_nodes") {
    val typeNode = reference("type_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
}

object DistributionTags: CompositeIdTable("tagged_distributions") {
    val distribution = reference("distribution", Distributions, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
}


object PackageTags: CompositeIdTable("tagged_packages") {
    val distribution = reference("distribution", Distributions)
    val version = reference("package_id", Versions, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
}
