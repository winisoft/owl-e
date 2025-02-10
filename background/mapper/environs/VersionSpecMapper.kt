package com.wini.owle.mapper.environs

import com.wini.owle.dto.VersionSpecDto
import com.wini.owle.entity.environs.VersionSpecEntity
import com.wini.owle.mapper.BaseDbMapper

sealed class VersionSpecMapper : BaseDbMapper<VersionSpecDto, VersionSpecEntity>() {

}