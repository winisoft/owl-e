package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.entity.workflows.enums.StoreMethodEntity

class StoreMethodMapper {

    fun toEntity(dto: StoreMethod): StoreMethodEntity {
        return when (dto) {
            StoreMethod.TEXT -> StoreMethodEntity.TEXT
            StoreMethod.BLOB -> StoreMethodEntity.BLOB
            StoreMethod.FILE -> StoreMethodEntity.FILE
        }
    }

    fun toDto(entity: StoreMethodEntity): StoreMethod {
        return when (entity) {
            StoreMethodEntity.TEXT -> StoreMethod.TEXT
            StoreMethodEntity.BLOB -> StoreMethod.BLOB
            StoreMethodEntity.FILE -> StoreMethod.FILE
        }
    }
}