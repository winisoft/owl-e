package com.wini.owle.dto.workflows

import com.wini.owle.dto.CompositeDto

data class PhaseRelation(
    val parent: Phase,
    val child: Phase
): CompositeDto {

    override val owleId: String
        get() = TODO("Not yet implemented")

    override val owleTypedId: String
        get() = super.owleTypedId

}