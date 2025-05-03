package com.wini.owle.api.typing

import com.wini.owle.api.Base2IdCompositeApiTest
import com.wini.owle.error.OwleError
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.entity.TypeUnionMembers
import com.wini.owle.entity.composteid.CompositeIdFactory
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.validators.BaseValidator


open class TypeUnionMemberApiTest : Base2IdCompositeApiTest<TypeUnionMember, TypeUnionMemberApi>() {


    override fun getDto(id1: Long, id2: Long): TypeUnionMember = DtoTestInstanceFactory.typeUnionMemberDto(
        unionTypeDeclarationId = id1,
        memberTypeDeclarationId = id2
    )

    override fun getApiInstance(
        dll: DLL<CompositeID, TypeUnionMember>,
        validator: BaseValidator<TypeUnionMember>,
        compositeIdFactory: CompositeIdFactory<TypeUnionMember>
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

    override val serializer: KSerializer<TypeUnionMember> = TypeUnionMember.serializer()
    
    override val listSerializer: KSerializer<List<TypeUnionMember>> = ListSerializer(TypeUnionMember.serializer())

    override fun onlyFirstParamProvidedErrors(): List<OwleError.ApiRequestError> {
        return listOf(
            OwleError.ApiRequestError.MissingParameter("memberTypeDeclarationId")
        )
    }

    override fun allParamsMalformedErrors(): List<OwleError.ApiRequestError> {
        return listOf(
            OwleError.ApiRequestError.ParamValueMalformedLong("unionTypeDeclarationId"),
            OwleError.ApiRequestError.ParamValueMalformedLong("memberTypeDeclarationId")
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