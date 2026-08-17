package com.wini.owle.dto.workflows

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.schema.ModelDefinition


@ModelDefinition
data class WorkflowRequirement(

    val workflow: Workflow,

    val requiredDist: Distribution,

    val requiredPackageVersion: Version?,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.WorkflowRequirements.REQUIRED_EXTRA_NAME_MAX_LENGTH)
    val requiredExtraName: String?

): CompositeDto {

    override val owleId: String
        get() = ""
}
