package com.wini.owle.services.user

import com.wini.owle.dto.user.PaymentMethodDto
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.mapper.user.PaymentMethodsMapper
import com.wini.owle.repo.PaymentMethodIdFactory
import com.wini.owle.repo.user.PaymentMethodRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory
import org.jetbrains.exposed.dao.id.CompositeID


class PaymentMethodService(
    repository: PaymentMethodRepository,
    mapper: PaymentMethodsMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, PaymentMethodDto, PaymentMethodDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)