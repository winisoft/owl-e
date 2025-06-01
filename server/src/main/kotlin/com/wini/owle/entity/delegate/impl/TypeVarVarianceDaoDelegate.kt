package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.typing.TypeVarVarianceEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column

object TypeVarVarianceDaoDelegate: CompositeIdDaoDelegate<
        TypeVarVariance,
        TypeVarVarianceEntity
        >(TypeVarVarianceEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: TypeVarVariance): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}