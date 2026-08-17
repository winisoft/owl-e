package com.wini.owle.dto.workflows

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax


data class WorkflowRequirement(

    val workflowId: ULong,

    val requiredDist: Distribution,

    val requiredPackage: DistributionPackage,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.WorkflowRequirements.REQUIRED_EXTRA_NAME_MAX_LENGTH)
    val requiredExtraName: String

): CompositeDto {

    override val owleId: String
        get() = ""
}
