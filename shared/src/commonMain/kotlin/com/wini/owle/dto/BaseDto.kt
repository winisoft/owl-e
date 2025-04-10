package com.wini.owle.dto

/**
 * A 'Plain Old Java Object' type with properties that map directly onto a persistence-layer Entity type
 * that has only a single function called on it to produce that corresponding type
 */
interface BaseDto {
    fun idToString(): String
}

interface StandardDto: BaseDto {
    val id: Long

    override fun idToString(): String {
        return "id={$id}"
    }
}

interface CompositeDto: BaseDto