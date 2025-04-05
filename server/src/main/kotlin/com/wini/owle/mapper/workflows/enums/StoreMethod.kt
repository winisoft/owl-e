package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.StoreMethodDto
import com.wini.owle.entity.workflows.enums.StoreMethod

class StoreMethodMapper {

    fun toEntity(dto: StoreMethodDto): StoreMethod {
        return when (dto) {
            StoreMethodDto.Text -> StoreMethod.TEXT
            StoreMethodDto.Blob -> StoreMethod.BLOB
            StoreMethodDto.File -> StoreMethod.FILE
        }
    }

    fun toDto(entity: StoreMethod): StoreMethodDto {
        return when (entity) {
            StoreMethod.TEXT -> StoreMethodDto.Text
            StoreMethod.BLOB -> StoreMethodDto.Blob
            StoreMethod.FILE -> StoreMethodDto.File
        }
    }
}