package com.wini.owle.mapper.user

import com.wini.owle.dto.DtoTestInstanceFactory.userDto
import com.wini.owle.dto.UserDto
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.SubscriptionStatus
import com.wini.owle.entity.SubscriptionStatus.Companion.toDto
import com.wini.owle.entity.Users
import com.wini.owle.entity.user.UserDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.environs.PackageRepoMapper
import com.wini.owle.mapper.mapperModule
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class UserMapperTest: BaseOwleMapperTest<UserDto, UserDao, UsersMapper>() {

    private val realMapper by KoinJavaComponent.inject<UsersMapper>(UsersMapper::class.java)


    override val implicatedTables: List<IdTable<*>> = listOf(Users)

    override fun instantiateMapper(): UsersMapper { return realMapper }

    override fun Transaction.dto(id: Long): UserDto {
        return userDto(
            id = id,
            email = "test@example.com",
            password = "securepassword"
        )
    }

    override fun Transaction.existingDao(): UserDao {
        return userDao(
            email = "test@example.com",
            password = "securepassword",
            subscriptionStatus = SubscriptionStatus.ACTIVE
        )
    }

    override fun Transaction.existingDaoDto(): Pair<UserDao, UserDto> {
        val dao = existingDao()
        val dto = userDto(
            id = dao.id.value,
            email = dao.email,
            password = dao.password,
            resetToken = dao.resetToken,
            oauthProvider = dao.oauthProvider,
            oauthId = dao.oauthId,
            subscriptionStatus = dao.subscriptionStatus.toDto(),
            apiKey = dao.apiKey,
            createdAt = DateTimeConverter.toString(dao.createdAt),
            updatedAt = DateTimeConverter.toString(dao.updatedAt),
            paymentMethods = emptyList()
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: UserDao, dto: UserDto) {
        assertEquals(dao.email, dto.email)
        assertEquals(dao.password, dto.password)
        assertEquals(dao.subscriptionStatus.toDto(), dto.subscriptionStatus)
        assertEquals(dao.apiKey, dto.apiKey)
    }

    override fun assertDtoPropsExpectedMatch(dto: UserDto, dao: UserDao) {
        assertEquals(dto.email, dao.email)
        assertEquals(dto.password, dao.password)
        assertEquals(dto.subscriptionStatus, dao.subscriptionStatus.toDto())
        assertEquals(dto.apiKey, dao.apiKey)
    }


}