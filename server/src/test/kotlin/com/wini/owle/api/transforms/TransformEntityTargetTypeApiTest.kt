package com.wini.owle.api.transforms

import com.wini.owle.api.Base2IdCompositeApiTest
import com.wini.owle.async.AsyncError
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.entity.TransformTargetTypes
import com.wini.owle.entity.composteid.CompositeIdFactory
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.validators.BaseValidator


open class TransformEntityTargetTypeApiTest : Base2IdCompositeApiTest<TransformTargetType, TransformTargetTypeApi>() {

    override val route: String = "/api/transform-target-type"

    override val malformedParamsRoute: String
        get() = "$route/id?unionTypeDeclarationId={Text}&typeDeclarationId={Text}"

    override val getByIdRoute: String
        get() = getDto(1, 1).run {
            "$route/id?transformationId=${transformationId}&typeDeclarationId=${typeDeclarationId}"
        } //

    override val deleteByIdRoute: String
        get() = getDto(1, 1).run {
            "$route/id?transformationId=${transformationId}&typeDeclarationId=${typeDeclarationId}"
        }

    override val onlyFirstParamRoute: String
        get() = getDto(1, 1).run {
            "$route/id?transformationId=${transformationId}"
        }

    override fun getDto(id1: Long, id2: Long): TransformTargetType = DtoTestInstanceFactory.transformationTargetTypeDto(
        transformationId = id1,
        typeDeclarationId = id2
    )

    override fun getApiInstance(
        dll: DLL<CompositeID, TransformTargetType>,
        validator: BaseValidator<TransformTargetType>,
        compositeIdFactory: CompositeIdFactory<TransformTargetType>
    ): TransformTargetTypeApi {
        return TransformTargetTypeApi(
            dll = dll,
            validator = validator,
            compositeIdFactory = compositeIdFactory
        )
    }

    override fun compositeId1(): CompositeID {
        return CompositeID {
            it[TransformTargetTypes.transformation] = 1
            it[TransformTargetTypes.eligibleTypeDeclaration] = 1
        }
    }

    override fun compositeId2(): CompositeID {
        return CompositeID {
            it[TransformTargetTypes.transformation] = 2
            it[TransformTargetTypes.eligibleTypeDeclaration] = 2
        }
    }

    override val serializer: KSerializer<TransformTargetType> = TransformTargetType.serializer()
    
    override val listSerializer: KSerializer<List<TransformTargetType>> = ListSerializer(TransformTargetType.serializer())

    override fun onlyFirstParamProvidedErrors(): List<AsyncError.ApiRequestError> {
        return listOf(
            AsyncError.ApiRequestError.MissingParameter("typeDeclarationId")
        )
    }

    override fun allParamsMalformedErrors(): List<AsyncError.ApiRequestError> {
        return listOf(
            AsyncError.ApiRequestError.ParamValueMalformedLong("transformationId"),
            AsyncError.ApiRequestError.ParamValueMalformedLong("typeDeclarationId")
        )
    }
}