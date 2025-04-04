package com.wini.owle.mapper

import kotlinx.datetime.*
import java.time.format.DateTimeFormatter


object DateTimeConverter {

    private val formatter: ThreadLocal<DateTimeFormatter> = ThreadLocal.withInitial {
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    }

    fun toString(timestamp: LocalDateTime): String {
        return timestamp.toJavaLocalDateTime().format(formatter.get())
    }

    fun fromString(timestamp: String): LocalDateTime {
        return java.time.LocalDateTime.parse(timestamp, formatter.get()).toKotlinLocalDateTime()
    }
}


