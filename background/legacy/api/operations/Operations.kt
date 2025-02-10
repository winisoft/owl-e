package com.wini.owle.air.api.operations

import kotlinx.serialization.Serializable

@Serializable
class Operations(val items: List<OperationsItem>): List<OperationsItem> by items