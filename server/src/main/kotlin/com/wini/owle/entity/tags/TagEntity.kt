package com.wini.owle.entity.tags

import com.wini.owle.entity.environs.pkg.DistributionEntity
import com.wini.owle.entity.steps.ToolEntity
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.GrouperEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * A label applicable to most things a user can author. Tags come from three places — Owl-E's own
 * base categories, PyPI classifiers, and users — which [source] distinguishes; [user] is set only
 * for the third.
 */
class TagEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TagEntity>(Tags)

    /**
     * Unique name of the tag.
     */
    var name by Tags.name

    /**
     * Optional description of the tag.
     */
    var description by Tags.description

    var source by Tags.tagSource

    /**
     * Author, for user-created tags.
     */
    var user by UserEntity optionalReferencedOn Tags.user

    /**
     * Tags this tag sits above in a custom tag hierarchy
     */
    val childTags: SizedIterable<TagEntity> by TagEntity.via(TagRelations.parentTag, TagRelations.childTag)

    /**
     * Tags this tag sits beneath in a custom tag hierarchy
     */
    val parentTags: SizedIterable<TagEntity> by TagEntity.via(TagRelations.childTag, TagRelations.parentTag)

    // --- application sites --------------------------------------------------------------------

    val distributionsAssociated: SizedIterable<DistributionEntity>
            by DistributionEntity.via(DistributionTags.tag, DistributionTags.distribution)

    val groupersAssociated: SizedIterable<GrouperEntity>
            by GrouperEntity.via(GrouperTags.tag, GrouperTags.grouper)

    val stepsAssociated: SizedIterable<StepEntity>
            by StepEntity.via(StepTags.tag, StepTags.step)

    val toolsAssociated: SizedIterable<ToolEntity>
            by ToolEntity.via(ToolTags.tag, ToolTags.tool)

    val transformsAssociated: SizedIterable<TransformEntity>
            by TransformEntity.via(TransformTags.tag, TransformTags.transform)

    val workflowFoldersAssociated: SizedIterable<WorkflowFolderEntity>
            by WorkflowFolderEntity.via(WorkflowFolderTags.tag, WorkflowFolderTags.workflowFolder)

    val workflowsAssociated: SizedIterable<WorkflowEntity>
            by WorkflowEntity.via(WorkflowTags.tag, WorkflowTags.workflow)
}
