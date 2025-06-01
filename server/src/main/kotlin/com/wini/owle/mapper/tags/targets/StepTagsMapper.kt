package com.wini.owle.mapper.tags.targets

import com.wini.owle.dto.tags.targets.StepTag
import com.wini.owle.entity.tags.StepTags
import com.wini.owle.entity.tags.Tags
import com.wini.owle.entity.tags.targets.StepTagEntity
import com.wini.owle.entity.workflows.Steps
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class StepTagsMapper : DbMapper<CompositeID, StepTag, StepTagEntity> {

    // No properties to apply for this simple relation entity beyond the ID
    val applyProps: StepTagEntity.(StepTag) -> Unit = {}

    override fun toEntity(dto: StepTag): StepTagEntity {

        val compositeId = CompositeID {
            it[StepTags.step] = EntityID(dto.subjectId, Steps)
            it[StepTags.tag] = EntityID(dto.tagId, Tags)
        }
        return StepTagEntity.new(
            id = CompositeID {
                it[StepTags.step] = dto.subjectId
                it[StepTags.tag] = dto.tagId
            }
        ) {

        }
    }

    override fun toDto(entity: StepTagEntity): StepTag {
        return StepTag(
            subjectId = entity.step.id.value,
            tagId = entity.tagEntity.id.value
        )
    }
}