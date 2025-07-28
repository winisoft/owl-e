package com.wini.owle.api.user

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.User
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class UserEntityApiTest : BaseLongPkApiTest<User, UserApi>() {

    override val route: String = "/api/user"

    override fun getDto(id: Long): User = DtoTestInstanceFactory.userDto(
        id = id
    )

    override val serializer: KSerializer<User> = User.serializer()
    
    override val listSerializer: KSerializer<List<User>> = ListSerializer(User.serializer())

    override fun getApiInstance(
        dll: DLL<Long, User>,
        validator: DtoValidator<User>
    ): UserApi = UserApi(dll, validator)

}