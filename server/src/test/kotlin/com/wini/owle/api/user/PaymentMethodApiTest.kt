package com.wini.owle.api.user

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.user.PaymentMethodDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class PaymentMethodApiTest : BaseLongPkApiTest<PaymentMethodDto, PaymentMethodApi>() {
    override val route: String = "/api/payment-method"

    override fun getDto(id: Long): PaymentMethodDto = DtoTestInstanceFactory.paymentMethodDto(
        id = id
    )

    override val serializer: KSerializer<PaymentMethodDto> = PaymentMethodDto.serializer()
    
    override val listSerializer: KSerializer<List<PaymentMethodDto>> = ListSerializer(PaymentMethodDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, PaymentMethodDto>,
        validator: BaseValidator<PaymentMethodDto>
    ): PaymentMethodApi = PaymentMethodApi(dll, validator)
}