package com.wini.owle.repo.transforms

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.transforms.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


open class AppliedTransformArgRepositoryTest : DbLayerIntegrationTests<Long, AppliedTransformArgDao>() {

    private val realRepo by KoinJavaComponent.inject<AppliedTransformArgRepository>(AppliedTransformArgRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = listOf(
        AppliedTransformArgs,
        AppliedTransforms,
        Transformations,
        TypeDeclarations,
        TypeDefinitions,
        VersionSpecs,
        DataPoints,
        Workflows,
        Steps,
        Tools,
        TransformationArgs
    )

    override val testModule: Module
        get() = module {
            single<AppliedTransformArgRepository> { AppliedTransformArgRepository() }
        }

    override fun getInstance(): AppliedTransformArgDao {
        val typeDefinition = EntityTestInstanceFactory.typeDefinitionDao()
        val resultTypeDef = EntityTestInstanceFactory.typeDefinitionDao()
        val resultType = typeDeclarationDao(typeDefinition = resultTypeDef)
        val transformationReturnsType = typeDeclarationDao(typeDefinition = typeDefinition)
        val transformation = TransformationDao.new {
            this.name = "Example Transformation"
            this.description = "Transforms data"
            this.examples = "Input -> Output"
            this.returnsTypeDeclaration = transformationReturnsType
        }
        val appliedTransform = AppliedTransformDao.new {
            this.transformation = transformation
            this.occurrence = TransformOccurrence.PREREQINJECTION
            this.resultType = resultType
            this.dataPoint = dataPoint ?: EntityTestInstanceFactory.dataPointDao()
        }
        val transformArg = TransformationArgDao.new {
            this.transformation = transformation
            this.typeDeclaration = transformationReturnsType
            this.name = "Argument Name"
            this.description = "Argument Description"
        }

        val appliedTransformArg = AppliedTransformArgDao.new {
            this.appliedTransform = appliedTransform
            this.transformationArg = transformArg
            this.value = "exampleArgumentValue"
        }

        return appliedTransformArg
    }

}