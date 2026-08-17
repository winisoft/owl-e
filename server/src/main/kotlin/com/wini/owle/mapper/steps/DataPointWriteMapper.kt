package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.DataPointWrite
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.steps.DataPointWriteEntity
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointMapper
import com.wini.owle.mapper.workflows.StepsMapper


class DataPointWriteMapper(
    private val daoDelegate: DaoDelegate<ULong, DataPointWrite, DataPointWriteEntity>,
    private val stepMapper: StepsMapper,
    private val dataPointMapper: DataPointMapper
): DbMapper<ULong, DataPointWrite, DataPointWriteEntity> {

    override fun toEntity(dto: DataPointWrite): DataPointWriteEntity {
        return daoDelegate.findOrCreate(dto) {

        }
    }

    override fun toDto(entity: DataPointWriteEntity): DataPointWrite {
        return DataPointWrite(
            id = 1UL,
            step = stepMapper.toDto(entity.step)
        )
    }

}