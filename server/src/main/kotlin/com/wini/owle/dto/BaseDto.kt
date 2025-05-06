package com.wini.owle.dto

/**
 * A 'Plain Old Java Object' type with properties that map directly onto a persistence-layer Entity type
 * that has only a single function called on it to produce that corresponding type
 */
interface BaseDto {
    val idText: String get() = "${this::class.simpleName}{${idToString()}}"
    fun idToString(): String
}

