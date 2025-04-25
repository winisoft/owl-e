package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.repo.OwleRepoImpl

class TransformationRepository: OwleRepoImpl<Long, TransformEntity>(TransformEntity)