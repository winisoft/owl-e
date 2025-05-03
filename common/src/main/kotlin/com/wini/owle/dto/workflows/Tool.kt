package com.wini.owle.dto.workflows

import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.dto.workflows.Step.Companion.MAX_NAME_LENGTH
import kotlinx.serialization.Serializable

/**
 * Represents a tool that can be utilized within a step.
 * Contains the tool's body, optional description, return type, and dependencies.
 */
@OwleModel
@Serializable
data class Tool(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Name of the tool.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    /**
     * Optional description of the tool.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_DESC_LENGTH)
    val description: String? = null,

    /**
     * The main body or code of the tool.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_BODY_LENGTH)
    val body: String,

    /**
     * The return type of the tool, if defined.
     */
    val returnTypeDeclaration: TypeDeclaration?

) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 512
        const val MAX_DESC_LENGTH = 1024
        const val MAX_BODY_LENGTH = 64_000
    }
}