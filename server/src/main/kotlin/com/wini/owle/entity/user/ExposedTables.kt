package com.wini.owle.entity.user

import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.dto.user.enums.OauthProvider
import com.wini.owle.dto.user.enums.UserStatus
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp


object Users : ULongIdTable("users") {
    val email = varchar("email", 255).uniqueIndex()
    val userName = varchar("user_name", 52).nullable()
    val firstName = varchar("name_personal_first", 128).nullable()
    val lastName = varchar("name_personal_last", 255).nullable()
    val password = varchar("password", 255)
    val resetToken = varchar("reset_token", 255).nullable()
    val oauthProvider = enumerationByName("oauth_provider", 50, OauthProvider::class).nullable()
    val oauthId = varchar("oauth_id", 255).nullable()
    val userStatus = enumerationByName("user_status", 20, UserStatus::class)
        .default(UserStatus.REGISTERED)
    val apiKey = varchar("apiKey", 255).nullable()
    val createdAt = timestamp("createdAt").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updatedAt").defaultExpression(CurrentTimestamp)
}


object PaymentMethods : ULongIdTable("payment_methods") {
    val stripeId = varchar("stripe_id", 255).nullable()
    val user = reference("user_id", Users, onDelete = ReferenceOption.CASCADE)
    val cardBrand = enumerationByName("card_brand", 50, CardBrand::class).nullable()
    val lastFour = varchar("last_four", 4).nullable()
    val isDefault = bool("is_default").default(false)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
}