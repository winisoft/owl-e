package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.entity.typing.TypeUnionMembers
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column

object TypeUnionMemberDaoDelegate: CompositeIdDaoDelegate<
        TypeUnionMember,
        TypeUnionMemberEntity
        >(TypeUnionMemberEntity.Companion) {

    fun buildId(unionType: TypeNodeEntity, memberType: TypeNodeEntity): CompositeID {
        return CompositeID {
            it[TypeUnionMembers.unionNode] = unionType.id
            it[TypeUnionMembers.memberNode] = memberType.id
        }
    }

    fun buildId(unionTypeId: ULong, memberTypeId: ULong): CompositeID {
        return CompositeID {
            it[TypeUnionMembers.unionNode] = unionTypeId
            it[TypeUnionMembers.memberNode] = memberTypeId
        }
    }

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: TypeUnionMember): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}