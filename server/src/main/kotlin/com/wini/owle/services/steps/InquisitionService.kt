package com.wini.owle.services.steps

import com.wini.owle.dto.steps.InquisitionDto
import com.wini.owle.entity.steps.InquisitionDao
import com.wini.owle.mapper.steps.InquisitionsMapper
import com.wini.owle.repo.steps.InquisitionRepository
import com.wini.owle.services.BaseServiceImpl


class InquisitionService(
    repository: InquisitionRepository,
    mapper: InquisitionsMapper
): BaseServiceImpl<Long, InquisitionDto, InquisitionDao>(repository, mapper)