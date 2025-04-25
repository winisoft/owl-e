package com.wini.owle.dll.user

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.User
import com.wini.owle.services.user.UserService

class UserDll(
    private val service: UserService
): DLL<Long, User> {

    override fun getAll(): AsyncResult<List<User>> = service.getAll()
    override fun getById(id: Long): AsyncResult<User> = service.getById(id)
    override fun create(dto: User): AsyncResult<User> = service.create(dto)
    override fun createAll(dtos: List<User>): AsyncResult<List<User>> = service.create(dtos)
    override fun update(dto: User): AsyncResult<User> = service.update(dto)
    override fun updateAll(dtos: List<User>): AsyncResult<List<User>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}