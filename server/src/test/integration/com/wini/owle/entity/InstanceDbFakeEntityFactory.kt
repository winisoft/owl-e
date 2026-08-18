package com.wini.owle.entity

import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.schema.Schema
import kotlinx.datetime.Instant


/**
 * Real rows, for integration tests that need something actually in the database.
 *
 * Call these inside a transaction, against `owle_test`, after [com.wini.owle.TestHelper.resetAll].
 * Unlike [InstanceMockEntityFactory] these insert — a fixture here has to satisfy every foreign
 * key and NOT NULL the schema declares, which is exactly why it is worth having.
 *
 * This was 45 functions; three were reachable from anything that ran. The rest built rows for
 * tables that no longer exist. They are not worth carrying: a fixture nobody calls is a fixture
 * nobody has checked against the current schema, and it will be wrong by the time someone needs
 * it. Add one back when a test actually needs it, and it will be right because the test proves it.
 */
object InstanceDbFakeEntityFactory {

    fun versionEntity(
        id: ULong = 1UL,
        epoch: ULong = 0UL,
        major: ULong = 3UL,
        minor: ULong = 12UL,
        micro: ULong = 0UL,
        phase: PrereleasePhase = PrereleasePhase.FINAL,
        phaseNumber: ULong? = null,
        postNumber: ULong? = null,
        devNumber: ULong? = null,
        local: String? = null,
        hasOverflow: Boolean = false
    ): VersionEntity = VersionEntity.new(id) {
        this.epoch = epoch
        this.major = major
        this.minor = minor
        this.micro = micro
        this.phase = phase
        this.phaseNumber = phaseNumber
        this.postNumber = postNumber
        this.devNumber = devNumber
        this.local = local
        this.hasOverflow = hasOverflow
    }

    /**
     * `python_versions` reuses its parent `versions` id rather than generating its own, so the
     * [version] row must exist first.
     */
    fun pythonVersionEntity(
        version: VersionEntity = versionEntity()
    ): PythonVersionEntity = PythonVersionEntity.new(version.id.value) {}

    fun workflowEntity(
        id: ULong = 1UL,
        name: String = "Example Workflow",
        description: String? = "A sample workflow description",
        pythonVersion: () -> PythonVersionEntity = { pythonVersionEntity() },
        parentFolder: () -> WorkflowFolderEntity? = { null },
        createdAt: Instant = Schema.Utils.instant,
        updatedAt: Instant = Schema.Utils.instant
    ): WorkflowEntity = WorkflowEntity.new(id) {
        this.name = name
        this.description = description
        this.pythonVersion = pythonVersion()
        this.parentFolder = parentFolder()
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}
