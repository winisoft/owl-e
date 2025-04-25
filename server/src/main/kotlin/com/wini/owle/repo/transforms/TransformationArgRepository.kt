package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.repo.OwleRepoImpl

class TransformationArgRepository: OwleRepoImpl<Long, TransformArgEntity>(TransformArgEntity)