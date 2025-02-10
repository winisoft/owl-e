package com.wini.owle.mapper

import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DateTimeFormatBuilder

import java.time.format.DateTimeFormatter


object DateTimeConverter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    fun toString(localDateTime: LocalDateTime): String {
        return localDateTime.toJavaLocalDateTime().format(formatter)
    }

    fun fromString(timestamp: String): LocalDateTime {
        return java.time.LocalDateTime.parse(timestamp, formatter).toKotlinLocalDateTime()
    }
}