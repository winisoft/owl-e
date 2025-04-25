package com.wini.owle.services.user

import com.wini.owle.dto.user.User
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.repo.UserIdFactory
import com.wini.owle.repo.user.UserRepository
import com.wini.owle.services.BaseServiceImpl


class UserService(
    repository: UserRepository,
    mapper: UsersMapper,
    entityIdFactory: UserIdFactory
): BaseServiceImpl<Long, User, UserEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)