package com.wini.owle.dll

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto


interface DLL<ID: Any, Dto: BaseDto> {

    fun getAll(): AsyncResult<List<Dto>>

    fun getById(id: ID): AsyncResult<Dto?>

    fun create(dto: Dto): AsyncResult<Dto>

    fun createAll(dtos: List<Dto>): AsyncResult<List<Dto>>

    fun update(dto: Dto): AsyncResult<Dto>

    fun updateAll(dtos: List<Dto>): AsyncResult<List<Dto>>

    fun delete(id: ID): AsyncResult<Unit>

    fun deleteAll(ids: List<ID>): AsyncResult<Unit>

}