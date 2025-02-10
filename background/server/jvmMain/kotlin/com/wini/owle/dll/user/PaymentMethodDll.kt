package com.wini.owle.dll.user

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.PaymentMethodDto
import com.wini.owle.services.user.PaymentMethodService

class PaymentMethodDll(
    private val service: PaymentMethodService
): DLL<Long, PaymentMethodDto> {

    override fun getAll(): AsyncResult<List<PaymentMethodDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<PaymentMethodDto?> = service.getById(id)
    override fun create(dto: PaymentMethodDto): AsyncResult<PaymentMethodDto> = service.create(dto)
    override fun create(dtos: List<PaymentMethodDto>): AsyncResult<List<PaymentMethodDto>> = service.create(dtos)
    override fun update(dto: PaymentMethodDto): AsyncResult<PaymentMethodDto> = service.update(dto)
    override fun update(dtos: List<PaymentMethodDto>): AsyncResult<List<PaymentMethodDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}