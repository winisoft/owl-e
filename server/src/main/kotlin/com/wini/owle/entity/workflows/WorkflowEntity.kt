package com.wini.owle.entity.workflows

import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.entity.steps.WorkflowJunctions
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.WorkflowTags
import com.wini.owle.entity.user.UserEntity
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * A reusable unit of authored activity: steps over data points, in one Python environment.
 *
 * A workflow knows nothing about what lies beyond its junctions — the reachable closure is a
 * [MultiflowEntity] concern.
 */
open class WorkflowEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<WorkflowEntity>(Workflows)

    var name by Workflows.name

    var user by UserEntity optionalReferencedOn Workflows.user

    var description by Workflows.description

    /**
     * The interpreter this workflow's environment is built on.
     */
    var pythonVersion by PythonVersionEntity referencedOn Workflows.pythonVersion

    var parentFolder by WorkflowFolderEntity optionalReferencedOn Workflows.parentFolder

    var createdAt by Workflows.createdAt

    var updatedAt by Workflows.updatedAt

    // --- contents -------------------------------------------------------------------------

    val steps: SizedIterable<StepEntity> by StepEntity referrersOn Steps.workflow

    /**
     * The entry step. More than one is a configuration error, not a supported shape.
     */
    val headStep: StepEntity?
        get() = StepEntity.find { (Steps.workflow eq id) and (Steps.isWorkflowHead eq true) }.firstOrNull()

    val dataPoints: SizedIterable<DataPointEntity> by DataPointEntity referrersOn DataPoints.workflow

    /**
     * Data points that must be supplied before the run starts.
     */
    val prerequisiteDataPoints: SizedIterable<DataPointEntity>
        get() = DataPointEntity.find { (DataPoints.workflow eq id) and (DataPoints.isPrerequisite eq true) }

    val groupers: SizedIterable<GrouperEntity> by GrouperEntity referrersOn Groupers.workflow

    /**
     * Distribution requirements pinned for this workflow's environment.
     */
    val requirements: SizedIterable<WorkflowRequirementEntity>
            by WorkflowRequirementEntity referrersOn WorkflowRequirements.workflow

    /**
     * Sharing and licensing terms. At most one row per workflow.
     */
    val metadata: WorkflowMetadataEntity? by WorkflowMetadataEntity optionalBackReferencedOn Metadatas.workflow

    // --- graph edges ----------------------------------------------------------------------

    /**
     * Junctions leaving this workflow, by way of one of its steps.
     */
    val outboundJunctions: SizedIterable<WorkflowJunctionEntity>
        get() = WorkflowJunctionEntity.find { WorkflowJunctions.sourceStep inList steps.map { it.id } }

    /**
     * Junctions from elsewhere that land on this workflow.
     */
    val inboundJunctions: SizedIterable<WorkflowJunctionEntity>
            by WorkflowJunctionEntity referrersOn WorkflowJunctions.targetWorkflow

    /**
     * Multiflows this workflow is a member of.
     */
    val multiflows: SizedIterable<MultiflowEntity>
            by MultiflowEntity.via(MultiflowWorkflows.workflow, MultiflowWorkflows.multiflow)

    val tags: SizedIterable<TagEntity> by TagEntity.via(WorkflowTags.workflow, WorkflowTags.tag)
}
