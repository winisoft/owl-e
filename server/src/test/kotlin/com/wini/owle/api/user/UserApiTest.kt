package com.wini.owle.api.user

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.UserDto
import kotlinx.serialization.KSerializer


class UserApiTest : BaseLongPkApiTest<Long, UserDto, UserApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): UserDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<UserDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<UserDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, UserDto>): UserApi {
        TODO("Not yet implemented")
    }

}