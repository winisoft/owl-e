package com.wini.owle.dto.user.metadata

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
data class DistributionAgreementDto(
    val version: String = "1.0.0",
    val acceptedAt: String = Clock.System.now().toString(),
    val updatedAt: String = Clock.System.now().toString()
)