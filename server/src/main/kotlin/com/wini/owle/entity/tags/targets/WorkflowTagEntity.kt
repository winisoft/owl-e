package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.WorkflowTags
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Represents a tag a given user has associated with a workflow
 */
class WorkflowTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<WorkflowTagEntity>(WorkflowTags)

    /**
     * The workflow for which the tag is applied
     */
    var workflow by WorkflowEntity referencedOn WorkflowTags.workflow

    /**
     * The tag associated with the workflow
     */
    var tag by TagEntity referencedOn WorkflowTags.tag
}
