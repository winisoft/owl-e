package com.wini.owle.api.user

import com.wini.owle.api.Base2IdCompositeApiTest
import com.wini.owle.async.AsyncError
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.entity.TagRelations
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.validators.BaseValidator
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID


open class TagEntityRelationApiTest: Base2IdCompositeApiTest<TagRelation, TagRelationApi>() {

    override fun getDto(id1: Long, id2: Long): TagRelation = DtoTestInstanceFactory.tagRelationDto(
        parentTagId = id1,
        childTagId = id2
    )

    override fun getApiInstance(
        dll: DLL<CompositeID, TagRelation>,
        validator: BaseValidator<TagRelation>,
        compositeIdFactory: CompositeIdFactory<TagRelation>
    ): TagRelationApi {
        return TagRelationApi(
            dll = dll,
            validator = validator,
            compositeIdFactory = compositeIdFactory
        )
    }

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

    override val serializer: KSerializer<TagRelation> = TagRelation.serializer()
    
    override val listSerializer: KSerializer<List<TagRelation>> = ListSerializer(TagRelation.serializer())

    override fun allParamsMalformedErrors(): List<AsyncError.ApiRequestError> {
        return listOf(
            AsyncError.ApiRequestError.ParamValueMalformedLong("parentTagId"),
            AsyncError.ApiRequestError.ParamValueMalformedLong("childTagId")
        )
    }

    override fun onlyFirstParamProvidedErrors(): List<AsyncError.ApiRequestError> {
        return listOf(
            AsyncError.ApiRequestError.MissingParameter("childTagId")
        )
    }


    override val route: String = "/api/tag-relation"

    override val malformedParamsRoute: String
        get() = "$route/id?parentTagId=Text&childTagId=Text"

    override val getByIdRoute: String
        get() = getDto(1, 1).run { "$route/id?parentTagId=${parentTagId}&childTagId=${childTagId}"  }

    override val deleteByIdRoute: String
        get() = getDto(1, 1).run { "$route/id?parentTagId=${parentTagId}&childTagId=${childTagId}" }

    override val onlyFirstParamRoute: String
        get() = getDto(1, 1).run { "$route/id?parentTagId=${parentTagId}" }
}