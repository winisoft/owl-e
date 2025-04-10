package com.wini.owle.api.user

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.user.UserDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class UserApiTest : BaseLongPkApiTest<UserDto, UserApi>() {

    override val route: String = "/api/user"

    override fun getDto(id: Long): UserDto = DtoTestInstanceFactory.userDto(
        id = id
    )

    override val serializer: KSerializer<UserDto> = UserDto.serializer()
    
    override val listSerializer: KSerializer<List<UserDto>> = ListSerializer(UserDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, UserDto>,
        validator: DtoValidator<UserDto>
    ): UserApi = UserApi(dll, validator)

}