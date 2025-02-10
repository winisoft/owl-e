package com.wini.owle.mapper

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.DataPointDto
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.mapper.enum.DbEnumMapper
import com.wini.owle.mapper.enum.ModelEnumMapper
import com.wini.owle.models.workflows.DataPointModel


sealed class ModelMapper<DTO: BaseDto, T> : IModelMapper<DTO, T> {

    data object DataPointMapper : ModelMapper<DataPointDto, DataPointModel>() {

        override fun toDto(model: DataPointModel): DataPointDto {
            throw NotImplementedError()
        }

        override fun toModel(dto: DataPointDto): DataPointModel {
            throw NotImplementedError()
        }


    }
}