package com.wini.owle.air.api.openapi.junk

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import io.swagger.v3.oas.models.security.SecurityScheme

class OpenApiState(defaultScheme: SecurityScheme) {

    val selected: MutableState<SecurityScheme> = mutableStateOf(defaultScheme)
    val isServersDropDownExpanded: MutableState<Boolean> = mutableStateOf(false)

}