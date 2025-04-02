package com.wini.owle.mapper.user


//class WorkflowTagsMapper : DbMapper<WorkflowTagDto, WorkflowTagDao>() {
//
//    // No properties to apply for this simple relation entity beyond the ID
//    val applyProps: WorkflowTagDao.(WorkflowTagDto) -> Unit = {}
//
//    override fun toEntity(dto: WorkflowTagDto): WorkflowTagDao {
//        val compositeId = CompositeID {
//            it[WorkflowTags.workflow] = EntityID(dto.workflowId, Workflows)
//            it[WorkflowTags.tag] = EntityID(dto.tagId, Tags)
//        }
//        // Find existing or create new might be more appropriate,
//        // but sticking to creation based on original code.
//        // WorkflowTagDao.findById(compositeId) ?: WorkflowTagDao.new(compositeId) {} might be an alternative
//        return WorkflowTagDao.new(EntityID(compositeId, WorkflowTags)) {}
//    }
//
//    override fun toDto(entity: WorkflowTagDao): WorkflowTagDto {
//        return WorkflowTagDto(
//            workflowId = entity.workflow.id.value,
//            tagId = entity.tag.id.value
//        )
//    }
//}