package com.wini.owle.mapper.tags.targets

import com.wini.owle.dto.tags.targets.WorkflowTag
import com.wini.owle.entity.tags.Tags
import com.wini.owle.entity.tags.WorkflowTags
import com.wini.owle.entity.tags.targets.WorkflowTagEntity
import com.wini.owle.entity.workflows.Workflows
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class WorkflowTagsMapper : DbMapper<CompositeID, WorkflowTag, WorkflowTagEntity> {

    // No properties to apply for this simple relation entity beyond the ID
    val applyProps: WorkflowTagEntity.(WorkflowTag) -> Unit = {}

    override fun toEntity(dto: WorkflowTag): WorkflowTagEntity {
        return WorkflowTagEntity.new(
            id = CompositeID {
                it[WorkflowTags.workflow] = EntityID(dto.subjectId, Workflows)
                it[WorkflowTags.tag] = EntityID(dto.tagId, Tags)
            }
        ) {}
    }

    override fun toDto(entity: WorkflowTagEntity): WorkflowTag {
        return WorkflowTag(
            subjectId = entity.workflow.id.value,
            tagId = entity.tagEntity.id.value
        )
    }
}