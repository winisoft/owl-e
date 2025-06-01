package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.typing.TypeVarBoundEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column

object TypeVarBoundDaoDelegate: CompositeIdDaoDelegate<
        TypeVarBound,
        TypeVarBoundEntity
        >(TypeVarBoundEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: TypeVarBound): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}