package com.wini.owle.dto

import com.wini.owle.dto.enums.*
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * A 'Plain Old Java Object' type with properties that map directly onto a persistence-layer Entity type
 * that has only a single function called on it to produce that corresponding type
 */
@Serializable
sealed interface BaseDto

@Serializable
data class DataPointDto(
    val id: Long,
    val workflow: WorkflowDto,
    val name: String,
    val description: String? = null,
    val presetValue: String? = null,
    val storeMethod: StoreMethod,
    val typeDeclaration: TypeDeclarationDto,
    val createdAt: String,
    val updatedAt: String
) : BaseDto


@Serializable
data class WorkflowFolderDto(
    val id: Long,
    val parentFolder: WorkflowFolderDto? = null,
    val name: String,
    val description: String? = null,
    val img: ByteArray? = null,
    val createdAt: String,
    val updatedAt: String
) : BaseDto

@Serializable
data class PackageRepoDto(
    val id: Long,
    val sourceType: PackageRepoType,
    val sourceUrl: String?
) : BaseDto

@Serializable
data class ToolDto(
    val id: Long,
    val name: String,
    val description: String? = null,
    val body: String,
    val returnTypeDeclaration: TypeDeclarationDto?
) : BaseDto

@Serializable
data class WorkflowDto(
    val id: Long,
    val name: String,
    val description: String?,
    val pythonVersion: String,
    val parentFolder: WorkflowFolderDto?,
    val metadata: UserDesignedMetadataDto,
    val createdAt: String,
    val updatedAt: String
) : BaseDto

@Serializable
data class VersionSpecDto(
    val id: Long,
    val versionMin: String? = null,
    val versionMax: String? = null,
    val versionExact: String? = null
) : BaseDto

@Serializable
data class StepDto(
    val id: Long,
    val name: String,
    val description: String?,
    val parentPhase: PhaseDto?,
    val displayOrder: Int,
    val workflow: WorkflowDto,
    val isWorkflowHead: Boolean,
    val tool: ToolDto,
    val nextStep: StepDto?
) : BaseDto

@Serializable
data class PackageDto(
    val id: Long,
    val name: String,
    val disambiguation: String?,
    val versionSpec: VersionSpecDto
) : BaseDto

@Serializable
data class ToolPackageDependencyDto(
    val toolId: Long,
    val packageId: Long,
    val versionSpecId: Long
) : BaseDto

@Serializable
data class BenchmarkingDataDto(
    val id: Long,
    val workflowExecution: WorkflowExecutionDto,
    val totalExecutionTime: DecimalValue,
    val totalSteps: Int,
    val aiDeterminedSteps: Int,
    val predeterminedSteps: Int
) : BaseDto

@Serializable
data class ExecutionLogDto(
    val id: Long,
    val stepExecution: StepExecutionDto,
    val timestamp: String,
    val logLevel: LogLevel,
    val message: String
) : BaseDto

@Serializable
data class StepBenchmarkingDataDto(
    val id: Long,
    val stepExecution: StepExecutionDto,
    val executionTime: DecimalValue,
    val resourceUsage: ResourceUsageDto?
) : BaseDto

@Serializable
data class StepExecutionDto(
    val id: Long,
    val workflowExecution: WorkflowExecutionDto,
    val step: StepDto,
    val startedAt: LocalDateTime,
    val completedAt: LocalDateTime?,
    val status: ExecutionStatus
) : BaseDto

@Serializable
data class TestCaseDto(
    val id: Long,
    val user: UserDto,
    val name: String,
    val description: String?,
    val workflow: WorkflowDto?,
    val step: StepDto?,
    val inputs: List<TestInputDto>,
    val expectedOutcomes: List<TestExpectedOutcomeDto>,
    val results: List<TestResultDto>
) : BaseDto

@Serializable
data class TestInputDto(
    val id: Long,
    val testCase: TestCaseDto,
    val dataPointName: String,
    val value: String?,
    val generationMethod: GenerationMethod
) : BaseDto

@Serializable
data class TestExpectedOutcomeDto(
    val id: Long,
    val testCase: TestCaseDto,
    val dataPointName: String,
    val expectedValue: String?,
    val comparisonOperator: ComparisonOperator
) : BaseDto

@Serializable
data class TestResultDto(
    val id: Long,
    val testCase: TestCaseDto,
    val workflowExecution: WorkflowExecutionDto,
    val passed: Boolean,
    val executedAt: String
) : BaseDto

@Serializable
data class WorkflowExecutionDto(
    val id: Long,
    val workflow: WorkflowDto,
    val user: UserDto,
    val startedAt: LocalDateTime,
    val completedAt: LocalDateTime?,
    val status: ExecutionStatus,
    val stepExecutions: List<StepExecutionDto>
) : BaseDto

@Serializable
data class ErrorWatchDto(
    val id: Long,
    val step: StepDto,
    val errorType: String,
    val triggerStep: StepDto
) : BaseDto

@Serializable
data class WorkflowJunctionDto(
    val id: Long,
    val sourceStep: StepDto,
    val targetWorkflow: WorkflowDto,
    val triggerInquisition: InquisitionDto?
) : BaseDto

@Serializable
data class ErrorWatchFnDto(
    val id: Long,
    val errorWatch: ErrorWatchDto,
    val functionName: String,
    val position: Int
) : BaseDto

@Serializable
data class InquisitionDto(
    val id: Long,
    val step: StepDto,
    val className: String,
    val dataPoint: DataPointDto,
    val onMetWorkflowStep: StepDto,
    val executionOrder: Int
) : BaseDto

@Serializable
data class PreReqInjectionDto(
    val id: Long,
    val junction: WorkflowJunctionDto,
    val sourceDataPoint: DataPointDto,
    val injectedDataPoint: DataPointDto,
    val name: String?,
    val description: String?
) : BaseDto

@Serializable
data class AppliedTransformDto(
    val id: Long,
    val transformation: TransformationDto,
    val occurrence: TransformOccurrence,
    val resultType: TypeDeclarationDto,
    val dataPoint: DataPointDto?
) : BaseDto

@Serializable
data class AppliedTransformArgDto(
    val id: Long,
    val appliedTransform: AppliedTransformDto,
    val transformationArg: TransformationArgDto,
    val value: String
) : BaseDto

@Serializable
data class TransformationArgDto(
    val id: Long,
    val transformation: TransformationDto,
    val typeDeclaration: TypeDeclarationDto,
    val name: String,
    val description: String?
) : BaseDto

@Serializable
data class ToolParamDto(
    val id: Long,
    val name: String,
    val tool: ToolDto,
    val typeDeclaration: TypeDeclarationDto,
    val hasDefault: Boolean,
) : BaseDto

@Serializable
data class ProvisionDto(
    val id: Long,
    val step: StepDto,
    val toolParam: ToolParamDto,
    val dataPoints: List<ProvisionDataPointDto>
) : BaseDto

@Serializable
data class TransformationDto(
    val id: Long,
    val name: String,
    val description: String,
    val examples: String?,
    val returnsType: TypeDeclarationDto?
) : BaseDto

@Serializable
data class TransformationTargetTypeDto(
    val transformationId: Long,
    val typeDeclarationId: Long
) : BaseDto

@Serializable
data class TypeDeclarationDto(
    val id: Long,
    val typeDefinition: TypeDefinitionDto,
    val alias: String? = null
) : BaseDto

@Serializable
data class TypeDefinitionDto(
    val id: Long,
    val name: String,
    val namespace: String,
    val versionSpec: VersionSpecDto
) : BaseDto

@Serializable
data class TypeParameterDto(
    val id: Long,
    val typeDeclaration: TypeDeclarationDto,
    val position: Int,
    val parameterTypeDeclaration: TypeDeclarationDto
) : BaseDto

@Serializable
data class TypeBoundsDto(
    val id: Long,
    val boundTypeDeclaration: TypeDeclarationDto,
    val typeVariableDeclaration: TypeDeclarationDto
) : BaseDto

@Serializable
data class TypeSchemaRefDto(
    val id: Long,
    val parentRef: TypeSchemaRefDto?,
    val name: String,
    val typeDeclaration: TypeDeclarationDto,
    val description: String?,
    val childRefs: List<TypeSchemaRefDto>
) : BaseDto

@Serializable
data class TypeVarianceDto(
    val id: Long,
    val typeVariableDeclaration: TypeDeclarationDto,
    val varianceCardinality: VarianceCardinality
) : BaseDto

@Serializable
data class TagDto(
    val id: Long,
    val name: String,
    val description: String? = null,
    val user: UserDto
) : BaseDto

@Serializable
data class StepTagDto(
    val stepId: Long,
    val tagId: Long
) : BaseDto

@Serializable
data class WorkflowTagDto(
    val workflowId: Long,
    val tagId: Long
) : BaseDto

@Serializable
data class UserDto(
    val id: Long,
    val email: String,
    val password: String,
    val resetToken: String? = null,
    val oauthProvider: String? = null,
    val oauthId: String? = null,
    val subscriptionStatus: SubscriptionStatus,
    val apiKey: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val paymentMethods: List<PaymentMethodDto> = emptyList()
) : BaseDto

@Serializable
data class RevisionDto(
    val id: Long,
    val dataPoint: DataPointDto,
    val step: StepDto,
) : BaseDto

@Serializable
data class ProvisionDataPointDto(
    val provisionId: Long,
    val dataPointId: Long
) : BaseDto

@Serializable
data class TypeUnionMemberDto(
    val unionTypeDeclarationId: Long,
    val memberTypeDeclarationId: Long
) : BaseDto

@Serializable
data class PaymentMethodDto(
    val id: Long,
    val stripePaymentMethodId: String?,
    val user: UserDto,
    val cardBrand: CardBrand?,
    val lastFour: String?,
    val isDefault: Boolean,
    val createdAt: String,
    val updatedAt: String
) : BaseDto

@Serializable
data class TagRelationDto(
    val parentTagId: Long,
    val childTagId: Long
) : BaseDto

@Serializable
data class PhaseDto(
    val id: Long = 0L,
    val workflow: WorkflowDto,
    val parentPhase: PhaseDto?,
    val name: String,
    val description: String?,
    val displayOrder: Int
) : BaseDto

/**
 * Represents metadata for workflows or steps within the Owl-E application.
 * Includes information on authorship, usage permissions, and sharing configuration.
 */
@Serializable
data class UserDesignedMetadataDto(
    /**
     * The name of the entity (e.g., workflow or step).
     * Required.
     */
    val name: String,

    /**
     * The version of the entity, following semantic versioning (e.g., "1.0.0").
     * Defaults to "1.0.0" if not provided.
     */
    val version: String = "1.0.0",

    /**
     * List of authors for the entity. Must have at least one author.
     * Required.
     */
    val authors: List<String>,

    /**
     * Optional copyright information for the entity.
     */
    val copyrightInformation: String? = null,

    /**
     * Optional contact information for the author(s) of the entity.
     */
    val contactInformation: String? = null,

    /**
     * Information about third-party licenses that apply to this entity.
     * Optional.
     */
    val thirdPartyLicenses: String? = null,

    /**
     * Advises users to exercise discretion when using this workflow or step.
     * Defaults to `false`.
     */
    val discretionAdvisedContent: Boolean = false,

    /**
     * Permits commercial use under defined terms:
     * - "personalNonProfit" (default): Personal, non-profit use only.
     * - "personalProfit": Personal use, regardless of profit.
     * - "corporation": Corporate use allowed.
     */
    val commercialUsage: CommercialUsageDto = CommercialUsageDto.PERSONAL_NON_PROFIT,

    /**
     * Defines credit display requirements:
     * - "required" (default): Credit must be displayed.
     * - "unnecessary": Credit display is not required.
     */
    val creditNotation: CreditNotationDto = CreditNotationDto.REQUIRED,

    /**
     * Allows or disallows redistribution of the entity.
     * Defaults to `false`.
     */
    val allowRedistribution: Boolean = false,

    /**
     * Defines modification permissions:
     * - "prohibited" (default): Modifications are not allowed.
     * - "allowModification": Modifications are allowed but cannot be redistributed.
     * - "allowModificationRedistribution": Modifications and redistribution allowed.
     */
    val modification: ModificationDto = ModificationDto.PROHIBITED,

    /**
     * Tracks the user's accepted distribution terms, including version number
     * and timestamps for initial acceptance and most recent update.
     */
    val distributionAgreement: DistributionAgreementDto = DistributionAgreementDto(),

    /**
     * Configuration for sharing the workflow or step, including whether
     * all steps are bundled by default and any step exclusions.
     */
    val sharingConfig: SharingConfigDto = SharingConfigDto()
) {

    companion object {
        val jsonConfig = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }

        val mock =  UserDesignedMetadataDto(
            name = "John Arbuckle",
            version = "1.0.0",
            authors = listOf("Garfield Cat", "Nermal"),
            copyrightInformation = "2025",
            contactInformation = "lasagnalonely@email.com",
            thirdPartyLicenses = "Ok",
            discretionAdvisedContent = true,
            commercialUsage = CommercialUsageDto.CORPORATION,
            creditNotation = CreditNotationDto.REQUIRED,
            allowRedistribution = false,
            modification = ModificationDto.PROHIBITED,
            distributionAgreement = DistributionAgreementDto(
                version = "1.0.0",
                acceptedAt = "",
                updatedAt = ""
            ),
            sharingConfig = SharingConfigDto(
                includeAllSteps = true,
                excludedSteps = emptyList()
            )
        )
    }
}

@Serializable
data class DistributionAgreementDto(
    val version: String = "1.0.0",
    val acceptedAt: String = Clock.System.now().toString(),
    val updatedAt: String = Clock.System.now().toString()
)
@Serializable
data class SharingConfigDto(
    val includeAllSteps: Boolean = true,
    val excludedSteps: List<Int> = emptyList()
)

@Serializable
enum class CommercialUsageDto {
    PERSONAL_NON_PROFIT, PERSONAL_PROFIT, CORPORATION
}

@Serializable
enum class CreditNotationDto {
    REQUIRED, UNNECESSARY
}

@Serializable
enum class ModificationDto {
    PROHIBITED, ALLOW_MODIFICATION, ALLOW_MODIFICATION_REDISTRIBUTION
}

@Serializable
data class ResourceUsageDto(
    val name: String? = null
) {
    companion object {
        val jsonConfig = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
}