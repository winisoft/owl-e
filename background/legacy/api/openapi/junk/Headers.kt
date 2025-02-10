package com.wini.owle.air.api.openapi.junk

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import io.swagger.models.properties.Property
import io.swagger.v3.oas.models.headers.Header


@Composable
fun Headers(headers: MutableMap<String, Property>) {

    val listState = rememberLazyListState()

    LazyColumn(state = listState) {

        headers.map { header ->
            item {
                Header(header as Header)
            }
        }
    }
}