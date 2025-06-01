package com.wini.owle.entity.typing

import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.steps.DataPointWriteLinks
import com.wini.owle.entity.steps.ToolParamDataPointReadEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * Every kind of type (base, instantiation, typevar, union, callable, alias) lives in type_nodes; all queries converge
 * on a single primary key space. All insights into the unique characteristics of a given type -- union membership,
 * declared fields, for example -- are a single association away in a related data type.
 *
 */
class TypeNodeEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeNodeEntity>(TypeNodes)

    /** */
    var version by VersionEntity referencedOn TypeNodes.version
    /** */
    var namespace by TypeNodes.namespace
    /** */
    var name by TypeNodes.name
    /** for TypeVar names or “MyList = List\[int]”*/
    var alias by TypeNodes.alias
    /** */
    var isGeneric by TypeNodes.isGeneric

    var kind by TypeNodes.kind

    // Gets base classes: this node is a child in the relation
    val baseClasses get() = TypeNodeRelationEntity.find { TypeNodeRelations.childNode eq id }.map { it.parentNode }

    // Gets subclasses: this node is a parent in the relation
    val subclasses get() = TypeNodeRelationEntity.find { TypeNodeRelations.parentNode eq id }.map { it.childNode }

    /** Schema members defined by this type **/
    val schemaRefs: SizedIterable<TypeSchemaRefEntity> by TypeSchemaRefEntity referrersOn TypeSchemaRefs.classNode
    /** If this node is used as a schema member type **/
    val usedInSchemas: SizedIterable<TypeSchemaRefEntity> by TypeSchemaRefEntity referrersOn TypeSchemaRefs.typeNode
    /** Type parameters declared by this type  **/
    val typeParameters: SizedIterable<TypeParameterEntity> by TypeParameterEntity referrersOn TypeParameters.typeDeclaration
    /** If this is a type parameter pointing to this node **/
    val referencedAsParameter: SizedIterable<TypeParameterEntity> by TypeParameterEntity referrersOn TypeParameters.parameterTypeDeclaration
    /** Variance relationships **/
    val variance: TypeVarVarianceEntity? by TypeVarVarianceEntity optionalBackReferencedOn TypeVarVariances.typeVarNode
    /** */
    val constraints: SizedIterable<TypeVarConstraintEntity> by TypeVarConstraintEntity referrersOn TypeVarConstraints.typeVarNode
    /** */
    val bounds: SizedIterable<TypeVarBoundEntity> by TypeVarBoundEntity referrersOn TypeVarBounds.typeVarNode
    /** Union Membership **/
    val unionMemberships: SizedIterable<TypeUnionMemberEntity> by TypeUnionMemberEntity referrersOn TypeUnionMembers.memberNode
    /** */
    val unionRoot: SizedIterable<TypeUnionMemberEntity> by TypeUnionMemberEntity referrersOn TypeUnionMembers.unionNode



}