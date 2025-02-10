package com.wini.owle.entity

import com.wini.owle.entity.environs.ToolPackageDependencyDao
import com.wini.owle.entity.steps.ProvisionDao
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.entity.user.TagDao
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.entity.workflows.WorkflowDao
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.sql.SizedIterable
import kotlin.reflect.KProperty1


sealed class CollectionRelations<ID: Any, T>(
    val collectionFkColumn: KProperty1<T, Iterable<Entity<*>>>
) where T : Entity<ID> {

    sealed class Provision<FK: Entity<*>>(
        collectionFkColumn: KProperty1<ProvisionDao, SizedIterable<FK>>
    ) : CollectionRelations<Long, ProvisionDao>(collectionFkColumn) {
        data object DataPoints : Provision<DataPointDao>(ProvisionDao::dataPoint)
    }

    sealed class Transformation<FK: Entity<*>>(
        collectionFkColumn: KProperty1<TransformationDao, SizedIterable<FK>>
    ) : CollectionRelations<Long, TransformationDao>(collectionFkColumn) {
        data object EligibleTypeDeclarations : Transformation<TypeDeclarationDao>(TransformationDao::eligibleTypeDeclarations)
    }

    sealed class TypeDeclaration<FK: Entity<*>>(
        collectionFkColumn: KProperty1<TypeDeclarationDao, SizedIterable<FK>>
    ) : CollectionRelations<Long, TypeDeclarationDao>(collectionFkColumn) {
        data object Transformations : TypeDeclaration<TransformationDao>(TypeDeclarationDao::transformations)
    }

    sealed class Tag<FK: Entity<*>>(
        collectionFkColumn: KProperty1<TagDao, SizedIterable<FK>>
    ) : CollectionRelations<Long, TagDao>(collectionFkColumn) {
        data object ChildTags : Tag<TagDao>(TagDao::childTags)
        data object ParentTags : Tag<TagDao>(TagDao::parentTags)
        data object StepsAssociated : Tag<StepDao>(TagDao::stepsAssociated)
        data object WorkflowsAssociated : Tag<WorkflowDao>(TagDao::workflowsAssociated)
    }

    sealed class Tool<FK: Entity<*>>(
        collectionFkColumn: KProperty1<ToolDao, SizedIterable<FK>>
    ) : CollectionRelations<Long, ToolDao>(collectionFkColumn) {
        data object PackageDependencies : Tool<ToolPackageDependencyDao>(ToolDao::packageDependencies)
        //data object Steps : Tool<StepDao>(ToolDao::steps)
    }

    sealed class Workflow<FK: Entity<*>>(
        collectionFkColumn: KProperty1<WorkflowDao, SizedIterable<FK>>
    ) : CollectionRelations<Long, WorkflowDao>(collectionFkColumn) {
        //data object Steps: Workflow<StepDao>(WorkflowDao::steps)
        //data object DataPoints: Workflow<DataPointDao>(WorkflowDao::dataPoints)
    }

}