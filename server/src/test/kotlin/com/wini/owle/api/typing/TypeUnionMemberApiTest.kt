package com.wini.owle.api.typing

import com.wini.owle.api.ApiError
import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.entity.TypeUnionMembers
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import com.wini.owle.validators.DtoValidator
import kotlinx.serialization.json.Json


open class TypeUnionMemberApiTest : BaseCompositePkApiTest<TypeUnionMemberDto, TypeUnionMemberApi>() {

    override val route: String = "/api/type-union-member"

    override val malformedParamsRoute: String
        get() = "$route/id?unionTypeDeclarationId=Text&memberTypeDeclarationId=Text"

    override fun getDto(id1: Long, id2: Long): TypeUnionMemberDto = DtoTestInstanceFactory.typeUnionMemberDto(
        unionTypeDeclarationId = id1,
        memberTypeDeclarationId = id2
    )

    override fun compositeId1(): CompositeID {
        return CompositeID {
            it[TypeUnionMembers.unionTypeDeclaration] = 1
            it[TypeUnionMembers.memberTypeDeclaration] = 1
        }
    }

    override fun compositeId2(): CompositeID {
        return CompositeID {
            it[TypeUnionMembers.unionTypeDeclaration] = 2
            it[TypeUnionMembers.memberTypeDeclaration] = 2
        }
    }

    override val serializer: KSerializer<TypeUnionMemberDto> = TypeUnionMemberDto.serializer()
    
    override val listSerializer: KSerializer<List<TypeUnionMemberDto>> = ListSerializer(TypeUnionMemberDto.serializer())

    override fun getApiInstance(
        dll: DLL<CompositeID, TypeUnionMemberDto>,
        validator: DtoValidator<TypeUnionMemberDto>
    ): TypeUnionMemberApi = TypeUnionMemberApi(dll, validator)

    override fun missingParamErrors(): List<ApiError> {
        return listOf(
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("unionTypeDeclarationId")),
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("memberTypeDeclarationId"))
        )
    }

    override fun improperParameters(): List<ApiError> {
        return listOf(
            ApiError.fromAsyncError(AsyncError.ApiRequestError.ParamValueMalformedLong("unionTypeDeclarationId")),
            ApiError.fromAsyncError(AsyncError.ApiRequestError.ParamValueMalformedLong("memberTypeDeclarationId"))
        )
    }


}