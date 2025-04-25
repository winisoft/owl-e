package com.wini.owle.repo.user

import com.wini.owle.entity.user.UserEntity
import com.wini.owle.repo.OwleRepoImpl

class UserRepository: OwleRepoImpl<Long, UserEntity>(UserEntity)