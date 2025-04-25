package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.AppliedTransformEntity
import com.wini.owle.repo.OwleRepoImpl

class AppliedTransformRepository: OwleRepoImpl<Long, AppliedTransformEntity>(AppliedTransformEntity)