package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ProvisionDataPointEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.CompositeID


class ProvisionDataPointRepository: OwleRepoImpl<CompositeID, ProvisionDataPointEntity>(ProvisionDataPointEntity)