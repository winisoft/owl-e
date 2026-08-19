package com.wini.owle.dto.testing

import com.wini.owle.dto.environs.enums.EnvMarkerExpressionMode
import com.wini.owle.dto.environs.enums.EnvMarkerVariable
import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.dto.environs.marker.EnvMarkerExpression
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.dto.environs.pkg.PackageDistRequirement
import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.dto.environs.version.PythonVersionSpecifier
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.dto.environs.version.VersionOverflow
import com.wini.owle.dto.environs.version.VersionSpecifier
import com.wini.owle.dto.environs.version.enums.OverflowViolation
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.dto.runs.step.StepTestRun
import com.wini.owle.dto.runs.step.ExecutionNotification
import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestOutcomeExpectation
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.steps.DataPointInjection
import com.wini.owle.dto.steps.DataPointRead
import com.wini.owle.dto.steps.DataPointWrite
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.tags.enums.TagSource
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.dto.typing.enums.VarianceCardinality
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.dto.user.enums.UserStatus
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Grouper
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.dto.runs.test.TestExpectedOutcome
import com.wini.owle.dto.runs.workflow.TestResult

/**
 * Canned [transforms] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object TransformsDtoFactory {

    fun transformTargetType(
        transform: Transform = transformDto(),
        eligibleTypeNode: TypeNode = TypingDtoFactory.typeNode(),
        returnTypeNode: TypeNode = TypingDtoFactory.typeNode()
    ): TransformTargetType = TransformTargetType(
        transform = transform,
        eligibleTypeNode = eligibleTypeNode,
        returnTypeNode = returnTypeNode
    )

    fun transformDto(
        id: ULong = 1UL,
        name: String = "Transformation Name",
        description: String = "Transformation Description",
        examples: String? = "Example",
        returnsType: TypeNode = TypingDtoFactory.typeNode()
    ) : Transform {
        return Transform(
            id = id,
            name = name,
            description = description,
            examples = examples
        )
    }

    fun transformOccurrenceArgDto(
        id: ULong = 0UL,
        transformOccurrence: TransformOccurrence = transformOccurrenceDto(),
        transformationArg: TransformArg = transformArgDto(),
        value: String = ""
    ) : TransformOccurrenceArg {
        return TransformOccurrenceArg(
            id = id,
            transformOccurrence = transformOccurrence,
            transformArg = transformationArg,
            value = value
        )
    }

    fun transformArgDto(
        id: ULong = 0UL,
        transformation: Transform = transformDto(),
        name: String = "Transformation Arg Name",
        typeNode: TypeNode = TypingDtoFactory.typeNode(),
        description: String? = "Transformation Arg Description"
    ): TransformArg {
        return TransformArg(
            id = id,
            transform = transformation,
            name = name,
            typeNode = typeNode,
            description = description
        )
    }

    fun transformOccurrenceDto(
        id: ULong = 0UL,
        transformation: Transform = transformDto(),
        occurrence: com.wini.owle.dto.transforms.enums.TransformOccurrenceInitiator = com.wini.owle.dto.transforms.enums.TransformOccurrenceInitiator.DATA_POINT_INJECTION,
        resultType: TypeNode = TypingDtoFactory.typeNode()
    ) : TransformOccurrence {
        return TransformOccurrence(
            id = id,
            transform = transformation,
            initiator = occurrence,
        )
    }
}
