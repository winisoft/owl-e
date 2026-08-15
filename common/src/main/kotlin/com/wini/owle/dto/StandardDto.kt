package com.wini.owle.dto

interface StandardDto: BaseDto {

    val id: Long

    override val owleId: String
        get() = "id=$owleId"

}