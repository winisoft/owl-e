package com.wini.owle.api.user

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.PaymentMethod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class PaymentMethodEntityApiTest : BaseLongPkApiTest<PaymentMethod, PaymentMethodApi>() {
    override val route: String = "/api/payment-method"

    override fun getDto(id: Long): PaymentMethod = DtoTestInstanceFactory.paymentMethodDto(
        id = id
    )

    override val serializer: KSerializer<PaymentMethod> = PaymentMethod.serializer()
    
    override val listSerializer: KSerializer<List<PaymentMethod>> = ListSerializer(PaymentMethod.serializer())

    override fun getApiInstance(
        dll: DLL<Long, PaymentMethod>,
        validator: DtoValidator<PaymentMethod>
    ): PaymentMethodApi = PaymentMethodApi(dll, validator)
}