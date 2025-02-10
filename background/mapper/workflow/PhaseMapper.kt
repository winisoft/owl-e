package com.wini.owle.mapper.workflow

import com.wini.owle.dto.PhaseDto
import com.wini.owle.entity.workflows.PhaseEntity
import com.wini.owle.mapper.BaseDbMapper

sealed class PhaseMapper : BaseDbMapper<PhaseDto, PhaseEntity>(){

}