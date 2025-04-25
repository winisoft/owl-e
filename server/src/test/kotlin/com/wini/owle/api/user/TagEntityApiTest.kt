package com.wini.owle.api.user

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.user.tags.Tag
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class TagEntityApiTest : BaseLongPkApiTest<Tag, TagApi>() {
    override val route: String = "/api/tag"

    override fun getDto(id: Long): Tag = DtoTestInstanceFactory.tagDto(
        id = id
    )

    override val serializer: KSerializer<Tag> = Tag.serializer()
    
    override val listSerializer: KSerializer<List<Tag>> = ListSerializer(Tag.serializer())

    override fun getApiInstance(
        dll: DLL<Long, Tag>,
        validator: BaseValidator<Tag>
    ): TagApi = TagApi(dll, validator)
}