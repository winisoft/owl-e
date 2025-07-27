package com.wini.owle.data.ext

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.wini.owle.data.Constants.DB_NAME
import com.wini.owle.data.Constants.OUTPUT_DIR_SQL
import com.wini.owle.data.Table
import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.environs.*
import com.wini.owle.dto.runs.step.*
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.runs.workflow.*
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.workflows.*
import com.wini.owle.dto.user.*
import com.wini.owle.dto.user.tags.*
import java.io.OutputStream
import kotlin.reflect.KClass


fun fileName(name: String, execIteration: Int, phase: Int): String {
    if (execIteration > 999 || phase > 99)
        throw Exception()
    return "${"$execIteration".padStart(3, '0')}_${name}_${"$phase".padStart(2, '0')}"
}

/**
 * Default convenience function
 */
fun CodeGenerator.createNewFile(name: String, iteration: Int, phase: Int): OutputStream {
    return createNewFile(
        dependencies = Dependencies(false),
        packageName = OUTPUT_DIR_SQL,
        fileName = fileName(name, iteration, phase),
        extensionName = "sql"
    )
}

fun String.maybeComma(index: Int, all: Collection<*>): String {
    return "$this${if (index == all.size-1) ";" else ","}"
}

val KClass<*>.insertText: String get() = when (qualifiedName) {

    PythonVersion::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.python_versions}` ${ValArgs.python_versions} VALUES"

    BenchmarkReport::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.benchmarking_data}` ${Table.benchmarking_data} VALUES"

    TypeUnionMember::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.type_union_members}` ${Table.type_union_members} VALUES"

    TestCase::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.test_cases}` ${Table.test_cases} VALUES"

    VersionSpecifier::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.version_specifiers}` ${Table.version_specifiers} VALUES"

    ProvisionDataPoint::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.provision_data_points}` ${Table.provision_data_points} VALUES"

    TransformArg::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.transformation_args}` ${Table.transformation_args} VALUES"

    ErrorWatch::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.error_watches}` ${Table.error_watches} VALUES"

    SpecifierElement::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.specifier_elements}` ${Table.specifier_elements} VALUES"

    ToolParam::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.tool_params}` ${Table.tool_params} VALUES"

    TypeParameter::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.type_parameters}` ${Table.type_parameters} VALUES"

    AppliedTransform::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.applied_transforms}` ${Table.applied_transforms} VALUES"

    TransformTargetType::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.transform_target_types}` ${Table.transform_target_types} VALUES"

    WorkflowExecution::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.workflow_executions}` ${Table.workflow_executions} VALUES"

    ErrorWatchFn::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.error_watch_fns}` ${Table.error_watch_fns} VALUES"

    ToolPyPackDependency::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.workflow_environment_dependencies}` ${Table.workflow_environment_dependencies} VALUES"

    Provision::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.provisions}` ${Table.provisions} VALUES"

    TypeDeclaration::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.type_declarations}` ${Table.type_declarations} VALUES"

    VersionSpec::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.version_specifiers}` ${Table.version_specifiers} VALUES"

    InitialDataPointInjector::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.prereq_injections}` ${Table.prereq_injections} VALUES"

    AppliedTransformArg::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.applied_transform_args}` ${Table.applied_transform_args} VALUES"

    TypeDefinition::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.type_definitions}` ${Table.type_definitions} VALUES"

    WorkflowJunction::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.workflow_junctions}` ${Table.workflow_junctions} VALUES"

    TypeVariance::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.type_variance}` ${Table.type_variance} VALUES"

    TestExpectation::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.test_expected_outcomes}` ${Table.test_expected_outcomes} VALUES"

    Distribution::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.distributions}` ${Table.distributions} VALUES"

    VersionSpecSpecElement::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.version_specifier_specifier_elements}` ${Table.version_specifier_specifier_elements} VALUES"

    StepRunLogLine::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.execution_logs}` ${Table.execution_logs} VALUES"

    StepTag::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.step_tags}` ${Table.step_tags} VALUES"

    Transform::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.transformations}` ${Table.transformations} VALUES"

    Inquisition::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.inquisitions}` ${Table.inquisitions} VALUES"

    VersionOverflow::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.overflow_version_components}` ${Table.overflow_version_components} VALUES"

    TestInput::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.test_inputs}` ${Table.test_inputs} VALUES"

    StepRun::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.step_executions}` ${Table.step_executions} VALUES"

    DataPointSave::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.revisions}` ${Table.revisions} VALUES"

    WorkflowTestResult::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.test_results}` ${Table.test_results} VALUES"

    TypeSchemaRef::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.type_schema_refs}` ${Table.type_schema_refs} VALUES"

    TypeBound::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.type_bounds}` ${Table.type_bounds} VALUES"

    Tag::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.tags}` ${Table.tags} VALUES"

    TagRelation::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.tag_relations}` ${Table.tag_relations} VALUES"

    WorkflowTag::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.workflow_tags}` ${Table.workflow_tags} VALUES"

    PaymentMethod::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.payment_methods}` ${Table.payment_methods} VALUES"

    User::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.users}` ${Table.users} VALUES"

    DataPoint::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.data_points}` ${Table.data_points} VALUES"

    Phase::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.phases}` ${Table.phases} VALUES"

    Step::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.steps}` ${Table.steps} VALUES"

    Tool::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.tools}` ${Table.tools} VALUES"

    Workflow::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.workflows}` ${Table.workflows} VALUES"

    WorkflowFolder::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.workflow_folders}` ${Table.workflow_folders} VALUES"

    WorkflowMetadata::class.qualifiedName
        -> "INSERT INTO `${DB_NAME}`.`${Table.workflow_metadata}` ${Table.workflow_metadata} VALUES"

    else -> throw IllegalArgumentException("Unexpected type for insertText")

}