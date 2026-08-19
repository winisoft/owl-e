package com.wini.owle.entity.user

import com.wini.owle.entity.dbEnum

import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.dto.user.enums.OauthProvider
import com.wini.owle.dto.user.enums.UserStatus
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp


object Users : ULongIdTable("users") {
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val resetToken = varchar("reset_token", 255).nullable()
    val oauthProvider = dbEnum<OauthProvider>("oauth_provider", 32).nullable()
    val oauthId = varchar("oauth_id", 255).nullable()
    val userStatus = dbEnum<UserStatus>("user_status", 20).default(UserStatus.REGISTERED)
    val apiKey = varchar("api_key", 255).nullable()
    val firstName = varchar("name_personal_first", 128).nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
}

object PaymentMethods : ULongIdTable("payment_methods") {
    val stripeId = varchar("stripe_id", 255).nullable()
    val user = reference("user_id", Users)
    val cardBrand = dbEnum<CardBrand>("card_brand", 32).nullable()
    val lastFour = varchar("last_four", 4).nullable()
    val isDefault = bool("is_default").default(false)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
}
