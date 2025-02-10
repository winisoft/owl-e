package com.wini.owle.mapper.enum

import com.wini.owle.dto.*
import com.wini.owle.entity.workflows.StoreMethod
import com.wini.owle.mapper.IModelEnumMapper
import com.wini.owle.models.*


sealed interface ModelEnumMapper<DTO, Model> : IModelEnumMapper<DTO, Model> {

    data object StoreMethodMapper: ModelEnumMapper<StoreMethodDto, StoreMethodModel> {

        override fun toDto(model: StoreMethodModel): StoreMethodDto {
            return when (model) {
                StoreMethodModel.Blob -> StoreMethodDto.Blob
                StoreMethodModel.File -> StoreMethodDto.File
                StoreMethodModel.Text -> StoreMethodDto.Text
            }
        }

        override fun toModel(dto: StoreMethodDto): StoreMethodModel {
            return when (dto) {
                StoreMethodDto.Blob -> StoreMethodModel.Blob
                StoreMethodDto.File -> StoreMethodModel.File
                StoreMethodDto.Text -> StoreMethodModel.Text
            }
        }
    }
    
    data object CardBrandMapper : ModelEnumMapper<CardBrandDto, CardBrandModel> {
        override fun toDto(model: CardBrandModel): CardBrandDto {
            return when (model) {
                CardBrandModel.AmericanExpress -> CardBrandDto.AmericanExpress
                CardBrandModel.DinersClub -> CardBrandDto.DinersClub
                CardBrandModel.Discover -> CardBrandDto.Discover
                CardBrandModel.EftposAustralia -> CardBrandDto.EftposAustralia
                CardBrandModel.Girocard -> CardBrandDto.Girocard
                CardBrandModel.Jcb -> CardBrandDto.Jcb
                CardBrandModel.Mastercard -> CardBrandDto.Mastercard
                CardBrandModel.UnionPay -> CardBrandDto.UnionPay
                CardBrandModel.Visa -> CardBrandDto.Visa
                CardBrandModel.Unknown -> CardBrandDto.Unknown
            }
        }

        override fun toModel(dto: CardBrandDto): CardBrandModel {
            return when (dto) {
                CardBrandDto.AmericanExpress -> CardBrandModel.AmericanExpress
                CardBrandDto.DinersClub -> CardBrandModel.DinersClub
                CardBrandDto.Discover -> CardBrandModel.Discover
                CardBrandDto.EftposAustralia -> CardBrandModel.EftposAustralia
                CardBrandDto.Girocard -> CardBrandModel.Girocard
                CardBrandDto.Jcb -> CardBrandModel.Jcb
                CardBrandDto.Mastercard -> CardBrandModel.Mastercard
                CardBrandDto.UnionPay -> CardBrandModel.UnionPay
                CardBrandDto.Visa -> CardBrandModel.Visa
                CardBrandDto.Unknown -> CardBrandModel.Unknown
            }
        }
    }

    data object ComparisonOperatorMapper : ModelEnumMapper<ComparisonOperatorDto, ComparisonOperatorModel> {
        override fun toModel(dto: ComparisonOperatorDto): ComparisonOperatorModel {
            return when (dto) {
                ComparisonOperatorDto.Equals -> ComparisonOperatorModel.Equals
                ComparisonOperatorDto.NotEquals -> ComparisonOperatorModel.NotEquals
                ComparisonOperatorDto.Contains -> ComparisonOperatorModel.Contains
                ComparisonOperatorDto.GreaterThan -> ComparisonOperatorModel.GreaterThan
                ComparisonOperatorDto.LessThan -> ComparisonOperatorModel.LessThan
            }
        }

        override fun toDto(model: ComparisonOperatorModel): ComparisonOperatorDto {
            return when (model) {
                ComparisonOperatorModel.Equals -> ComparisonOperatorDto.Equals
                ComparisonOperatorModel.NotEquals -> ComparisonOperatorDto.NotEquals
                ComparisonOperatorModel.Contains -> ComparisonOperatorDto.Contains
                ComparisonOperatorModel.GreaterThan -> ComparisonOperatorDto.GreaterThan
                ComparisonOperatorModel.LessThan -> ComparisonOperatorDto.LessThan
            }
        }
    }

    data object ExecutionStatusMapper : ModelEnumMapper<ExecutionStatusDto, ExecutionStatusModel> {
        override fun toModel(dto: ExecutionStatusDto): ExecutionStatusModel {
            return when (dto) {
                ExecutionStatusDto.Running -> ExecutionStatusModel.Running
                ExecutionStatusDto.Completed -> ExecutionStatusModel.Completed
                ExecutionStatusDto.Failed -> ExecutionStatusModel.Failed
                ExecutionStatusDto.Skipped -> ExecutionStatusModel.Skipped
            }
        }

        override fun toDto(model: ExecutionStatusModel): ExecutionStatusDto {
            return when (model) {
                ExecutionStatusModel.Running -> ExecutionStatusDto.Running
                ExecutionStatusModel.Completed -> ExecutionStatusDto.Completed
                ExecutionStatusModel.Failed -> ExecutionStatusDto.Failed
                ExecutionStatusModel.Skipped -> ExecutionStatusDto.Skipped
            }
        }
    }

    data object VarianceMapper : ModelEnumMapper<VarianceDto, VarianceModel> {
        override fun toModel(dto: VarianceDto): VarianceModel {
            return when (dto) {
                VarianceDto.Covariant -> VarianceModel.Covariant
                VarianceDto.Contravariant -> VarianceModel.Contravariant
                VarianceDto.Invariant -> VarianceModel.Invariant
            }
        }

        override fun toDto(model: VarianceModel): VarianceDto {
            return when (model) {
                VarianceModel.Covariant -> VarianceDto.Covariant
                VarianceModel.Contravariant -> VarianceDto.Contravariant
                VarianceModel.Invariant -> VarianceDto.Invariant
            }
        }
    }

    data object PackageRepoTypeMapper : ModelEnumMapper<PackageRepoTypeDto, PackageRepoTypeModel> {
        override fun toModel(dto: PackageRepoTypeDto): PackageRepoTypeModel {
            return when (dto) {
                PackageRepoTypeDto.Pypi -> PackageRepoTypeModel.Pypi
                PackageRepoTypeDto.Conda -> PackageRepoTypeModel.Conda
                PackageRepoTypeDto.Custom -> PackageRepoTypeModel.Custom
                PackageRepoTypeDto.Github -> PackageRepoTypeModel.Github
                PackageRepoTypeDto.Local -> PackageRepoTypeModel.Local
            }
        }

        override fun toDto(model: PackageRepoTypeModel): PackageRepoTypeDto {
            return when (model) {
                PackageRepoTypeModel.Pypi -> PackageRepoTypeDto.Pypi
                PackageRepoTypeModel.Conda -> PackageRepoTypeDto.Conda
                PackageRepoTypeModel.Custom -> PackageRepoTypeDto.Custom
                PackageRepoTypeModel.Github -> PackageRepoTypeDto.Github
                PackageRepoTypeModel.Local -> PackageRepoTypeDto.Local
            }
        }
    }

    data object SubscriptionStatusMapper : ModelEnumMapper<SubscriptionStatusDto, SubscriptionStatusModel> {
        override fun toModel(dto: SubscriptionStatusDto): SubscriptionStatusModel {
            return when (dto) {
                SubscriptionStatusDto.Registered -> SubscriptionStatusModel.Registered
                SubscriptionStatusDto.Active -> SubscriptionStatusModel.Active
                SubscriptionStatusDto.Cancelled -> SubscriptionStatusModel.Cancelled
                SubscriptionStatusDto.OnHold -> SubscriptionStatusModel.OnHold
            }
        }

        override fun toDto(model: SubscriptionStatusModel): SubscriptionStatusDto {
            return when (model) {
                SubscriptionStatusModel.Registered -> SubscriptionStatusDto.Registered
                SubscriptionStatusModel.Active -> SubscriptionStatusDto.Active
                SubscriptionStatusModel.Cancelled -> SubscriptionStatusDto.Cancelled
                SubscriptionStatusModel.OnHold -> SubscriptionStatusDto.OnHold
            }
        }
    }

    data object TransformOccurrenceMapper : ModelEnumMapper<TransformOccurrenceDto, TransformOccurrenceModel> {
        override fun toModel(dto: TransformOccurrenceDto): TransformOccurrenceModel {
            return when (dto) {
                TransformOccurrenceDto.Provision -> TransformOccurrenceModel.Provision
                TransformOccurrenceDto.Revision -> TransformOccurrenceModel.Revision
                TransformOccurrenceDto.Inquisition -> TransformOccurrenceModel.Inquisition
                TransformOccurrenceDto.PrereqInjection -> TransformOccurrenceModel.PrereqInjection
            }
        }

        override fun toDto(model: TransformOccurrenceModel): TransformOccurrenceDto {
            return when (model) {
                TransformOccurrenceModel.Provision -> TransformOccurrenceDto.Provision
                TransformOccurrenceModel.Revision -> TransformOccurrenceDto.Revision
                TransformOccurrenceModel.Inquisition -> TransformOccurrenceDto.Inquisition
                TransformOccurrenceModel.PrereqInjection -> TransformOccurrenceDto.PrereqInjection
            }
        }
    }

}