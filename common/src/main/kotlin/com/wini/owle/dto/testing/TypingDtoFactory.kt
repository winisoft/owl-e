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
import com.wini.owle.dto.typing.TypeParameter
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
import com.wini.owle.dto.typing.CallableParameter
import com.wini.owle.dto.typing.CallableSignature

/**
 * Canned [typing] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object TypingDtoFactory {

    fun typeUnionMember(
        unionTypeNode: TypeNode = typeNode(),
        memberTypeNode: TypeNode = typeNode(),
    ): TypeUnionMember = TypeUnionMember(
        unionTypeNode = unionTypeNode,
        memberTypeNode = memberTypeNode
    )

    fun typeParameterDto(
        id: ULong = 1UL,
        typeDeclaration: TypeNode = typeNode(),
        position: Int = 1,
        parameterTypeDeclaration: TypeNode = typeNode()
    ) : TypeParameter {
        return TypeParameter(
            id = id,
            typeDeclaration = typeDeclaration,
            position = position,
            parameterTypeDeclaration = parameterTypeDeclaration
        )
    }

    fun typeSchemaRefDto(
        id: ULong = 1UL,
        parentRef: TypeSchemaRef? = null,
        name: String = "exampleSchema",
        typeDeclaration: TypeNode = typeNode(),
        description: String = "Schema reference description",
        childRefs: List<TypeSchemaRef> = emptyList()
    ) : TypeSchemaRef {
        return TypeSchemaRef(
            id = id,
            parentRef = parentRef,
            name = name,
            typeNode = typeDeclaration,
            description = description,
            children = childRefs
        )
    }

    fun typeNode(): TypeNode = TypeNode(
        id = 1UL,
        version = EnvironsDtoFactory.version(),
        null,
        "",
        null,
        false
    )

    fun typeUnionMemberDto(
        unionTypeDeclarationId: TypeNode = typeNode(),
        memberTypeDeclarationId: TypeNode = typeNode()
    ): TypeUnionMember {
        return TypeUnionMember(
            unionTypeNode = unionTypeDeclarationId,
            memberTypeNode = memberTypeDeclarationId
        )
    }

    fun typeVarianceDto(
        id: ULong = 23UL,
        typeVariableDeclaration: TypeNode = typeNode(),
        varianceCardinality: VarianceCardinality = VarianceCardinality.COVARIANT
    ): TypeVarVariance {
        return TypeVarVariance(
            id = 1UL,
            typeVarNode = typeVariableDeclaration,
            varianceCardinality = varianceCardinality
        )
    }

    fun typeVarBoundDto(
        id: ULong = 1UL,
        boundTypeDeclaration: TypeNode = typeNode(),
        typeVariableDeclaration: TypeNode = typeNode()
    ) : TypeVarBound {
        return TypeVarBound(
            id = 1UL,
            boundTypeNode = boundTypeDeclaration,
            typeVarNode = typeVariableDeclaration
        )
    }

    fun callableSignatureDto(
        id: ULong = 1UL,
        typeNode: TypeNode = typeNode(),
        returnTypeNode: TypeNode = typeNode(),
        docstring: String? = "An example callable.",
        isStatic: Boolean = false,
        isClassmethod: Boolean = false
    ): CallableSignature = CallableSignature(
        id = id,
        typeNode = typeNode,
        returnTypeNode = returnTypeNode,
        docstring = docstring,
        isStatic = isStatic,
        isClassmethod = isClassmethod
    )

    fun callableParameterDto(
        id: ULong = 1UL,
        signature: CallableSignature = callableSignatureDto(),
        paramTypeNode: TypeNode = typeNode(),
        position: UInt = 0u,
        paramName: String = "url",
        isVarArg: Boolean = false,
        isKwarg: Boolean = false,
        defaultValueText: String? = null
    ): CallableParameter = CallableParameter(
        id = id,
        signature = signature,
        paramTypeNode = paramTypeNode,
        position = position,
        paramName = paramName,
        isVarArg = isVarArg,
        isKwarg = isKwarg,
        defaultValueText = defaultValueText
    )
}
