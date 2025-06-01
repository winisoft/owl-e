package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class CallableSignatureEntity(id: EntityID<ULong>): ULongEntity(id) {

    companion object : ULongEntityClass<CallableSignatureEntity>(CallableSignatures)

    var parentSchemaRef by TypeSchemaRefEntity referencedOn CallableSignatures.parentSchemaRef

    var name by CallableSignatures.name

    var returnTypeNode by TypeSchemaRefEntity referencedOn CallableSignatures.returnTypeNode

    var docstring by CallableSignatures.docString

    var isStatic  by CallableSignatures.isStatic

    var isClassmethod by CallableSignatures.isClassMethod


}