package com.wini.owle.entity.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.typing.TypeUnionMember
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.Column


/**
 * Represents individual member types within a union type declaration.
 * Supports union types such as 'Union[int, str]' by linking each member type to the union type declaration.
 */
class TypeUnionMemberEntity(
    id: EntityID<CompositeID>
) : CompositeEntity(id) {

    companion object : CompositeEntityClass<TypeUnionMemberEntity>(TypeUnionMembers)

    /**
     * Relationship to the union type declaration this member belongs to.
     */
    var unionTypeNode by TypeNodeEntity referencedOn TypeUnionMembers.unionNode

    /**
     * Relationship to the member type declaration within the union.
     */
    var memberTypeNode by TypeNodeEntity referencedOn TypeUnionMembers.memberNode
}