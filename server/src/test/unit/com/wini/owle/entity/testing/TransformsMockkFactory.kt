package com.wini.owle.entity.testing

import com.wini.owle.dto.transforms.enums.TransformOccurrenceInitiator
import com.wini.owle.entity.InstanceMockEntityFactory
import com.wini.owle.entity.InstanceMockEntityFactory.typeNodeDao
import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.workflows.DataPointEntity
import io.mockk.every
import io.mockk.mockk


/**
 * Canned Entity mockk instances for tests.
 *
 * One factory per entity package, composed by [EntityMockkFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object TransformsMockkFactory {

    fun transformDao(
        name: String = "Example Transform",
        description: String = "Transforms data",
        examples: String? = "Input -> Output"
    ): TransformEntity = mockk(relaxed = true) {
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.examples } returns examples
    }

    /**
     * The source is exactly one of [dataPoint] or a tool result — the table's own CHECK enforces
     * it — so a caller that cares should pass one and leave the other null.
     */
    fun transformOccurrence(
        transform: TransformEntity = InstanceMockEntityFactory.transformDao(),
        initiator: TransformOccurrenceInitiator = TransformOccurrenceInitiator.DATA_POINT_INJECTION,
        dataPoint: DataPointEntity? = null
    ): TransformOccurrenceEntity = mockk(relaxed = true) {
        every { this@mockk.transform } returns transform
        every { this@mockk.initiator } returns initiator
        every { this@mockk.dataPoint } returns dataPoint
    }

    fun transformArg(
        transform: TransformEntity = InstanceMockEntityFactory.transformDao(),
        typeNode: TypeNodeEntity = typeNodeDao(),
        name: String = "Argument Name",
        description: String? = "Argument Description"
    ): TransformArgEntity = mockk(relaxed = true) {
        every { this@mockk.transform } returns transform
        every { this@mockk.typeNode } returns typeNode
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
    }
}
