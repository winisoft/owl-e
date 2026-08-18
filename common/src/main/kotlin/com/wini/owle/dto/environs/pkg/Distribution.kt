package com.wini.owle.dto.environs.pkg

import com.wini.owle.dto.StringDto
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

@ModelDefinition
@ApiEndpointDefinition("api.environs.pkg.distribution", ["environs"], "distribution")
@Serializable
data class Distribution(

    /**
     * The name of the Python library package (e.g., 'numpy', 'pandas').
     * Required.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Workflow.NAME_MAX_LENGTH)
    override val name: String,

): StringDto