package com.wini.owle.api.typing

import com.wini.owle.api.Base2IdCompositeApiTest
import com.wini.owle.async.AsyncError
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.entity.TypeUnionMembers
import com.wini.owle.entity.composteid.CompositeIdFactory
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.validators.BaseValidator


open class TypeUnionMemberApiTest : Base2IdCompositeApiTest<TypeUnionMemberDto, TypeUnionMemberApi>() {


    override fun getDto(id1: Long, id2: Long): TypeUnionMemberDto = DtoTestInstanceFactory.typeUnionMemberDto(
        unionTypeDeclarationId = id1,
        memberTypeDeclarationId = id2
    )

    override fun getApiInstance(
        dll: DLL<CompositeID, TypeUnionMemberDto>,
        validator: BaseValidator<TypeUnionMemberDto>,
        compositeIdFactory: CompositeIdFactory<TypeUnionMemberDto>
    ): TypeUnionMemberApi {
        return TypeUnionMemberApi(
            dll = dll,
            validator = validator,
            compositeIdFactory = compositeIdFactory
        )
    }

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

    override fun onlyFirstParamProvidedErrors(): List<AsyncError.ApiRequestError> {
        return listOf(
            AsyncError.ApiRequestError.MissingParameter("memberTypeDeclarationId")
        )
    }

    override fun allParamsMalformedErrors(): List<AsyncError.ApiRequestError> {
        return listOf(
            AsyncError.ApiRequestError.ParamValueMalformedLong("unionTypeDeclarationId"),
            AsyncError.ApiRequestError.ParamValueMalformedLong("memberTypeDeclarationId")
        )
    }


    override val route: String = "/api/type-union-member"

    override val malformedParamsRoute: String
        get() = "$route/id?unionTypeDeclarationId=Text&memberTypeDeclarationId=Text"

    override val getByIdRoute: String
        get() = getDto(1, 1).run {
            "$route/id?unionTypeDeclarationId=${unionTypeDeclarationId}&memberTypeDeclarationId=${memberTypeDeclarationId}"
        }

    override val deleteByIdRoute: String
        get() = getDto(1, 1).run {
            "$route/id?unionTypeDeclarationId=${unionTypeDeclarationId}&memberTypeDeclarationId=${memberTypeDeclarationId}"
        }

    override val onlyFirstParamRoute: String
        get() = getDto(1, 1).run {
            "$route/id?unionTypeDeclarationId=${unionTypeDeclarationId}"
        }
}