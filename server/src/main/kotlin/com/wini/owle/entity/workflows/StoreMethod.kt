package com.wini.owle.entity.workflows

import com.wini.owle.dto.workflows.StoreMethodDto

enum class StoreMethod {
    TEXT, BLOB, FILE;

    companion object {
        fun fromDto(dto: StoreMethodDto): StoreMethod {
            return when (dto) {
                StoreMethodDto.Text -> TEXT
                StoreMethodDto.Blob -> BLOB
                StoreMethodDto.File -> FILE
            }
        }
        fun StoreMethod.toDto(): StoreMethodDto {
            return when (this) {
                TEXT -> StoreMethodDto.Text
                BLOB -> StoreMethodDto.Blob
                FILE -> StoreMethodDto.File
            }
        }
    }
}