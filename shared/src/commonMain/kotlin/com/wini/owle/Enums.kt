package com.wini.owle
import com.wini.owle.dto.enums.CardBrand as CardBrandDto
import com.wini.owle.dto.enums.CardBrand.*
import com.wini.owle.dto.enums.ComparisonOperator as ComparisonOperatorDto
import com.wini.owle.dto.enums.ComparisonOperator.*
import com.wini.owle.dto.enums.ExecutionStatus as ExecutionStatusDto
import com.wini.owle.dto.enums.ExecutionStatus.*
import com.wini.owle.dto.enums.GenerationMethod as GenerationMethodDto
import com.wini.owle.dto.enums.LogLevel as LogLevelDto
import com.wini.owle.dto.enums.LogLevel.*
import com.wini.owle.dto.enums.GenerationMethod.*
import com.wini.owle.dto.enums.StoreMethod as StoreMethodDto
import com.wini.owle.dto.enums.StoreMethod.*
import com.wini.owle.dto.enums.VarianceCardinality as VarianceCardinalityDto
import com.wini.owle.dto.enums.VarianceCardinality.*
import com.wini.owle.dto.enums.PackageRepoType as PackageRepoTypeDto
import com.wini.owle.dto.enums.PackageRepoType.*
import com.wini.owle.dto.enums.SubscriptionStatus as SubscriptionStatusDto
import com.wini.owle.dto.enums.TransformOccurrence.*
import com.wini.owle.dto.enums.TransformOccurrence as TransformOccurrenceDto
import com.wini.owle.dto.enums.SubscriptionStatus.Registered
import com.wini.owle.dto.enums.SubscriptionStatus.Cancelled
import com.wini.owle.dto.enums.SubscriptionStatus.Active
import com.wini.owle.dto.enums.SubscriptionStatus.OnHold

//enum class CardBrand {
//    AMERICAN_EXPRESS,
//    DINERS_CLUB,
//    DISCOVER,
//    EFTPOS_AUSTRALIA,
//    GIROCARD,
//    JCB,
//    MASTERCARD,
//    UNIONPAY,
//    VISA,
//    UNKNOWN;
//
//    companion object {
//
//        fun fromDto(dto: CardBrandDto): CardBrand {
//            return when (dto) {
//                AmericanExpress -> AMERICAN_EXPRESS
//                DinersClub -> DINERS_CLUB
//                Discover -> DISCOVER
//                EftposAustralia -> EFTPOS_AUSTRALIA
//                Girocard -> GIROCARD
//                Jcb -> JCB
//                Mastercard -> MASTERCARD
//                UnionPay -> UNIONPAY
//                Visa -> VISA
//                Unknown -> UNKNOWN
//            }
//        }
//
//        fun CardBrand.toDto(): CardBrandDto {
//            return when (this) {
//                AMERICAN_EXPRESS -> AmericanExpress
//                DINERS_CLUB -> DinersClub
//                DISCOVER -> Discover
//                EFTPOS_AUSTRALIA -> EftposAustralia
//                GIROCARD -> Girocard
//                JCB -> Jcb
//                MASTERCARD -> Mastercard
//                UNIONPAY -> UnionPay
//                VISA -> Visa
//                UNKNOWN -> Unknown
//            }
//        }
//    }
//}
//
//enum class ExecutionStatus {
//    RUNNING, COMPLETED, FAILED, SKIPPED, CANCELLED;
//
//    companion object {
//
//        fun fromDto(dto: ExecutionStatusDto): ExecutionStatus {
//            return when (dto) {
//                Running -> RUNNING
//                Completed -> COMPLETED
//                Failed -> FAILED
//                Skipped -> SKIPPED
//                ExecutionStatusDto.Cancelled -> CANCELLED
//            }
//        }
//
//        fun ExecutionStatus.toDto(): ExecutionStatusDto {
//            return when (this) {
//                RUNNING -> Running
//                COMPLETED -> Completed
//                FAILED -> Failed
//                SKIPPED -> Skipped
//                CANCELLED -> ExecutionStatusDto.Cancelled
//            }
//        }
//    }
//}
//
//enum class LogLevel {
//    DEBUG, INFO, WARNING, ERROR, CRITICAL;
//
//    companion object {
//        fun fromDto(dto: LogLevelDto): LogLevel {
//            return when (dto) {
//                Debug -> DEBUG
//                Info -> INFO
//                Warning -> WARNING
//                Error -> ERROR
//                Critical -> CRITICAL
//            }
//        }
//        fun LogLevel.toDto(): LogLevelDto {
//            return when (this) {
//                DEBUG -> Debug
//                INFO -> Info
//                WARNING -> Warning
//                ERROR -> Error
//                CRITICAL -> Critical
//            }
//        }
//    }
//}
//
//enum class GenerationMethod {
//    MANUAL, AI_GENERATED;
//
//    companion object {
//        fun fromDto(dto: GenerationMethodDto): GenerationMethod {
//            return when (dto) {
//                Manual -> MANUAL
//                AiGenerated -> AI_GENERATED
//            }
//        }
//        fun GenerationMethod.toDto(): GenerationMethodDto {
//            return when (this) {
//                MANUAL -> Manual
//                AI_GENERATED -> AiGenerated
//            }
//        }
//    }
//}
//
//enum class ComparisonOperator {
//    EQUALS, NOT_EQUALS, CONTAINS, GREATER_THAN, LESS_THAN;
//
//    companion object {
//        fun fromDto(dto: ComparisonOperatorDto) : ComparisonOperator {
//            return when (dto) {
//                Contains -> CONTAINS
//                Equals -> EQUALS
//                GreaterThan -> GREATER_THAN
//                LessThan -> LESS_THAN
//                NotEquals -> NOT_EQUALS
//            }
//        }
//        fun ComparisonOperator.toDto(): ComparisonOperatorDto {
//            return when (this) {
//                EQUALS -> Equals
//                NOT_EQUALS -> NotEquals
//                CONTAINS -> Contains
//                GREATER_THAN -> GreaterThan
//                LESS_THAN -> LessThan
//            }
//        }
//    }
//}
//
//enum class VarianceCardinality {
//    COVARIANT, CONTRAVARIANT, INVARIANT;
//
//    companion object {
//        fun fromDto(dto: VarianceCardinalityDto): VarianceCardinality {
//            return when (dto) {
//                Covariant -> COVARIANT
//                Contravariant -> CONTRAVARIANT
//                Invariant -> INVARIANT
//            }
//        }
//        fun VarianceCardinality.toDto(): VarianceCardinalityDto {
//            return when (this) {
//                COVARIANT -> Covariant
//                CONTRAVARIANT -> Contravariant
//                INVARIANT -> Invariant
//            }
//        }
//    }
//}
//
//enum class StoreMethod {
//    TEXT, BLOB, FILE;
//
//    companion object {
//        fun fromDto(dto: StoreMethodDto): StoreMethod {
//            return when (dto) {
//                Text -> TEXT
//                Blob -> BLOB
//                File -> FILE
//            }
//        }
//        fun StoreMethod.toDto(): StoreMethodDto {
//            return when (this) {
//                TEXT -> StoreMethodDto.Text
//                BLOB -> StoreMethodDto.Blob
//                FILE -> StoreMethodDto.File
//            }
//        }
//    }
//}
//
//enum class PackageRepoType {
//    PYPI, CONDA, CUSTOM, GITHUB, LOCAL;
//
//    companion object {
//        fun fromDto(dto: PackageRepoTypeDto): PackageRepoType {
//            return when (dto) {
//                Pypi -> PYPI
//                Conda -> CONDA
//                Custom -> CUSTOM
//                Github -> GITHUB
//                Local -> LOCAL
//            }
//        }
//        fun PackageRepoType.toDto(): PackageRepoTypeDto {
//            return when (this) {
//                PYPI -> Pypi
//                CONDA -> Conda
//                CUSTOM -> Custom
//                GITHUB -> Github
//                LOCAL -> Local
//            }
//        }
//    }
//}
//
//enum class TransformOccurrence {
//    PROVISION, REVISION, INQUISITION, PREREQINJECTION;
//
//    companion object {
//        fun fromDto(dto: TransformOccurrenceDto): TransformOccurrence {
//            return when (dto) {
//                Provision -> PROVISION
//                Revision -> REVISION
//                Inquisition -> INQUISITION
//                PrereqInjection -> PREREQINJECTION
//            }
//        }
//        fun TransformOccurrence.toDto(): TransformOccurrenceDto {
//            return when (this) {
//                PROVISION -> Provision
//                REVISION -> Revision
//                INQUISITION -> Inquisition
//                PREREQINJECTION -> PrereqInjection
//            }
//        }
//    }
//}
//
//enum class SubscriptionStatus {
//    REGISTERED, ACTIVE, CANCELLED, ON_HOLD;
//
//    companion object {
//        fun fromDto(dto: SubscriptionStatusDto): SubscriptionStatus {
//            return when (dto) {
//                Registered -> REGISTERED
//                Active -> ACTIVE
//                Cancelled -> CANCELLED
//                OnHold -> ON_HOLD
//            }
//        }
//        fun SubscriptionStatus.toDto(): SubscriptionStatusDto {
//            return when(this){
//                SubscriptionStatus.REGISTERED -> SubscriptionStatusDto.Registered
//                SubscriptionStatus.ACTIVE -> SubscriptionStatusDto.Active
//                SubscriptionStatus.CANCELLED -> SubscriptionStatusDto.Cancelled
//                SubscriptionStatus.ON_HOLD -> SubscriptionStatusDto.OnHold
//            }
//        }
//    }
//}
