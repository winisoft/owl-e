package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.DataPointSaveEntity
import com.wini.owle.repo.OwleRepoImpl

class RevisionRepository: OwleRepoImpl<Long, DataPointSaveEntity>(DataPointSaveEntity)