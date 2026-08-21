package com.wini.owle

import com.wini.owle.entity.workflows.WorkflowFolderDbIntegrationTest
import com.wini.owle.service.workflows.WorkflowFolderServiceTest
import com.wini.owle.service.workflows.WorkflowHierarchyServiceImplTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag


/**
 * Everything that needs a live `owle_test` behind it.
 *
 * Abstract on purpose: only [OwleTest] instantiates it, so each test underneath runs exactly once.
 * A concrete class here would be discovered on its own as well and every test would report twice.
 * The same rule applies all the way down — the classes nested below are abstract for the same
 * reason, which is also what leaves them free to grow tests specific to their own subject.
 */
@Tag("integration")
@DisplayName("Integration")
abstract class OwleIntegrationTestSuite {

    @Nested
    @DisplayName("Query execution")
    inner class QueryExecution : com.wini.owle.query.QueryExecutionTest()

    @Nested
    @DisplayName("Enum column drift")
    inner class EnumColumnDrift : com.wini.owle.entity.EnumColumnDriftTest()

    @Nested
    @DisplayName("Workflow Folder (entity)")
    inner class WorkflowFolderEntityTests : WorkflowFolderDbIntegrationTest()

    @Nested
    @DisplayName("Workflow Folder (service)")
    inner class WorkflowFolderServiceTests : WorkflowFolderServiceTest()

    @Nested
    @DisplayName("Workflow Hierarchy (service)")
    inner class WorkflowHierarchyServiceTests : WorkflowHierarchyServiceImplTest()
}
