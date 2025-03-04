package com.wini.owle.entity

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.dao.id.CompositeIdTable
import org.jetbrains.exposed.sql.Table


object TableRegistry {

    private val longIdTableMap: MutableMap<String, LongIdTable> = mutableMapOf()
    private val compositeIdTableMap: MutableMap<String, CompositeIdTable> = mutableMapOf()
    
    fun registerTables() {
        registerLongIdTable("VersionSpecsIdConverter", VersionSpecs)
        registerLongIdTable("PackagesIdConverter", Packages)
        registerLongIdTable("TypeDefinitionsIdConverter", TypeDefinitions)
        registerLongIdTable("TypeDeclarationsIdConverter", TypeDeclarations)
        registerLongIdTable("ProvisionsIdConverter", Provisions)
        registerLongIdTable("TypeParametersIdConverter", TypeParameters)
        registerLongIdTable("TypeBoundsIdConverter", TypeBounds)
        registerLongIdTable("TypeVarianceIdConverter", TypeVariances)
        registerLongIdTable("TypeSchemaRefsIdConverter", TypeSchemaRefs)
        registerLongIdTable("WorkflowFoldersIdConverter", WorkflowFolders)
        registerLongIdTable("WorkflowsIdConverter", Workflows)
        registerLongIdTable("ToolsIdConverter", Tools)
        registerLongIdTable("ToolParamsIdConverter", ToolParams)
        registerLongIdTable("PhasesIdConverter", Phases)
        registerLongIdTable("StepsIdConverter", Steps)
        registerLongIdTable("DataPointsIdConverter", DataPoints)
        registerLongIdTable("PackageRepositoriesIdConverter", PackageRepositories)
        registerLongIdTable("TransformationsIdConverter", Transformations)
        registerLongIdTable("TransformationArgsIdConverter", TransformationArgs)
        registerLongIdTable("AppliedTransformsIdConverter", AppliedTransforms)
        registerLongIdTable("AppliedTransformArgsIdConverter", AppliedTransformArgs)
        registerLongIdTable("ErrorWatchesIdConverter", ErrorWatches)
        registerLongIdTable("ErrorWatchFnsIdConverter", ErrorWatchFns)
        registerLongIdTable("InquisitionsIdConverter", Inquisitions)
        registerLongIdTable("RevisionsIdConverter", Revisions)
        registerLongIdTable("WorkflowJunctionsIdConverter", WorkflowJunctions)
        registerLongIdTable("PrereqInjectionsIdConverter", PrereqInjections)
        registerLongIdTable("UsersIdConverter", Users)
        registerLongIdTable("PaymentMethodsIdConverter", PaymentMethods)
        registerLongIdTable("TagsIdConverter", Tags)
        registerLongIdTable("WorkflowExecutionsIdConverter", WorkflowExecutions)
        registerLongIdTable("StepExecutionsIdConverter", StepExecutions)
        registerLongIdTable("ExecutionLogsIdConverter", ExecutionLogs)
        registerLongIdTable("BenchmarkingDataIdConverter", BenchmarkingData)
        registerLongIdTable("StepBenchmarkingDataIdConverter", StepBenchmarkingDatas)
        registerLongIdTable("TestCasesIdConverter", TestCases)
        registerLongIdTable("TestInputsIdConverter", TestInputs)
        registerLongIdTable("TestExpectedOutcomesIdConverter", TestExpectedOutcomes)
        registerLongIdTable("TestResultsIdConverter", TestResults)
        registerCompositeIdTable("TypeUnionMembersIdConverter", TypeUnionMembers)
        registerCompositeIdTable("ToolPackageDependenciesIdConverter", ToolPackageDependencies)
        registerCompositeIdTable("TagRelationsIdConverter", TagRelations)
        registerCompositeIdTable("TypeUnionMembersIdConverter", TypeUnionMembers)
        registerCompositeIdTable("TransformTargetTypesIdConverter", TransformTargetTypes)
        registerCompositeIdTable("TagRelationsIdConverter", TagRelations)
        registerCompositeIdTable("ProvisionDataPointsIdConverter", ProvisionDataPoints)
    }

    private fun registerLongIdTable(entityName: String, table: LongIdTable) {
        longIdTableMap[entityName] = table
    }

    private fun registerCompositeIdTable(entityName: String, table: CompositeIdTable) {
        compositeIdTableMap[entityName] = table
    }

    fun getLongIdTable(entityName: String): LongIdTable? {
        return longIdTableMap[entityName]
    }

    fun getCompositeIdTable(entityName: String): CompositeIdTable? {
        return compositeIdTableMap[entityName]
    }

    private fun getTable(entityName: String): Table? {
        return longIdTableMap[entityName] ?: compositeIdTableMap[entityName]
    }
}
