package com.wini.owle.mapper.workflow

import com.wini.owle.dto.DataPointDto
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.mapper.BaseDbMapper

sealed class DataPointMapper : BaseDbMapper<DataPointDto, DataPointEntity>() {

}