package com.wini.owle.mapper.enum

import com.wini.owle.dto.*
import com.wini.owle.entity.environs.PackageRepoType
import com.wini.owle.entity.runs.ComparisonOperator
import com.wini.owle.entity.runs.ExecutionStatus
import com.wini.owle.entity.runs.LogLevel
import com.wini.owle.entity.transforms.TransformOccurrence
import com.wini.owle.entity.typing.Variance
import com.wini.owle.entity.user.CardBrand
import com.wini.owle.entity.user.SubscriptionStatus
import com.wini.owle.entity.workflows.StoreMethod
import com.wini.owle.entity.runs.TestInputEntity
import com.wini.owle.mapper.IDbEnumMapper


sealed interface DbEnumMapper<DtoEnum, EntityEnum>: IDbEnumMapper<DtoEnum, EntityEnum> {

    data object StoreMethodMapper: DbEnumMapper<StoreMethodDto, StoreMethod> {
        override fun toEntity(dto: StoreMethodDto): StoreMethod {
            return when (dto) {
                StoreMethodDto.Blob -> StoreMethod.BLOB
                StoreMethodDto.File -> StoreMethod.FILE
                StoreMethodDto.Text -> StoreMethod.TEXT
            }
        }

        override fun toDto(entity: StoreMethod): StoreMethodDto {
            return when (entity) {
                StoreMethod.BLOB -> StoreMethodDto.Blob
                StoreMethod.FILE -> StoreMethodDto.File
                StoreMethod.TEXT -> StoreMethodDto.Text
            }
        }
    }

    data object CardBrandMapper: DbEnumMapper<CardBrandDto, CardBrand> {

        override fun toEntity(dto: CardBrandDto): CardBrand {
            return when (dto) {
                CardBrandDto.AmericanExpress -> CardBrand.AMERICAN_EXPRESS
                CardBrandDto.DinersClub -> CardBrand.DINERS_CLUB
                CardBrandDto.Discover -> CardBrand.DISCOVER
                CardBrandDto.EftposAustralia -> CardBrand.EFTPOS_AUSTRALIA
                CardBrandDto.Girocard -> CardBrand.GIROCARD
                CardBrandDto.Jcb -> CardBrand.JCB
                CardBrandDto.Mastercard -> CardBrand.MASTERCARD
                CardBrandDto.UnionPay -> CardBrand.UNIONPAY
                CardBrandDto.Visa -> CardBrand.VISA
                CardBrandDto.Unknown -> CardBrand.UNKNOWN
                else -> throw IllegalArgumentException("Attempted map of unknown type  ${dto::class.simpleName}")
            }
        }

        override fun toDto(entity: CardBrand): CardBrandDto {
            return when (entity) {
                CardBrand.AMERICAN_EXPRESS -> CardBrandDto.AmericanExpress
                CardBrand.DINERS_CLUB -> CardBrandDto.DinersClub
                CardBrand.DISCOVER -> CardBrandDto.Discover
                CardBrand.EFTPOS_AUSTRALIA -> CardBrandDto.EftposAustralia
                CardBrand.GIROCARD -> CardBrandDto.Girocard
                CardBrand.JCB -> CardBrandDto.Jcb
                CardBrand.MASTERCARD -> CardBrandDto.Mastercard
                CardBrand.UNIONPAY -> CardBrandDto.UnionPay
                CardBrand.VISA -> CardBrandDto.Visa
                CardBrand.UNKNOWN -> CardBrandDto.Unknown
            }
        }
    }

    data object ComparisonOperatorMapper : DbEnumMapper<ComparisonOperatorDto, ComparisonOperator> {
        override fun toEntity(dto: ComparisonOperatorDto): ComparisonOperator {
            return when (dto) {
                ComparisonOperatorDto.Equals -> ComparisonOperator.EQUALS
                ComparisonOperatorDto.NotEquals -> ComparisonOperator.NOT_EQUALS
                ComparisonOperatorDto.Contains -> ComparisonOperator.CONTAINS
                ComparisonOperatorDto.GreaterThan -> ComparisonOperator.GREATER_THAN
                ComparisonOperatorDto.LessThan -> ComparisonOperator.LESS_THAN
                else -> throw IllegalArgumentException("Attempted map of unknown type ${dto::class.simpleName}")
            }
        }

        override fun toDto(entity: ComparisonOperator): ComparisonOperatorDto {
            return when (entity) {
                ComparisonOperator.EQUALS -> ComparisonOperatorDto.Equals
                ComparisonOperator.NOT_EQUALS -> ComparisonOperatorDto.NotEquals
                ComparisonOperator.CONTAINS -> ComparisonOperatorDto.Contains
                ComparisonOperator.GREATER_THAN -> ComparisonOperatorDto.GreaterThan
                ComparisonOperator.LESS_THAN -> ComparisonOperatorDto.LessThan
            }
        }
    }

    data object ExecutionStatusMapper : DbEnumMapper<ExecutionStatusDto, ExecutionStatus> {
        override fun toEntity(dto: ExecutionStatusDto): ExecutionStatus {
            return when (dto) {
                ExecutionStatusDto.Running -> ExecutionStatus.RUNNING
                ExecutionStatusDto.Completed -> ExecutionStatus.COMPLETED
                ExecutionStatusDto.Failed -> ExecutionStatus.FAILED
                ExecutionStatusDto.Skipped -> ExecutionStatus.SKIPPED
                else -> throw IllegalArgumentException("Attempted map of unknown type ${dto::class.simpleName}")
            }
        }

        override fun toDto(entity: ExecutionStatus): ExecutionStatusDto {
            return when (entity) {
                ExecutionStatus.RUNNING -> ExecutionStatusDto.Running
                ExecutionStatus.COMPLETED -> ExecutionStatusDto.Completed
                ExecutionStatus.FAILED -> ExecutionStatusDto.Failed
                ExecutionStatus.SKIPPED -> ExecutionStatusDto.Skipped
            }
        }
    }

    data object VarianceMapper : DbEnumMapper<VarianceDto, Variance> {
        override fun toEntity(dto: VarianceDto): Variance {
            return when (dto) {
                VarianceDto.Covariant -> Variance.COVARIANT
                VarianceDto.Contravariant -> Variance.CONTRAVARIANT
                VarianceDto.Invariant -> Variance.INVARIANT
                else -> throw IllegalArgumentException("Unknown type: ${dto::class.simpleName}")
            }
        }

        override fun toDto(entity: Variance): VarianceDto {
            return when (entity) {
                Variance.COVARIANT -> VarianceDto.Covariant
                Variance.CONTRAVARIANT -> VarianceDto.Contravariant
                Variance.INVARIANT -> VarianceDto.Invariant
            }
        }
    }

    data object PackageRepoTypeMapper : DbEnumMapper<PackageRepoTypeDto, PackageRepoType> {
        override fun toEntity(dto: PackageRepoTypeDto): PackageRepoType {
            return when (dto) {
                PackageRepoTypeDto.Pypi -> PackageRepoType.PYPI
                PackageRepoTypeDto.Conda -> PackageRepoType.CONDA
                PackageRepoTypeDto.Custom -> PackageRepoType.CUSTOM
                PackageRepoTypeDto.Github -> PackageRepoType.GITHUB
                PackageRepoTypeDto.Local -> PackageRepoType.LOCAL
                else -> throw IllegalArgumentException("Unknown type: ${dto::class.simpleName}")
            }
        }

        override fun toDto(entity: PackageRepoType): PackageRepoTypeDto {
            return when (entity) {
                PackageRepoType.PYPI -> PackageRepoTypeDto.Pypi
                PackageRepoType.CONDA -> PackageRepoTypeDto.Conda
                PackageRepoType.CUSTOM -> PackageRepoTypeDto.Custom
                PackageRepoType.GITHUB -> PackageRepoTypeDto.Github
                PackageRepoType.LOCAL -> PackageRepoTypeDto.Local
            }
        }
    }

    data object SubscriptionStatusMapper : DbEnumMapper<SubscriptionStatusDto, SubscriptionStatus> {
        override fun toEntity(dto: SubscriptionStatusDto): SubscriptionStatus {
            return when (dto) {
                SubscriptionStatusDto.Registered -> SubscriptionStatus.REGISTERED
                SubscriptionStatusDto.Active -> SubscriptionStatus.ACTIVE
                SubscriptionStatusDto.Cancelled -> SubscriptionStatus.CANCELLED
                SubscriptionStatusDto.OnHold -> SubscriptionStatus.ON_HOLD
                else -> throw IllegalArgumentException("Unknown type: ${dto::class.simpleName}")
            }
        }

        override fun toDto(entity: SubscriptionStatus): SubscriptionStatusDto {
            return when (entity) {
                SubscriptionStatus.REGISTERED -> SubscriptionStatusDto.Registered
                SubscriptionStatus.ACTIVE -> SubscriptionStatusDto.Active
                SubscriptionStatus.CANCELLED -> SubscriptionStatusDto.Cancelled
                SubscriptionStatus.ON_HOLD -> SubscriptionStatusDto.OnHold
            }
        }
    }

    data object LogLevelMapper : DbEnumMapper<LogLevelDto, LogLevel> {
        override fun toEntity(dto: LogLevelDto): LogLevel {
            return when (dto) {
                LogLevelDto.Debug -> LogLevel.DEBUG
                LogLevelDto.Info -> LogLevel.INFO
                LogLevelDto.Warning -> LogLevel.WARNING
                LogLevelDto.Error -> LogLevel.ERROR
                LogLevelDto.Critical -> LogLevel.CRITICAL
                else -> throw IllegalArgumentException("Unknown type: ${dto::class.simpleName}")
            }
        }

        override fun toDto(entity: LogLevel): LogLevelDto {
            return when (entity) {
                LogLevel.DEBUG -> LogLevelDto.Debug
                LogLevel.INFO -> LogLevelDto.Info
                LogLevel.WARNING -> LogLevelDto.Warning
                LogLevel.ERROR -> LogLevelDto.Error
                LogLevel.CRITICAL -> LogLevelDto.Critical
            }
        }
    }

    data object TransformOccurrenceMapper : DbEnumMapper<TransformOccurrenceDto, TransformOccurrence> {
        override fun toEntity(dto: TransformOccurrenceDto): TransformOccurrence {
            return when (dto) {
                TransformOccurrenceDto.Provision -> TransformOccurrence.PROVISION
                TransformOccurrenceDto.Revision -> TransformOccurrence.REVISION
                TransformOccurrenceDto.Inquisition -> TransformOccurrence.INQUISITION
                TransformOccurrenceDto.PrereqInjection -> TransformOccurrence.PREREQ_INJECTION
                else -> throw IllegalArgumentException("Unknown type: ${dto::class.simpleName}")
            }
        }

        override fun toDto(entity: TransformOccurrence): TransformOccurrenceDto {
            return when (entity) {
                TransformOccurrence.PROVISION -> TransformOccurrenceDto.Provision
                TransformOccurrence.REVISION -> TransformOccurrenceDto.Revision
                TransformOccurrence.INQUISITION -> TransformOccurrenceDto.Inquisition
                TransformOccurrence.PREREQ_INJECTION -> TransformOccurrenceDto.PrereqInjection
            }
        }
    }

}
    data object ComparisonOperatorMapper : DbEnumMapper<ComparisonOperatorDto, ComparisonOperator> {
        override fun toEntity(dto: ComparisonOperatorDto): ComparisonOperator {
            return when (dto) {
                ComparisonOperatorDto.Equals -> ComparisonOperator.EQUALS
                ComparisonOperatorDto.NotEquals -> ComparisonOperator.NOT_EQUALS
                ComparisonOperatorDto.Contains -> ComparisonOperator.CONTAINS
                ComparisonOperatorDto.GreaterThan -> ComparisonOperator.GREATER_THAN
                ComparisonOperatorDto.LessThan -> ComparisonOperator.LESS_THAN
                else -> throw IllegalArgumentException("Unknown type: ${dto::class.simpleName}")
            }
        }

        override fun toDto(entity: ComparisonOperator): ComparisonOperatorDto {
            return when (entity) {
                ComparisonOperator.EQUALS -> ComparisonOperatorDto.Equals
                ComparisonOperator.NOT_EQUALS -> ComparisonOperatorDto.NotEquals
                ComparisonOperator.CONTAINS -> ComparisonOperatorDto.Contains
                ComparisonOperator.GREATER_THAN -> ComparisonOperatorDto.GreaterThan
                ComparisonOperator.LESS_THAN -> ComparisonOperatorDto.LessThan
            }
        }
    }
    data object GenerationMethodMapper : DbEnumMapper<GenerationMethodDto, TestInputEntity.GenerationMethod> {
        override fun toEntity(dto: GenerationMethodDto): TestInputEntity.GenerationMethod {
            return when (dto) {
                GenerationMethodDto.Manual -> TestInputEntity.GenerationMethod.MANUAL
                GenerationMethodDto.AiGenerated -> TestInputEntity.GenerationMethod.AI_GENERATED
                else -> throw IllegalArgumentException("Unknown type: ${dto::class.simpleName}")
            }
        }

        override fun toDto(entity: TestInputEntity.GenerationMethod): GenerationMethodDto {
            return when (entity) {
                TestInputEntity.GenerationMethod.MANUAL -> GenerationMethodDto.Manual
                TestInputEntity.GenerationMethod.AI_GENERATED -> GenerationMethodDto.AiGenerated
            }
        }
    }
