package com.wini.owle.services.user

import com.wini.owle.dto.user.PaymentMethodDto
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.mapper.user.PaymentMethodsMapper
import com.wini.owle.repo.user.PaymentMethodRepository
import com.wini.owle.services.BaseServiceImpl


class PaymentMethodService(
    repository: PaymentMethodRepository,
    mapper: PaymentMethodsMapper
): BaseServiceImpl<Long, PaymentMethodDto, PaymentMethodDao>(repository, mapper)