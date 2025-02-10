package com.wini.owle.services.steps

import com.wini.owle.dto.ProvisionDto
import com.wini.owle.entity.steps.ProvisionDao
import com.wini.owle.mapper.steps.ProvisionsMapper
import com.wini.owle.repo.steps.ProvisionRepository
import com.wini.owle.services.BaseServiceImpl


class ProvisionService(
    repository: ProvisionRepository,
    mapper: ProvisionsMapper
): BaseServiceImpl<Long, ProvisionDto, ProvisionDao>(repository, mapper)