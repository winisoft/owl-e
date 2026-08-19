package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.typing.TypeNodeRelation
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.typing.TypeNodeRelationEntity
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


object TypeNodeRelationDaoDelegate: CompositeIdDaoDelegate<
        TypeNodeRelation,
        TypeNodeRelationEntity,
        >(TypeNodeRelationEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: TypeNodeRelation): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}