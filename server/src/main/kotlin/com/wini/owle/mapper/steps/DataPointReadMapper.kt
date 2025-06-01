package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.DataPointRead
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.steps.DataPointReadEntity
import com.wini.owle.mapper.DbMapper


class DataPointReadMapper(
    private val daoDelegate: DaoDelegate<ULong, DataPointRead, DataPointReadEntity>,
): DbMapper<ULong, DataPointRead, DataPointReadEntity> {

    override fun toEntity(dto: DataPointRead): DataPointReadEntity {
        return daoDelegate.findOrCreate(dto) {

        }
    }

    override fun toDto(entity: DataPointReadEntity): DataPointRead {
        return DataPointRead(
            id = entity.id.value
        )
    }
}