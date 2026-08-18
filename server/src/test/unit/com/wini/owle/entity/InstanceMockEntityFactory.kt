package com.wini.owle.entity

import com.wini.owle.dto.transforms.enums.TransformOccurrenceInitiator
import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Instant


/**
 * Mocked entities for tests that need an instance without a database behind it.
 *
 * This was 41 factory functions over 603 lines. Six of them were reachable from anything that
 * actually ran; the other 35 stubbed properties on types that had since been renamed or dropped
 * (`PhaseEntity`, `ToolParamEntity`, `StepRunEntity`) and were the source of most of the module's
 * compile errors. Only the reachable six survive.
 *
 * The repository tests do not use this at all — they take `mockk(relaxed = true)` directly, since
 * what they assert is which calls the repository makes, not what the entity holds. Reach for this
 * only when a test needs an entity whose properties it will actually read.
 *
 * Every mock is relaxed, so a property this file does not stub still answers with a default
 * rather than throwing. Stub a property here only when its value matters to a caller.
 */
object InstanceMockEntityFactory {

    fun transformDao(
        name: String = "Example Transform",
        description: String = "Transforms data",
        examples: String? = "Input -> Output"
    ): TransformEntity = mockk(relaxed = true) {
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.examples } returns examples
    }

    fun typeNodeDao(
        namespace: String? = "builtins",
        name: String? = "List",
        alias: String? = "CustomType"
    ): TypeNodeEntity = mockk(relaxed = true) {
        every { this@mockk.namespace } returns namespace
        every { this@mockk.name } returns name
        every { this@mockk.alias } returns alias
    }

    /**
     * The source is exactly one of [dataPoint] or a tool result — the table's own CHECK enforces
     * it — so a caller that cares should pass one and leave the other null.
     */
    fun transformOccurrence(
        transform: TransformEntity = transformDao(),
        initiator: TransformOccurrenceInitiator = TransformOccurrenceInitiator.DATA_POINT_INJECTION,
        dataPoint: DataPointEntity? = null
    ): TransformOccurrenceEntity = mockk(relaxed = true) {
        every { this@mockk.transform } returns transform
        every { this@mockk.initiator } returns initiator
        every { this@mockk.dataPoint } returns dataPoint
    }

    fun transformationArg(
        transform: TransformEntity = transformDao(),
        typeNode: TypeNodeEntity = typeNodeDao(),
        name: String = "Argument Name",
        description: String? = "Argument Description"
    ): TransformArgEntity = mockk(relaxed = true) {
        every { this@mockk.transform } returns transform
        every { this@mockk.typeNode } returns typeNode
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
    }

    fun workflowFolderDao(
        parentFolder: WorkflowFolderEntity? = null,
        name: String = "ExampleFolder",
        description: String? = "Folder description",
        createdAt: Instant = mockk(relaxed = true),
        updatedAt: Instant = mockk(relaxed = true)
    ): WorkflowFolderEntity = mockk(relaxed = true) {
        every { this@mockk.parentFolder } returns parentFolder
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }
}
