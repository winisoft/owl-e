package com.wini.owle.dto.tags.targets

import com.wini.owle.dto.tags.TagSubject
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintSimple
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class WorkflowTag(

    @ConstraintSimple([PositiveValueULongError::class])
    override val subjectId: ULong,

    @ConstraintSimple([PositiveValueULongError::class])
    override val tagId: ULong

) : TagSubject {

    override val owleId: String get() = "subjectId=$subjectId,tagId=$tagId"

}