package com.wini.owle.mapper.user

import com.wini.owle.dto.DtoTestInstanceFactory.paymentMethodDto
import com.wini.owle.dto.DtoTestInstanceFactory.userDto
import com.wini.owle.dto.PaymentMethodDto
import com.wini.owle.entity.CardBrand.Companion.toDto
import com.wini.owle.entity.EntityTestInstanceFactory.paymentMethodDao
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.DateTimeConverter
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class PaymentMethodMapperTest: BaseOwleMapperTest<PaymentMethodDto, PaymentMethodDao, PaymentMethodsMapper>() {

    private val realMapper by KoinJavaComponent.inject<PaymentMethodsMapper>(PaymentMethodsMapper::class.java)


    override fun instantiateMapper(): PaymentMethodsMapper { return realMapper }

    override fun Transaction.dto(id: Long): PaymentMethodDto {
        return paymentMethodDto()
    }

    override fun Transaction.existingDao(): PaymentMethodDao {
        return paymentMethodDao()
    }

    override fun Transaction.existingDaoDto(): Pair<PaymentMethodDao, PaymentMethodDto> {
        val dao = existingDao()
        val dto = dao.run {
            paymentMethodDto(
                id = dao.id.value,
                stripePaymentMethodId = stripePaymentMethodId,
                user = userDto(id = user.id.value),
                cardBrand = cardBrand?.toDto(),
                lastFour = lastFour,
                isDefault = isDefault,
                createdAt = DateTimeConverter.toString(createdAt),
                updatedAt = DateTimeConverter.toString(updatedAt)
            )
        }
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: PaymentMethodDao, dto: PaymentMethodDto) {
        assertEquals(dao.stripePaymentMethodId, dto.stripePaymentMethodId)
        assertEquals(dao.user.id.value, dto.user.id)
        assertEquals(dao.cardBrand?.toDto(), dto.cardBrand)
        assertEquals(dao.lastFour, dto.lastFour)
        assertEquals(dao.isDefault, dto.isDefault)
    }

    override fun assertDtoPropsExpectedMatch(dto: PaymentMethodDto, dao: PaymentMethodDao) {
        assertEquals(dto.stripePaymentMethodId, dao.stripePaymentMethodId)
        assertEquals(dto.user.id, dao.user.id.value)
        assertEquals(dto.cardBrand, dao.cardBrand?.toDto())
        assertEquals(dto.lastFour, dao.lastFour)
        assertEquals(dto.isDefault, dao.isDefault)
    }


}