package com.wini.owle.services.user

import com.wini.owle.dto.user.PaymentMethodDto
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.mapper.user.PaymentMethodsMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.PaymentMethodIdFactory
import com.wini.owle.repo.user.PaymentMethodRepository
import com.wini.owle.services.BaseServiceImpl


class PaymentMethodService(
    repository: PaymentMethodRepository,
    mapper: PaymentMethodsMapper,
    entityIdFactory: PaymentMethodIdFactory
): BaseServiceImpl<Long, PaymentMethodDto, PaymentMethodDao>(
    repository,
    mapper,
    entityIdFactory
)