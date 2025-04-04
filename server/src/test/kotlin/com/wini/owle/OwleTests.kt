package com.wini.owle

import com.wini.owle.mapper.MapperTests
import com.wini.owle.repo.DatabaseIntegrationTests
import com.wini.owle.repo.RepositoryTest
import com.wini.owle.services.ServiceTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


class OwleTest {


    @Nested
    @DisplayName("Repository Tests")
    inner class RepositoryTests: RepositoryTest()

//    @Nested
//    @DisplayName("Dto Serialization Tests")
//    inner class DtoSerializationTests {
//        @TestFactory
//        fun `test Dto serialization - `() = listOf(
//            "PackageRepoDto" to DtoTestInstanceFactory.createDtoInstance(PackageRepoDto::class),
//            "PackageDto" to DtoTestInstanceFactory.createDtoInstance(PackageDto::class),
//            "VersionSpecDto" to DtoTestInstanceFactory.createDtoInstance(VersionSpecDto::class),
//
//            "BenchmarkingDataDto" to DtoTestInstanceFactory.createDtoInstance(BenchmarkingDataDto::class),
//            "ExecutionLogDto" to DtoTestInstanceFactory.createDtoInstance(ExecutionLogDto::class),
//            "TestCaseDto" to DtoTestInstanceFactory.createDtoInstance(TestCaseDto::class),
//            "TestInputDto" to DtoTestInstanceFactory.createDtoInstance(TestInputDto::class),
//            "TestExpectedOutcomeDto" to DtoTestInstanceFactory.createDtoInstance(TestExpectedOutcomeDto::class),
//            "TestResultDto" to DtoTestInstanceFactory.createDtoInstance(TestResultDto::class),
//
//            "ProvisionDataPointDto" to DtoTestInstanceFactory.createDtoInstance(ProvisionDataPointDto::class),
//            "ErrorWatchDto" to DtoTestInstanceFactory.createDtoInstance(ErrorWatchDto::class),
//            "ErrorWatchFnDto" to DtoTestInstanceFactory.createDtoInstance(ErrorWatchFnDto::class),
//            "InquisitionDto" to DtoTestInstanceFactory.createDtoInstance(InquisitionDto::class),
//            "PreReqInjectionDto" to DtoTestInstanceFactory.createDtoInstance(PreReqInjectionDto::class),
//            "ProvisionDto" to DtoTestInstanceFactory.createDtoInstance(ProvisionDto::class),
//            "RevisionDto" to DtoTestInstanceFactory.createDtoInstance(RevisionDto::class),
//
//            "TypeDeclarationDto" to DtoTestInstanceFactory.createDtoInstance(TypeDeclarationDto::class),
//            "TypeDefinitionDto" to DtoTestInstanceFactory.createDtoInstance(TypeDefinitionDto::class),
//            "TypeParameterDto" to DtoTestInstanceFactory.createDtoInstance(TypeParameterDto::class),
//            "TypeSchemaRefDto" to DtoTestInstanceFactory.createDtoInstance(TypeSchemaRefDto::class),
//            "TypeUnionMemberDto" to DtoTestInstanceFactory.createDtoInstance(TypeUnionMemberDto::class),
//            "TypeVarianceDto" to DtoTestInstanceFactory.createDtoInstance(TypeVarianceDto::class),
//
//            "UserDto" to DtoTestInstanceFactory.createDtoInstance(UserDto::class),
//            "PaymentMethodDto" to DtoTestInstanceFactory.createDtoInstance(PaymentMethodDto::class),
//            "TagDto" to DtoTestInstanceFactory.createDtoInstance(TagDto::class),
//            "TagRelationDto" to DtoTestInstanceFactory.createDtoInstance(TagRelationDto::class),
//
//            "DataPointDto" to DtoTestInstanceFactory.createDtoInstance(DataPointDto::class),
//            "PhaseDto" to DtoTestInstanceFactory.createDtoInstance(PhaseDto::class),
//            "ToolDto" to DtoTestInstanceFactory.createDtoInstance(ToolDto::class),
//            "ToolParamDto" to DtoTestInstanceFactory.createDtoInstance(ToolParamDto::class),
//            "StepDto" to DtoTestInstanceFactory.createDtoInstance(StepDto::class),
//            "WorkflowDto" to DtoTestInstanceFactory.createDtoInstance(WorkflowDto::class),
//            "WorkflowFolderDto" to DtoTestInstanceFactory.createDtoInstance(WorkflowFolderDto::class)
//        ).map { (dtoName, dtoInstance) ->
//            DynamicTest.dynamicTest("Round-trip serialization - $dtoName") {
//                val json = polymorphicJson.encodeToString(BaseDto.serializer(), dtoInstance)
//                val deserialized = polymorphicJson.decodeFromString(BaseDto.serializer(), json)
//                assertEquals(dtoInstance, deserialized, "Round-trip serialization failed for $dtoName")
//            }
//        }
//    }

    @Nested
    @DisplayName("Mapper Tests")
    inner class MappingTests: MapperTests()

    @Nested
    @DisplayName("Service Layer Tests")
    inner class ServiceLayer: ServiceTest()


}