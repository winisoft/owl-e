package com.wini.owle.dto.runs

import kotlinx.serialization.Serializable

@Serializable
sealed class LogLevelDto(val id: String) {

    @Serializable
    data object Debug : LogLevelDto("DEBUG")

    @Serializable
    data object Info : LogLevelDto("INFO")

    @Serializable
    data object Warning : LogLevelDto("WARNING")

    @Serializable
    data object Error : LogLevelDto("ERROR")

    @Serializable
    data object Critical : LogLevelDto("CRITICAL")
}