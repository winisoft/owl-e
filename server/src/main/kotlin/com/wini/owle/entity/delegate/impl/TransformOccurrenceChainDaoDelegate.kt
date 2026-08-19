package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.transforms.TransformOccurrenceChain
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.transforms.TransformOccurrenceChainEntity
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


object TransformOccurrenceChainDaoDelegate: CompositeIdDaoDelegate<
        TransformOccurrenceChain,
        TransformOccurrenceChainEntity,
        >(TransformOccurrenceChainEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: TransformOccurrenceChain): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}