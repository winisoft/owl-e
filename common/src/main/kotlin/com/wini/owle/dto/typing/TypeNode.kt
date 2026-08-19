package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents a type in the Owl-E system
 */
@ModelDefinition
@Serializable
@ApiEndpointDefinition("api.typing.typeNode", ["type"], "/type-node")
data class TypeNode(

    override val id: ULong,
    /** **/
    val version: Version,
    /** **/
    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.TypeNode.NAMESPACE_MAX_LENGTH)
    val namespace: String?,
    /** **/
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.TypeNode.NAME_MAX_LENGTH)
    val name: String?,
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