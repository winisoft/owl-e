package com.wini.owle.entity.typing

import com.wini.owle.entity.TypeUnionMembers
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents individual member types within a union type declaration.
 * Supports union types such as 'Union[int, str]' by linking each member type to the union type declaration.
 */
class TypeUnionMemberEntity(
    id: EntityID<CompositeID>
) : CompositeEntity(id) {

    companion object : CompositeEntityClass<TypeUnionMemberEntity>(TypeUnionMembers) {

        fun buildId(unionTypeId: Long, memberTypeId: Long): CompositeID {
            return CompositeID {

                it[TypeUnionMembers.unionTypeDeclaration] = unionTypeId
                it[TypeUnionMembers.memberTypeDeclaration] = memberTypeId
            }
        }

        fun create(unionTypeId: Long, memberTypeId: Long): TypeUnionMemberEntity {
            val compositeID = buildId(unionTypeId, memberTypeId)
            return TypeUnionMemberEntity(EntityID(compositeID, TypeUnionMembers))
        }

    }

    /**
     * Relationship to the union type declaration this member belongs to.
     */
    var unionTypeDeclarationEntity by TypeDeclarationEntity referencedOn TypeUnionMembers.unionTypeDeclaration

    /**
     * Relationship to the member type declaration within the union.
     */
    var memberTypeDeclarationEntity by TypeDeclarationEntity referencedOn TypeUnionMembers.memberTypeDeclaration
}