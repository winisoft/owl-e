package com.wini.owle.dto.enums

import kotlinx.serialization.Serializable

@Serializable
sealed class StoreMethod(val id: String) {
    
    @Serializable
    data object Text: StoreMethod("TEXT")

    @Serializable
    data object Blob: StoreMethod("BLOB")

    @Serializable
    data object File: StoreMethod("FILE")

}

@Serializable
sealed class CardBrand(val id: String) {

    @Serializable
    data object AmericanExpress : CardBrand("AMERICAN_EXPRESS")

    @Serializable
    data object DinersClub : CardBrand("DINERS_CLUB")

    @Serializable
    data object Discover : CardBrand("DISCOVER")

    @Serializable
    data object EftposAustralia : CardBrand("EFTPOS_AUSTRALIA")

    @Serializable
    data object Girocard : CardBrand("GIROCARD")

    @Serializable
    data object Jcb : CardBrand("JCB")

    @Serializable
    data object Mastercard : CardBrand("MASTERCARD")

    @Serializable
    data object UnionPay : CardBrand("UNIONPAY")

    @Serializable
    data object Visa : CardBrand("VISA")

    @Serializable
    data object Unknown : CardBrand("UNKNOWN")
}

@Serializable
sealed class ComparisonOperator(val id: String) {

    @Serializable
    data object Equals : ComparisonOperator("EQUALS")

    @Serializable
    data object NotEquals : ComparisonOperator("NOT_EQUALS")

    @Serializable
    data object Contains : ComparisonOperator("CONTAINS")

    @Serializable
    data object GreaterThan : ComparisonOperator("GREATER_THAN")

    @Serializable
    data object LessThan : ComparisonOperator("LESS_THAN")
}

@Serializable
sealed class ExecutionStatus(val id: String) {

    @Serializable
    data object Running : ExecutionStatus("RUNNING")

    @Serializable
    data object Completed : ExecutionStatus("COMPLETED")

    @Serializable
    data object Failed : ExecutionStatus("FAILED")

    @Serializable
    data object Skipped : ExecutionStatus("SKIPPED")

    @Serializable
    data object Cancelled : ExecutionStatus("CANCELLED")
}

@Serializable
sealed class GenerationMethod(val id: String) {

    @Serializable
    data object Manual : GenerationMethod("MANUAL")

    @Serializable
    data object AiGenerated : GenerationMethod("AI_GENERATED")
}

@Serializable
sealed class LogLevel(val id: String) {

    @Serializable
    data object Debug : LogLevel("DEBUG")

    @Serializable
    data object Info : LogLevel("INFO")

    @Serializable
    data object Warning : LogLevel("WARNING")

    @Serializable
    data object Error : LogLevel("ERROR")

    @Serializable
    data object Critical : LogLevel("CRITICAL")
}

@Serializable
sealed class SubscriptionStatus(val id: String) {

    @Serializable
    data object Registered : SubscriptionStatus("REGISTERED")

    @Serializable
    data object Active : SubscriptionStatus("ACTIVE")

    @Serializable
    data object Cancelled : SubscriptionStatus("CANCELLED")

    @Serializable
    data object OnHold : SubscriptionStatus("ON_HOLD")
}

@Serializable
sealed class TransformOccurrence(val id: String) {

    @Serializable
    data object Provision : TransformOccurrence("PROVISION")

    @Serializable
    data object Revision : TransformOccurrence("REVISION")

    @Serializable
    data object Inquisition : TransformOccurrence("INQUISITION")

    @Serializable
    data object PreReqInjection : TransformOccurrence("PREREQ_INJECTION")
}

@Serializable
sealed class VarianceCardinality(val id: String) {

    @Serializable
    data object Covariant : VarianceCardinality("COVARIANT")

    @Serializable
    data object Contravariant : VarianceCardinality("CONTRAVARIANT")

    @Serializable
    data object Invariant : VarianceCardinality("INVARIANT")
}

@Serializable
sealed class PackageRepoType(val id: String) {

    @Serializable
    data object Pypi : PackageRepoType("PYPI")

    @Serializable
    data object Conda : PackageRepoType("CONDA")

    @Serializable
    data object Custom : PackageRepoType("CUSTOM")

    @Serializable
    data object Github : PackageRepoType("GITHUB")

    @Serializable
    data object Local : PackageRepoType("LOCAL")
}

