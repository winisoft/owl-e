package com.wini.owle.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class DecimalValue(val value: String)

typealias TimeStamp = Instant
