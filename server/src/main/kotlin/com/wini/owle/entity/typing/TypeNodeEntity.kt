package com.wini.owle.entity.typing

import com.wini.owle.entity.environs.version.VersionEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * Every kind of type (base, instantiation, typevar, union, callable, alias) lives in type_nodes; all queries converge
 * on a single primary key space. All insights into the unique characteristics of a given type -- union membership,
 * declared fields, for example -- are a single association away in a related data type.
 */
class TypeNodeEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeNodeEntity>(TypeNodes)

    /** The Python version whose introspection produced this node. */
    var version by VersionEntity referencedOn TypeNodes.version

    /** Dotted module path the type was found in. */
    var namespace by TypeNodes.namespace

    var name by TypeNodes.name

    /** for TypeVar names or “MyList = List\[int]” */
    var alias by TypeNodes.alias

    var isGeneric by TypeNodes.isGeneric

    var kind by TypeNodes.kind

    /** Content hash that collapses structurally identical nodes at ingest time; unique where present. */
    var dedupHash by TypeNodes.dedupHash

    // --- inheritance ------------------------------------------------------------------------

    /** Direct supertypes — this node sits on the child side of the relation. */
    val baseClasses: SizedIterable<TypeNodeEntity>
            by TypeNodeEntity.via(TypeNodeRelations.childNode, TypeNodeRelations.parentNode)

    /** Direct subtypes — this node sits on the parent side of the relation. */
    val subclasses: SizedIterable<TypeNodeEntity>
            by TypeNodeEntity.via(TypeNodeRelations.parentNode, TypeNodeRelations.childNode)

    // --- schema membership ------------------------------------------------------------------

    /** Schema members declared by this type. */
    val schemaRefs: SizedIterable<TypeSchemaRefEntity> by TypeSchemaRefEntity referrersOn TypeSchemaRefs.classNode

    /** Schema members elsewhere that are typed as this node. */
    val usedInSchemas: SizedIterable<TypeSchemaRefEntity> by TypeSchemaRefEntity optionalReferrersOn TypeSchemaRefs.typeNode

    // --- typevar facets ---------------------------------------------------------------------

    /** At most one variance row per TypeVar. */
    val variance: TypeVarVarianceEntity? by TypeVarVarianceEntity optionalBackReferencedOn TypeVarVariances.typeVarNode

    val constraints: SizedIterable<TypeVarConstraintEntity> by TypeVarConstraintEntity referrersOn TypeVarConstraints.typeVarNode

    val bounds: SizedIterable<TypeVarBoundEntity> by TypeVarBoundEntity referrersOn TypeVarBounds.typeVarNode

    // --- generics ---------------------------------------------------------------------------

    /** TypeVars this class declares, in position order. */
    val genericParams: SizedIterable<TypeGenericParamEntity> by TypeGenericParamEntity referrersOn TypeGenericParams.classNode

    /** Rows where this node is the parameterized form, e.g. `List[int]`. */
    val asInstantiation: SizedIterable<TypeInstantiationEntity> by TypeInstantiationEntity referrersOn TypeInstantiations.instantiationNode

    /** Rows where this node is the generic origin being parameterized, e.g. `List`. */
    val instantiationsOfThis: SizedIterable<TypeInstantiationEntity> by TypeInstantiationEntity referrersOn TypeInstantiations.originNode

    // --- unions -----------------------------------------------------------------------------

    /** Member types, when this node is a union. */
    val unionMembers: SizedIterable<TypeNodeEntity>
            by TypeNodeEntity.via(TypeUnionMembers.unionTypeNode, TypeUnionMembers.memberTypeNode)

    /** Unions this node participates in. */
    val memberOfUnions: SizedIterable<TypeNodeEntity>
            by TypeNodeEntity.via(TypeUnionMembers.memberTypeNode, TypeUnionMembers.unionTypeNode)

    // --- callables --------------------------------------------------------------------------

    /** The signature, when this node is itself a callable. */
    val signature: CallableSignatureEntity? by CallableSignatureEntity optionalBackReferencedOn CallableSignatures.typeNode

    /** Callables owned by this node — the methods, when this node is a class. */
    val ownedCallables: SizedIterable<TypeNodeEntity>
            by TypeNodeEntity.via(CallableOwners.ownerNode, CallableOwners.callableNode)

    /** Types that own this node as a callable. */
    val owners: SizedIterable<TypeNodeEntity>
            by TypeNodeEntity.via(CallableOwners.callableNode, CallableOwners.ownerNode)

    // --- aliasing ---------------------------------------------------------------------------

    /** Set when this node is an alias; resolves to what it aliases. */
    val aliasOf: TypeAliasEntity? by TypeAliasEntity optionalBackReferencedOn TypeAliases.target
}
