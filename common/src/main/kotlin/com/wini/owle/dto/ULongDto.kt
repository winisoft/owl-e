package com.wini.owle.dto

interface ULongDto: BaseDto {

    /**
     * The unique identifier for members of this data type
     */
    val id: ULong

    override val owleId: String get() = "id=$id"

}