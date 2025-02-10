package com.wini.owle.air.api.openapi.junk

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import io.swagger.v3.oas.models.headers.Header

@Composable
fun LazyItemScope.Header(header: Header) {

    Text(text = header.description)

    header.schema
}