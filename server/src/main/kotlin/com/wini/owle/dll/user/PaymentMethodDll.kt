package com.wini.owle.dll.user

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.services.user.PaymentMethodService

class PaymentMethodDll(
    private val service: PaymentMethodService
): DLL<Long, PaymentMethod> {

    override fun getAll(): AsyncResult<List<PaymentMethod>> = service.getAll()
    override fun getById(id: Long): AsyncResult<PaymentMethod> = service.getById(id)
    override fun create(dto: PaymentMethod): AsyncResult<PaymentMethod> = service.create(dto)
    override fun createAll(dtos: List<PaymentMethod>): AsyncResult<List<PaymentMethod>> = service.create(dtos)
    override fun update(dto: PaymentMethod): AsyncResult<PaymentMethod> = service.update(dto)
    override fun updateAll(dtos: List<PaymentMethod>): AsyncResult<List<PaymentMethod>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}