package com.wini.owle.dto

import com.wini.owle.dto.enums.ComparisonOperator
import com.wini.owle.dto.enums.GenerationMethod
import com.wini.owle.dto.enums.PackageRepoType
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import com.wini.owle.serialization.polymorphicJson
import com.wini.owle.dto.enums.*
import com.wini.owle.dto.enums.SubscriptionStatus
import com.wini.owle.dto.enums.CardBrand
//
//class DynamicDtoTests {
//
//    @TestFactory
//    fun `test polymorphic serialization and deserialization for all BaseDto subclasses`() = listOf(
//        "PackageRepoDto" to DtoTestInstanceFactory.createDtoInstance(PackageRepoDto::class.java),
//        "VersionSpecDto" to DtoTestInstanceFactory.createDtoInstance(VersionSpecDto::class.java),
//        "StepDto" to DtoTestInstanceFactory.createDtoInstance(StepDto::class.java),
//        "WorkflowDto" to DtoTestInstanceFactory.createDtoInstance(WorkflowDto::class.java),
//        "TestCaseDto" to DtoTestInstanceFactory.createDtoInstance(TestCaseDto::class.java),
//        "TestInputDto" to DtoTestInstanceFactory.createDtoInstance(TestInputDto::class.java),
//        "TestExpectedOutcomeDto" to DtoTestInstanceFactory.createDtoInstance(TestExpectedOutcomeDto::class.java),
//        "TestResultDto" to DtoTestInstanceFactory.createDtoInstance(TestResultDto::class.java),
//        "BenchmarkingDataDto" to DtoTestInstanceFactory.createDtoInstance(BenchmarkingDataDto::class.java),
//        "ExecutionLogDto" to DtoTestInstanceFactory.createDtoInstance(ExecutionLogDto::class.java),
//        "ErrorWatchDto" to DtoTestInstanceFactory.createDtoInstance(ErrorWatchDto::class.java),
//        "ErrorWatchFnDto" to DtoTestInstanceFactory.createDtoInstance(ErrorWatchFnDto::class.java),
//        "InquisitionDto" to DtoTestInstanceFactory.createDtoInstance(InquisitionDto::class.java),
//        "PreReqInjectionDto" to DtoTestInstanceFactory.createDtoInstance(PreReqInjectionDto::class.java),
//        "ProvisionDto" to DtoTestInstanceFactory.createDtoInstance(ProvisionDto::class.java),
//        "RevisionDto" to DtoTestInstanceFactory.createDtoInstance(RevisionDto::class.java),
//        "TypeDeclarationDto" to DtoTestInstanceFactory.createDtoInstance(TypeDeclarationDto::class.java),
//        "TypeDefinitionDto" to DtoTestInstanceFactory.createDtoInstance(TypeDefinitionDto::class.java),
//        "TypeParameterDto" to DtoTestInstanceFactory.createDtoInstance(TypeParameterDto::class.java),
//        "TypeSchemaRefDto" to DtoTestInstanceFactory.createDtoInstance(TypeSchemaRefDto::class.java),
//        "TypeUnionMemberDto" to DtoTestInstanceFactory.createDtoInstance(TypeUnionMemberDto::class.java),
//        "TypeVarianceDto" to DtoTestInstanceFactory.createDtoInstance(TypeVarianceDto::class.java),
//        "UserDto" to DtoTestInstanceFactory.createDtoInstance(UserDto::class.java),
//        "PaymentMethodDto" to DtoTestInstanceFactory.createDtoInstance(PaymentMethodDto::class.java),
//        "TagDto" to DtoTestInstanceFactory.createDtoInstance(TagDto::class.java),
//        "TagRelationDto" to DtoTestInstanceFactory.createDtoInstance(TagRelationDto::class.java),
//        "DataPointDto" to DtoTestInstanceFactory.createDtoInstance(DataPointDto::class.java),
//        "PhaseDto" to DtoTestInstanceFactory.createDtoInstance(PhaseDto::class.java),
//        "ProvisionDataPointDto" to DtoTestInstanceFactory.createDtoInstance(ProvisionDataPointDto::class.java),
//        "ToolDto" to DtoTestInstanceFactory.createDtoInstance(ToolDto::class.java),
//        "ToolParamDto" to DtoTestInstanceFactory.createDtoInstance(ToolParamDto::class.java),
//        "WorkflowFolderDto" to DtoTestInstanceFactory.createDtoInstance(WorkflowFolderDto::class.java)
//    ).map { (dtoName, dtoInstance) ->
//        DynamicTest.dynamicTest("Round-trip serialization for $dtoName") {
//            val json = polymorphicJson.encodeToString(BaseDto.serializer(), dtoInstance)
//            val deserialized = polymorphicJson.decodeFromString(BaseDto.serializer(), json)
//            assertEquals(dtoInstance, deserialized, "Round-trip serialization failed for $dtoName")
//        }
//    }
//}
