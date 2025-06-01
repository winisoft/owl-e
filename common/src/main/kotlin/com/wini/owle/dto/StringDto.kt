package com.wini.owle.dto


interface StringDto: BaseDto {

    /**
     * The unique identifier for members of this data type
     */
    val name: String

    override val owleId: String get() = "mame=$name"

}