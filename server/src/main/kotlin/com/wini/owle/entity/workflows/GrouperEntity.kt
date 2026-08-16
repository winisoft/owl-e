package com.wini.owle.entity.workflows

import com.wini.owle.entity.tags.GrouperTags
import com.wini.owle.entity.tags.TagEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * A nesting region on a workflow's canvas.
 *
 * Groupers are presentational: they position and collapse a set of steps and nested groupers.
 * They impose no ordering and take no part in execution.
 */
class GrouperEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<GrouperEntity>(Groupers)

    /**
     * Workflow containing this grouper.
     */
    var workflow by WorkflowEntity referencedOn Groupers.workflow

    /**
     * Enclosing grouper. Null means it sits at the workflow level.
     */
    var parentGrouper by GrouperEntity optionalReferencedOn Groupers.parentGrouper

    var name by Groupers.name

    var description by Groupers.description

    var posX by Groupers.posX

    var posY by Groupers.posY

    var color by Groupers.color

    var isCollapsed by Groupers.isCollapsed

    /**
     * Groupers nested directly inside this one.
     */
    val childGroupers: SizedIterable<GrouperEntity> by GrouperEntity optionalReferrersOn Groupers.parentGrouper

    /**
     * Steps displayed directly inside this grouper.
     */
    val steps: SizedIterable<StepEntity> by StepEntity optionalReferrersOn Steps.parentGrouper

    val tags: SizedIterable<TagEntity> by TagEntity.via(GrouperTags.grouper, GrouperTags.tag)
}
