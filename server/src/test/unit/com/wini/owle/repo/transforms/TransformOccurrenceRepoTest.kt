package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents an instance of an applied transformation.
 * Each applied transformation has a type, may recursively reference a previous applied transform,
 * and specifies the result type it produces.
 */
abstract class TransformOccurrenceRepoTest : ULongIdGenericRepoTest<TransformOccurrenceEntity>() {

    override fun mockEntity(): TransformOccurrenceEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TransformOccurrenceEntity {
        return any<TransformOccurrenceEntity>()
    }

    

}

