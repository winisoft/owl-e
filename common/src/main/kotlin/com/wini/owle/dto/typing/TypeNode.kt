package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * Represents a type in the Owl-E system
 */
@Serializable
data class TypeNode(

    override val id: ULong,
    /** **/
    val version: Version,
    /** **/
    val namespace: String?,
    /** **/
    val name: String,
    /** **/
    val alias: String?,
    /** **/
    val isGeneric: Boolean,
    /** Schema members defined : this type **/
//    val schemaRefs : List<TypeSchemaRef>,
//    /** If this node is used as a schema member type **/
//    val usedInSchemas : List<TypeSchemaRef>,
//    /** Type parameters declared : this type  **/
//    val typeParameters : List<TypeParameter>,
//    /** If this is a type parameter pointing to this node **/
//    val referencedAsParameter : List<TypeParameter>,
//    /** Variance relationships **/
//    val variance : TypeVarVariance?,
//    /** **/
//    val constraints : List<TypeVarConstraint?>,
//    /** **/
//    val bounds : List<TypeVarBound>,
//    /** Literal Members **/
//    val literalMembers : List<LiteralMember>,
//    /** Union Membership **/
//    val unionMemberships : List<TypeUnionMember>,
//    /** **/
//    val unionRoot : List<TypeUnionMember>,
//    /** Class Inheritance **/
//    val baseClasses : List<ClassInheritance>,
//    /** **/
//    val subclasses : List<ClassInheritance>,
    
) : ULongDto