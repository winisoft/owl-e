package com.wini.owle.entity.typing

import com.wini.owle.dto.typing.enums.SchemaMembership
import com.wini.owle.dto.typing.enums.TypeNodeKind
import com.wini.owle.dto.typing.enums.TypeNodeRelationKind
import com.wini.owle.dto.typing.enums.VarianceCardinality
import com.wini.owle.entity.environs.Versions
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable


object TypeNodes : ULongIdTable("type_nodes") {
    val version = reference("version_id", Versions)
    val namespace = varchar("namespace", 1024).nullable()
    val name = varchar("name", 255).nullable()
    val kind = enumerationByName("kind", 13, TypeNodeKind::class)
    val alias = varchar("alias", 255).nullable()
    val isGeneric = bool("is_generic").default(false)
    val dedupHash = char("dedup_hash", 64).nullable().uniqueIndex()
}

object TypeNodeRelations : CompositeIdTable("type_node_relations") {
    val parentNode = reference("parent_node_id", TypeNodes)
    val childNode = reference("child_node_id", TypeNodes)
    val relationKind = enumerationByName("relation_kind", 16, TypeNodeRelationKind::class)
        .default(TypeNodeRelationKind.INHERITANCE)
    override val primaryKey = PrimaryKey(childNode, parentNode)
}

object TypeSchemaRefs : ULongIdTable("type_schema_refs") {
    val parentRef = reference("parent_ref_id", TypeSchemaRefs).nullable()
    val classNode = reference("class_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val membership = enumerationByName("member_kind", 16, SchemaMembership::class)
    val typeNode = reference("type_node_id", TypeNodes).nullable()
    val description = varchar("description", 4096).nullable()
    val position = integer("position").default(0)
}

object TypeVarBounds : ULongIdTable("typevar_bounds") {
    val typeVarNode = reference("typevar_node_id", TypeNodes)
    val boundNode = reference("bound_node_id", TypeNodes)
}

object TypeVarConstraints : ULongIdTable("typevar_constraints") {
    val typeVarNode = reference("typevar_node_id", TypeNodes)
    val constraintNode = reference("constraint_node_id", TypeNodes)
}

object TypeVarVariances : ULongIdTable("typevar_variances") {
    val typeVarNode = reference("typevar_node_id", TypeNodes).uniqueIndex()
    val variance = enumerationByName("variance", 16, VarianceCardinality::class)
        .default(VarianceCardinality.INVARIANT)
}

object TypeUnionMembers : CompositeIdTable("type_union_members") {
    val unionTypeNode = reference("union_node_id", TypeNodes)
    val memberTypeNode = reference("member_node_id", TypeNodes)
    override val primaryKey = PrimaryKey(unionTypeNode, memberTypeNode)
}

object CallableSignatures : ULongIdTable("callable_signatures") {
    val typeNode = reference("type_node_id", TypeNodes).uniqueIndex()
    val returnTypeNode = reference("return_type_node_id", TypeNodes)
    val docString = varchar("docstring", 8192).nullable()
    val isStatic = bool("is_static").default(false)
    val isClassMethod = bool("is_classmethod").default(false)
}

object CallableParameters : ULongIdTable("callable_parameters") {
    val signature = reference("signature_id", CallableSignatures)
    val position = uinteger("position")
    val paramName = varchar("param_name", 255)
    val paramTypeNode = reference("param_type_node_id", TypeNodes)
    val isVarArg = bool("is_vararg").default(false)
    val isKwarg = bool("is_kwarg").default(false)
    val defaultValueText = varchar("default_value_text", 1024).nullable()

    init {
        uniqueIndex(signature, position)
    }
}

object CallableOwners : CompositeIdTable("callable_owners") {
    val ownerNode = reference("owner_node_id", TypeNodes)
    val callableNode = reference("callable_node_id", TypeNodes)
    override val primaryKey = PrimaryKey(ownerNode, callableNode)
}

object TypeGenericParams : ULongIdTable("type_generic_params") {
    val classNode = reference("class_node_id", TypeNodes)
    val typeVar = reference("typevar_node_id", TypeNodes)
    val position = ulong("position")

    init {
        uniqueIndex(classNode, typeVar)
    }
}

object TypeInstantiations : ULongIdTable("type_instantiations") {
    val instantiationNode = reference("instantiation_node_id", TypeNodes)
    val originNode = reference("origin_node_id", TypeNodes)

    init {
        uniqueIndex(instantiationNode, originNode)
    }
}

object TypeInstantiationArgs : ULongIdTable("type_instantiation_args") {
    val instantiation = reference("instantiation_id", TypeInstantiations)
    val paramNode = reference("param_node_id", TypeNodes)
    val paramPosition = uinteger("param_position")

    init {
        uniqueIndex(instantiation, paramPosition)
    }
}

/**
 * Maps 1:1 onto TypeNodes.id — `alias_id` is both the PK and the FK to the aliasing node.
 */
object TypeAliases : IdTable<ULong>("type_aliases") {
    override val id: Column<EntityID<ULong>> = reference("alias_id", TypeNodes)
    val target = reference("target_id", TypeNodes)
    override val primaryKey = PrimaryKey(id)
}
