package com.wini.owle.mapper.user

//
//class StepTagsMapper : DbMapper<StepTagDto, StepTagDao>() {
//
//    // No properties to apply for this simple relation entity beyond the ID
//    val applyProps: StepTagDao.(StepTagDto) -> Unit = {}
//
//    override fun toEntity(dto: StepTagDto): StepTagDao {
//        StepTagDao.bui
//        val compositeId = CompositeID {
//            it[StepTags.step] = EntityID(dto.stepId, Steps)
//            it[StepTags.tag] = EntityID(dto.tagId, Tags)
//        }
//        // Find existing or create new might be more appropriate,
//        // but sticking to creation based on original code.
//        // StepTagDao.findById(compositeId) ?: StepTagDao.new(compositeId) {} might be an alternative
//        return StepTagDao.new(EntityID(compositeId, StepTags)) {}
//    }
//
//    override fun toDto(entity: StepTagDao): StepTagDto {
//        return StepTagDto(
//            stepId = entity.step.id.value,
//            tagId = entity.tag.id.value
//        )
//    }
//}