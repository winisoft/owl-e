package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.typing.CallableOwner
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.typing.CallableOwnerEntity
import com.wini.owle.entity.typing.CallableOwners.callableNode
import com.wini.owle.entity.typing.CallableOwners.ownerNode
import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID

object CallableOwnerDaoDelegate: CompositeIdDaoDelegate<
        CallableOwner,
        CallableOwnerEntity,
    >(CallableOwnerEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {
            it[ownerNode] = TypeNodeEntity[params["owner_node_id"] as ULong].id
            it[callableNode] = TypeNodeEntity[params["callable_node_id"] as ULong].id
        }
    }

    override fun extractId(dto: CallableOwner): CompositeID {
        return CompositeID.Companion {
            it[ownerNode] = TypeNodeEntity[dto.ownerNode.id].id
            it[callableNode] = TypeNodeEntity[dto.callableNode.id].id
        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}
