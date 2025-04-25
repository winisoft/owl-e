package com.wini.owle.repo.user

import com.wini.owle.entity.user.tags.TagRelationEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.CompositeID

class TagRelationRepository: OwleRepoImpl<CompositeID, TagRelationEntity>(TagRelationEntity)