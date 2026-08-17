package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.entity.transforms.TransformTargetTypes
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column

object TransformTargetTypeDaoDelegate: CompositeIdDaoDelegate<
        TransformTargetType,
        TransformTargetTypeEntity
        >(TransformTargetTypeEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {
            it[TransformTargetTypes.transform] = (params["transformationId"] as Transform).id
            it[TransformTargetTypes.eligibleTypeNode] = (params["typeDeclarationId"] as TypeNode).id
        }
    }

    override fun extractId(dto: TransformTargetType): CompositeID {
        return CompositeID.Companion {
            it[TransformTargetTypes.transform] = dto.transform.id
            it[TransformTargetTypes.eligibleTypeNode] = dto.eligibleTypeNode.id
        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(
        "transformationId" to TransformTargetTypes.transform,
        "typeDeclarationId" to TransformTargetTypes.eligibleTypeNode
    )
}