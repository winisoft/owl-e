package com.wini.owle.mapper.user

import com.wini.owle.dto.WorkflowTagDto
import com.wini.owle.entity.Tags
import com.wini.owle.entity.WorkflowTags
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.user.WorkflowTagDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID


class WorkflowTagsMapper : DbMapper<WorkflowTagDto, WorkflowTagDao>() {

    override val applyProps: WorkflowTagDao.(WorkflowTagDto) -> Unit = {}

    override fun WorkflowTagDto.toEntity(): WorkflowTagDao {
        val compositeId = CompositeID {
            it[WorkflowTags.workflow] = EntityID(workflowId, Workflows)
            it[WorkflowTags.tag] = EntityID(tagId, Tags)
        }
        return WorkflowTagDao(EntityID(compositeId, WorkflowTags))
    }

    override fun WorkflowTagDao.toDto(): WorkflowTagDto {
        return WorkflowTagDto(
            workflowId = workflow.id.value,
            tagId = tag.id.value
        )
    }
}