package com.wini.owle.api.user

import com.wini.owle.api.ApiError
import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.async.AsyncError
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.entity.TagRelations
import com.wini.owle.validators.DtoValidator
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID


open class TagRelationApiTest: BaseCompositePkApiTest<TagRelationDto, TagRelationApi>() {

    override val route: String = "/api/tag-relation"

    override val malformedParamsRoute: String
        get() = "$route/id?parentTagId=Text&childTagId=Text"

    override fun getDto(id1: Long, id2: Long): TagRelationDto = DtoTestInstanceFactory.tagRelationDto(
        parentTagId = id1,
        childTagId = id2
    )

    override fun compositeId1(): CompositeID {
        return CompositeID {
            it[TagRelations.parentTag] = 1
            it[TagRelations.childTag] = 1
        }
    }

    override fun compositeId2(): CompositeID {
        return CompositeID {
            it[TagRelations.parentTag] = 2
            it[TagRelations.childTag] = 2
        }
    }

    override val serializer: KSerializer<TagRelationDto> = TagRelationDto.serializer()
    
    override val listSerializer: KSerializer<List<TagRelationDto>> = ListSerializer(TagRelationDto.serializer())

    override fun getApiInstance(
        dll: DLL<CompositeID, TagRelationDto>,
        validator: DtoValidator<TagRelationDto>
    ): TagRelationApi = TagRelationApi(dll, validator)

    override fun improperParameters(): List<ApiError> {
        return listOf(
            ApiError.fromAsyncError(AsyncError.ApiRequestError.ParamValueMalformedLong("parentTagId")),
            ApiError.fromAsyncError(AsyncError.ApiRequestError.ParamValueMalformedLong("childTagId"))
        )
    }

    override fun missingParamErrors(): List<ApiError> {
        return listOf(
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("parentTagId")),
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("childTagId"))
        )
    }


}