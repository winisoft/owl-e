package com.wini.owle.models.workflows

import com.wini.owle.dto.workflows.enums.StoreMethod
import kotlinx.serialization.Serializable


@Serializable
data class DataPointModel(
    val id: Long,
    val workflowId: Long,
    val name: String,
    val presetValue: String?,
    val storeMethod: StoreMethod
) {

    val isReadOnly: Boolean
        get() = presetValue?.let{ true } ?: false

}


