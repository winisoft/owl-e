package com.wini.owle.entity.workflows

import com.wini.owle.entity.environs.pkg.DistributionEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * A distribution a workflow's environment must provide. A null [requiredPackageVersion] means
 * any version will do.
 */
class WorkflowRequirementEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<WorkflowRequirementEntity>(WorkflowRequirements)

    var workflow by WorkflowEntity referencedOn WorkflowRequirements.workflow

    var requiredDist by DistributionEntity referencedOn WorkflowRequirements.requiredDist

    var requiredPackageVersion by WorkflowRequirements.requiredPackageVersion

    var requiredExtraName: String? by WorkflowRequirements.requiredExtraName
}
