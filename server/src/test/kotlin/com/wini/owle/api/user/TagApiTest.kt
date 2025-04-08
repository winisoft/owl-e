package com.wini.owle.api.user

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.user.TagDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

open class TagApiTest : BaseLongPkApiTest<TagDto, TagApi>() {
    override val route: String = "/api/tag"

    override fun getDto(): TagDto = DtoTestInstanceFactory.tagDto()

    override val serializer: KSerializer<TagDto> = TagDto.serializer()
    
    override val listSerializer: KSerializer<List<TagDto>> = ListSerializer(TagDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TagDto>,
        validator: (TagDto) -> Boolean
    ): TagApi = TagApi(dll, validator)
}