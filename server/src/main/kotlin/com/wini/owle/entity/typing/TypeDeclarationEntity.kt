package com.wini.owle.entity.typing

import com.wini.owle.entity.TransformTargetTypes
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeUnionMembers
import com.wini.owle.entity.transforms.TransformEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents specific usages or instances of types, including aliases and complex structures.
 * Each type declaration can represent either a basic type usage or a complex structure using type parameters.
 */
class TypeDeclarationEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TypeDeclarationEntity>(TypeDeclarations)

    /**
     * Relationship to the type definition this declaration is based on.
     */
    var typeDefinitionEntity by TypeDefinitionEntity referencedOn TypeDeclarations.typeDefinition

    /**
     * Optional alias for the type declaration, e.g., 'AliasForType'.
     */
    var alias by TypeDeclarations.alias

    /**
     * The transformations for which this type is applicable
     */
    var transformations by TransformEntity via TransformTargetTypes

    /**
     *
     */
    val memberTypeDeclarationEntity by TypeDeclarationEntity referrersOn TypeUnionMembers

    /**
     *
     */
    val unionTypeDeclarationEntity by TypeDeclarationEntity referrersOn TypeUnionMembers
}