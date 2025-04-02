package com.wini.owle.models.metadata

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable


@Serializable
data class DistributionAgreement(
    val version: String = "1.0.0",
    val acceptedAt: String = Clock.System.now().toString(),
    val updatedAt: String = Clock.System.now().toString()
)