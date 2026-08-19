package com.wini.owle.entity.delegate.impl
//
//import com.wini.owle.dto.workflows.WorkflowRequirement
//import com.wini.owle.entity.delegate.DaoDelegate
//import com.wini.owle.entity.workflows.WorkflowRequirementEntity
//import org.jetbrains.exposed.v1.core.Column
//import org.jetbrains.exposed.v1.core.dao.id.CompositeID
//import org.jetbrains.exposed.v1.core.dao.id.EntityID
//
//
//object WorkflowRequirementDaoDelegate: DaoDelegate<CompositeID, WorkflowRequirement, WorkflowRequirementEntity> {
//    override val columnsByRequestParam: Map<String, Column<out Any>>
//        get() = TODO("Not yet implemented")
//
//    override fun extractId(dto: WorkflowRequirement): CompositeID {
//        TODO("Not yet implemented")
//    }
//
//    override fun buildEntityId(id: CompositeID): EntityID<CompositeID> {
//        TODO("Not yet implemented")
//    }
//
//    override fun findOrCreate(
//        dto: WorkflowRequirement,
//        applyProps: WorkflowRequirementEntity.() -> Unit
//    ): WorkflowRequirementEntity {
//        TODO("Not yet implemented")
//    }
//
//    override fun buildId(dto: WorkflowRequirement): EntityID<CompositeID> {
//        TODO("Not yet implemented")
//    }
//
//
//}