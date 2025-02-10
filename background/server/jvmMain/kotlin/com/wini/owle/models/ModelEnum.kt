package com.wini.owle.models

import kotlinx.serialization.Serializable

@Serializable
sealed class StoreMethodModel(val id: String) {
    
    @Serializable data object Text: StoreMethodModel("TEXT")
    @Serializable data object Blob: StoreMethodModel("BLOB")
    @Serializable data object File: StoreMethodModel("FILE")
}


@Serializable
sealed class CardBrandModel(val id: String) {
    @Serializable data object AmericanExpress : CardBrandModel("AMERICAN_EXPRESS")
    @Serializable data object DinersClub : CardBrandModel("DINERS_CLUB")
    @Serializable data object Discover : CardBrandModel("DISCOVER")
    @Serializable data object EftposAustralia : CardBrandModel("EFTPOS_AUSTRALIA")
    @Serializable data object Girocard : CardBrandModel("GIROCARD")
    @Serializable data object Jcb : CardBrandModel("JCB")
    @Serializable data object Mastercard : CardBrandModel("MASTERCARD")
    @Serializable data object UnionPay : CardBrandModel("UNIONPAY")
    @Serializable data object Visa : CardBrandModel("VISA")
    @Serializable data object Unknown : CardBrandModel("UNKNOWN")
}

@Serializable
sealed class ComparisonOperatorModel(val id: String) {
    @Serializable data object Equals : ComparisonOperatorModel("EQUALS")
    @Serializable data object NotEquals : ComparisonOperatorModel("NOT_EQUALS")
    @Serializable data object Contains : ComparisonOperatorModel("CONTAINS")
    @Serializable data object GreaterThan : ComparisonOperatorModel("GREATER_THAN")
    @Serializable data object LessThan : ComparisonOperatorModel("LESS_THAN")
}

@Serializable
sealed class ExecutionStatusModel(val id: String) {
    @Serializable data object Running : ExecutionStatusModel("RUNNING")
    @Serializable data object Completed : ExecutionStatusModel("COMPLETED")
    @Serializable data object Failed : ExecutionStatusModel("FAILED")
    @Serializable data object Skipped : ExecutionStatusModel("SKIPPED")
}

@Serializable
sealed class GenerationMethodModel(val id: String) {
    @Serializable data object Manual : GenerationMethodModel("MANUAL")
    @Serializable data object AiGenerated : GenerationMethodModel("AI_GENERATED")
}

@Serializable
sealed class LogLevelModel(val id: String) {
    @Serializable data object Debug : LogLevelModel("DEBUG")
    @Serializable data object Info : LogLevelModel("INFO")
    @Serializable data object Warning : LogLevelModel("WARNING")
    @Serializable data object Error : LogLevelModel("ERROR")
    @Serializable data object Critical : LogLevelModel("CRITICAL")
}

@Serializable
sealed class SubscriptionStatusModel(val id: String) {
    @Serializable data object Registered : SubscriptionStatusModel("REGISTERED")
    @Serializable data object Active : SubscriptionStatusModel("ACTIVE")
    @Serializable data object Cancelled : SubscriptionStatusModel("CANCELLED")
    @Serializable data object OnHold : SubscriptionStatusModel("ON_HOLD")
}

@Serializable
sealed class TransformOccurrenceModel(val id: String) {
    @Serializable data object Provision : TransformOccurrenceModel("PROVISION")
    @Serializable data object Revision : TransformOccurrenceModel("REVISION")
    @Serializable data object Inquisition : TransformOccurrenceModel("INQUISITION")
    @Serializable data object PrereqInjection : TransformOccurrenceModel("PREREQ_INJECTION")
}

@Serializable
sealed class VarianceModel(val id: String) {
    @Serializable data object Covariant : VarianceModel("COVARIANT")
    @Serializable data object Contravariant : VarianceModel("CONTRAVARIANT")
    @Serializable data object Invariant : VarianceModel("INVARIANT")
}

@Serializable
sealed class PackageRepoTypeModel(val id: String) {
    @Serializable data object Pypi : PackageRepoTypeModel("PYPI")
    @Serializable data object Conda : PackageRepoTypeModel("CONDA")
    @Serializable data object Custom : PackageRepoTypeModel("CUSTOM")
    @Serializable data object Github : PackageRepoTypeModel("GITHUB")
    @Serializable data object Local : PackageRepoTypeModel("LOCAL")
}