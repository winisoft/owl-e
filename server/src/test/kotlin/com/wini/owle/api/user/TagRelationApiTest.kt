package com.wini.owle.api.user

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.user.TagRelationDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID

open class TagRelationApiTest : BaseCompositePkApiTest<TagRelationDto, TagRelationApi>() {

    override val route: String = "/api/tag-relation"

    override fun getDto(): TagRelationDto = DtoTestInstanceFactory.tagRelationDto()

    override val serializer: KSerializer<TagRelationDto> = TagRelationDto.serializer()
    
    override val listSerializer: KSerializer<List<TagRelationDto>> = ListSerializer(TagRelationDto.serializer())

    override fun getApiInstance(dll: DLL<CompositeID, TagRelationDto>): TagRelationApi = TagRelationApi(dll)
}