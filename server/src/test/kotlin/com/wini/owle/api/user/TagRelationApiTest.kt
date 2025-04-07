package com.wini.owle.api.user

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.TagRelationDto
import kotlinx.serialization.KSerializer


class TagRelationApiTest : BaseCompositePkApiTest<Long, TagRelationDto, TagRelationApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TagRelationDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TagRelationDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TagRelationDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TagRelationDto>): TagRelationApi {
        TODO("Not yet implemented")
    }

}