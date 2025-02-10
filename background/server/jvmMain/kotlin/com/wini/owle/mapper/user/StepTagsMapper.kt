package com.wini.owle.mapper.user

import com.wini.owle.dto.StepTagDto
import com.wini.owle.entity.StepTags
import com.wini.owle.entity.Steps
import com.wini.owle.entity.Tags
import com.wini.owle.entity.user.StepTagDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID


class StepTagsMapper : DbMapper<StepTagDto, StepTagDao>() {

    override val applyProps: StepTagDao.(StepTagDto) -> Unit = {}

    override fun StepTagDto.toEntity(): StepTagDao {
        val compositeId = CompositeID {
            it[StepTags.step] = EntityID(stepId, Steps)
            it[StepTags.tag] = EntityID(tagId, Tags)
        }
        return StepTagDao(EntityID(compositeId, StepTags))
    }

    override fun StepTagDao.toDto(): StepTagDto {
        return StepTagDto(
            stepId = step.id.value,
            tagId = tag.id.value
        )
    }
}