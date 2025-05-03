package com.wini.owle.dto.user.tags

import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Workflow.Companion.MAX_NAME_LENGTH
import kotlinx.serialization.Serializable

@OwleModel
@Serializable
data class Tag(

    @LongValueMin(0)
    override val id: Long,

    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val description: String? = null,

    val user: User

) : StandardDto