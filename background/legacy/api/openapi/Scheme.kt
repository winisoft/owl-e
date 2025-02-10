package com.wini.owle.air.api.openapi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wini.owle.ui.style.LoTheme
import io.swagger.v3.oas.models.security.SecurityScheme

@Composable
fun Scheme(
    schemes: Map<String, SecurityScheme>?,
    apiAction: (ApiAction) -> Unit
) {
    Column {
        Row(horizontalArrangement = Arrangement.End) {
            ServersDropDown(schemes, apiAction)
            Box(modifier = Modifier.fillMaxWidth()) {
                AuthButton(schemes, apiAction)
            }
        }
    }
}

@Composable
fun AuthButton(
    securitySchemes: Map<String, SecurityScheme>?,
    authButtonClicked: (ApiAction.ShowAuthorizations) -> Unit
) {
    var restricted by remember { mutableStateOf(false) }
    Button(
        enabled = !restricted,
        modifier = Modifier.border(
            width = 1.dp,
            color = if (restricted) LoTheme.colors.onBackground else LoTheme.colors.primary
        ),
        onClick = {
            authButtonClicked(ApiAction.ShowAuthorizations(securitySchemes?.get("")!!))
        }
    ) {
        Text(text = "Authorize")
        Icon(
            imageVector = if (restricted) Icons.Default.Lock else Icons.Default.LockOpen,
            tint = if (restricted) LoTheme.colors.error else LoTheme.colors.primary,
            contentDescription = null
        )
    }
}

@Composable
fun ServersDropDown(schemes: Map<String, SecurityScheme>?, schemeChanged: (ApiAction) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedKey by remember { mutableStateOf(schemes?.asIterable()?.toList()?.get(0)?.key) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            text = schemes?.get(selectedKey)?.name ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = true })
                .background(Color.Gray)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
        ) {
            schemes?.forEach { it ->
                DropdownMenuItem(
                    onClick = {
                        selectedKey = it.key
                        expanded = false
                        schemeChanged(ApiAction.SchemeChanged(it.value))
                    }
                ) {
                    Text(text = it.value.name)
                }
            }
        }
    }
}