package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.typing.ClassInheritance
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.typing.TypeNodeRelationEntity
import com.wini.owle.entity.typing.TypeNodeRelations.childNode
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column
//
//object ClassInheritanceDaoDelegate: CompositeIdDaoDelegate<
//        ClassInheritance,
//        ClassInheritanceEntity
//        >(TypeNodeRelationEntity.Companion) {
//
//    override fun buildId(params: Map<String, Any?>): CompositeID {
//        return CompositeID.Companion {
//            it[childNode] = TypeNodeEntity[params["child"]]
//            it[parentNode]
//        }
//    }
//
//    override fun extractId(dto: ClassInheritance): CompositeID {
//        return CompositeID.Companion {
//
//        }
//    }
//
//    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(
//
//    )
//
}