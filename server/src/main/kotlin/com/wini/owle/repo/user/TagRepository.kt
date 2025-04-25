package com.wini.owle.repo.user

import com.wini.owle.entity.user.tags.TagEntity
import com.wini.owle.repo.OwleRepoImpl

class TagRepository: OwleRepoImpl<Long, TagEntity>(TagEntity)