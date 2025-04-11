package com.wini.owle.dto.workflows

import kotlinx.serialization.Serializable

@Serializable
sealed class StoreMethodDto(val id: String) {

    @Serializable
    data object Text: StoreMethodDto("TEXT")

    @Serializable
    data object Blob: StoreMethodDto("BLOB")

    @Serializable
    data object File: StoreMethodDto("FILE")

}