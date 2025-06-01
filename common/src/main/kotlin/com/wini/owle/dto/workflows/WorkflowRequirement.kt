package com.wini.owle.dto.workflows

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage


data class WorkflowRequirement(

    val workflowId: ULong,

    val requiredDist: Distribution,

    val requiredPackage: DistributionPackage,

    val requiredExtraName: String

): CompositeDto {

    override val owleId: String
        get() = ""
}
