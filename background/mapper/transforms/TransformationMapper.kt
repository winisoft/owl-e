package com.wini.owle.mapper.transforms

import com.wini.owle.dto.TransformationDto
import com.wini.owle.entity.transforms.TransformationEntity
import com.wini.owle.mapper.BaseDbMapper

sealed class TransformationMapper : BaseDbMapper<TransformationDto, TransformationEntity>() {

    }