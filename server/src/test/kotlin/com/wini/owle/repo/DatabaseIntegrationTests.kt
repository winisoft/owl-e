package com.wini.owle.repo

import com.wini.owle.repo.environs.VersionSpecDbIntegrationTest
import com.wini.owle.repo.runs.StepExecutionDbIntegrationTest
import com.wini.owle.repo.runs.WorkflowExecutionDbIntegrationTest
import com.wini.owle.repo.steps.*
import com.wini.owle.repo.typing.*
import com.wini.owle.repo.workflows.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


class DatabaseIntegrationTests {

    @Nested
    @DisplayName("Cascade Behavior")
    inner class CascadeBehaviorTest {

        @Nested
        @DisplayName("VersionSpec")
        inner class VersionSpecDbIntegrationTests: VersionSpecDbIntegrationTest()

        @Nested
        @DisplayName("StepExecution")
        inner class StepExecutionDbIntegrationTests: StepExecutionDbIntegrationTest()

        @Nested
        @DisplayName("WorkflowExecution")
        inner class WorkflowExecutionDbIntegrationTests: WorkflowExecutionDbIntegrationTest()

        @Nested
        @DisplayName("ErrorWatchFn")
        inner class ErrorWatchEntityFnDbIntegrationTests: ErrorWatchEntityFnDbIntegrationTest()

        @Nested
        @DisplayName("ErrorWatch")
        inner class ErrorWatchEntityDbIntegrationTests: ErrorWatchEntityDbIntegrationTest()

        @Nested
        @DisplayName("Inquisition")
        inner class InquisitionEntityDbIntegrationTests: InquisitionEntityDbIntegrationTest()

        @Nested
        @DisplayName("PreReqInjection")
        inner class PreReqInjectionDbIntegrationTests: PreReqInjectionDbIntegrationTest()

        @Nested
        @DisplayName("Provision")
        inner class ProvisionDbIntegrationTests: ProvisionDbIntegrationTest()

        @Nested
        @DisplayName("Revision")
        inner class RevisionDbIntegrationTests: RevisionDbIntegrationTest()

        @Nested
        @DisplayName("WorkflowJunction")
        inner class WorkflowJunctionDbIntegrationTests: WorkflowJunctionDbIntegrationTest()

        @Nested
        @DisplayName("TypeBound")
        inner class TypeBoundEntityDbIntegrationTests: TypeBoundEntityDbIntegrationTest()

        @Nested
        @DisplayName("TypeDeclaration")
        inner class TypeDeclarationEntityDbIntegrationTests: TypeDeclarationEntityDbIntegrationTest()

        @Nested
        @DisplayName("TypeDefinition")
        inner class TypeDefinitionEntityDbIntegrationTests: TypeDefinitionEntityDbIntegrationTest()

        @Nested
        @DisplayName("TypeParameter")
        inner class TypeParameterDbIntegrationTests: TypeParameterDbIntegrationTest()

        @Nested
        @DisplayName("TypeSchemaRef")
        inner class TypeSchemaRefDbIntegrationTests: TypeSchemaRefDbIntegrationTest()

        @Nested
        @DisplayName("DataPoint")
        inner class DataPointEntityDbIntegrationTests: DataPointEntityDbIntegrationTest()

        @Nested
        @DisplayName("Phase")
        inner class PhaseDbIntegrationTests: PhaseDbIntegrationTest()

        @Nested
        @DisplayName("Step")
        inner class StepDbIntegrationTests: StepDbIntegrationTest()

        @Nested
        @DisplayName("ToolParamD")
        inner class ToolParamDbIntegrationTests: ToolParamDbIntegrationTest()

        @Nested
        @DisplayName("WorkflowFolder")
        inner class WorkflowFolderDbIntegrationTests: WorkflowFolderDbIntegrationTest()
    }
}