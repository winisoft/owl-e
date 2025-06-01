package com.wini.owle.dto

interface UIntDto: BaseDto {

    /**
     * The unique identifier for members of this data type
     */
    val id: UInt

    override val owleId: String get() = "id=$id"

}