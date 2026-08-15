package com.wini.owle.entity.transforms

import com.wini.owle.dto.transforms.enums.TransformOccurrence
import com.wini.owle.entity.DataPoints
import com.wini.owle.entity.Steps
import com.wini.owle.entity.typing.TypeNodes
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable


object TransformOccurrences : ULongIdTable("transform_occurrences") {
    val transformation = reference("transformationId", Transforms, onDelete = ReferenceOption.CASCADE).nullable()
    val occurrence = enumerationByName("occurrence", 20, TransformOccurrence::class)
 //   val parentTransform = reference("parent_transform_id", TransformOccurrences, onDelete = ReferenceOption.CASCADE).nullable()
//    val childTransform = reference("child_transform_id", TransformOccurrences, onDelete = ReferenceOption.CASCADE).nullable()
}


object TransformOccurrenceArgs : ULongIdTable("transform_arg_occurrences") {
    val transformArg = reference("transform_arg_id", TransformArgs, onDelete = ReferenceOption.CASCADE)
    val transformOccurrence = reference("transform_occurrence_id", TransformOccurrences, onDelete = ReferenceOption.CASCADE)
    val value = text("value")
}

object TransformOccurrenceChains : CompositeIdTable("") {
    val transformOccurrence = reference("transform_occurrence_id", TransformOccurrences)
    val transformArg = reference("transform_arg_id", TransformArgs)
    val value = text("value")
}

object Transforms : ULongIdTable("transforms") {
    val name = varchar("name", 255)
    val description = varchar("description", 1024).nullable()
    val examples = varchar("examples", 255).nullable()
}

object TransformTargetTypes : CompositeIdTable("transform_target_types") {
    val transform = reference("transform_id", Transforms, onDelete = ReferenceOption.CASCADE)
    val eligibleType = reference("eligible_type_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val returnType = reference("return_type_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(transform, eligibleType)
}


object TransformArgs : ULongIdTable("transform_args") {
    val transform = reference("transform_id", Transforms, onDelete = ReferenceOption.CASCADE)
    val typeNode = reference("type_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
}
