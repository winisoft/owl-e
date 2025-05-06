package com.wini.owle.dto

interface StandardDto: BaseDto {

    val id: Long

    override fun idToString(): String {
        return "id=$id"
    }

}