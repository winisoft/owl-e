package com.wini.owle.services.user

import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.mapper.user.PaymentMethodsMapper
import com.wini.owle.repo.PaymentMethodIdFactory
import com.wini.owle.repo.user.PaymentMethodRepository
import com.wini.owle.services.BaseServiceImpl


class PaymentMethodService(
    repository: PaymentMethodRepository,
    mapper: PaymentMethodsMapper,
    entityIdFactory: PaymentMethodIdFactory
): BaseServiceImpl<Long, PaymentMethod, PaymentMethodEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)