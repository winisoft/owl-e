package com.wini.owle.api.user

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.PaymentMethodDto
import kotlinx.serialization.KSerializer

class PaymentMethodApiTest : BaseLongPkApiTest<Long, PaymentMethodDto, PaymentMethodApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): PaymentMethodDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<PaymentMethodDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<PaymentMethodDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, PaymentMethodDto>): PaymentMethodApi {
        TODO("Not yet implemented")
    }

}