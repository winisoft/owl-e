package com.wini.owle.api.user

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.TagDto
import kotlinx.serialization.KSerializer


class TagApiTest : BaseLongPkApiTest<Long, TagDto, TagApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TagDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TagDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TagDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TagDto>): TagApi {
        TODO("Not yet implemented")
    }

}