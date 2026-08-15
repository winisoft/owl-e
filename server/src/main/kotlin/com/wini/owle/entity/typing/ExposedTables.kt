package com.wini.owle.entity.typing

import com.wini.owle.dto.typing.enums.TypeNodeKind
import com.wini.owle.dto.typing.enums.SchemaMembership
import com.wini.owle.dto.typing.enums.VarianceCardinality
import com.wini.owle.entity.environs.Versions
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable


object TypeNodes : ULongIdTable("type_nodes") {
    val version = reference("version_id", Versions)
    val kind = enumerationByName("kind", 13, TypeNodeKind::class)
    val namespace = varchar("namespace", 1024).nullable()
    val name = varchar("name", 255).nullable()
    val alias = varchar("alias", 255).nullable()
    val isGeneric = bool("is_generic").default(false)
}

object TypeNodeRelations : CompositeIdTable("type_node_relations") {
    val parentNode = reference("parent_node_id", TypeNodes)
    val childNode = reference("child_node_id", TypeNodes)
    override val primaryKey = PrimaryKey(parentNode, childNode)
}

object TypeParameters : ULongIdTable("type_parameters") {
    val typeDeclaration = reference("typeDeclarationId", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val position = integer("position")
    val parameterTypeDeclaration = reference("parameterTypeDeclarationId", TypeNodes, onDelete = ReferenceOption.CASCADE)
}

object TypeSchemaRefs : ULongIdTable("type_schema_refs") {

    val parentRef = reference("parent_ref_id", TypeSchemaRefs, onDelete = ReferenceOption.CASCADE).nullable()
    val classNode = reference("class_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val membership = enumerationByName("member_kind", 9, SchemaMembership::class)
    val typeNode = reference("type_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val description = varchar("description", 4096).nullable()
    val position = integer("position").default(0)

}

object TypeVarBounds : CompositeIdTable("typevar_bounds") {
    val typeVarNode = reference("typevar_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val boundNode = reference("bound_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
}

object TypeVarConstraints : CompositeIdTable("typevar_constraints") {
    val typeVarNode = reference("typevar_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val constraintNode = reference("constraint_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
}

object TypeVarVariances : CompositeIdTable("type_variances") {
    val typeVarNode = reference("typevar_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val variance = enumerationByName("variance", 11, VarianceCardinality::class)
}


object TypeUnionMembers : CompositeIdTable("type_union_members") {
    val unionNode = reference("union_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val memberNode = reference("member_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(unionNode, memberNode)
}

object CallableSignatures : ULongIdTable("callable_signatures") {
    val parentSchemaRef = reference("parent_schema_ref_id", TypeSchemaRefs, onDelete = ReferenceOption.CASCADE)
    val returnTypeNode = reference("return_type_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255).nullable()
    val docString = varchar("docstring", 8192)
    val isStatic = bool("is_static").default(false)
    val isClassMethod = bool("is_classmethod").default(false)
}

object CallableParameters : ULongIdTable("callable_parameters") {
    val signature = reference("signature_id", CallableSignatures, onDelete = ReferenceOption.CASCADE)
    val paramTypeNode = reference("param_type_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val position = uinteger("position")
    val paramName = varchar("param_name", 255)
    val isVarArg = bool("is_vararg").default(false)
    val isKArg = bool("is_kwarg").default(false)
    val defaultValueText = varchar("default_value_text", 1024)
}

object TypeGenericParams: ULongIdTable("type_generic_params") {
    val classNode = reference("class_node_id", TypeNodes)
    val typeVar = reference("typevar_node_id", TypeNodes)
    val position = ulong("position")
}

object TypeInstantiations : ULongIdTable("type_instantiations") {
    val instantiation = reference("instantiation_node_id", TypeNodes)
    val origin = reference("origin_node_id", TypeNodes)
}

object TypeInstantiationArgs : ULongIdTable("type_instantiation_args") {
    val instantiation = reference("instantiation_id", TypeNodes)
    val param = reference("param_node_id", TypeNodes)
    val paramPosition = integer("param_position")
}

