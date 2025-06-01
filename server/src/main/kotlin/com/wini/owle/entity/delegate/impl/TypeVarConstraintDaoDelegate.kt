package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.typing.TypeVarConstraint
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.typing.TypeVarConstraintEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column

object TypeVarConstraintDaoDelegate: CompositeIdDaoDelegate<
        TypeVarConstraint,
        TypeVarConstraintEntity
        >(TypeVarConstraintEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: TypeVarConstraint): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}