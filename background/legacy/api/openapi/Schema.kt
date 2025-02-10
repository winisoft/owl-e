package com.wini.owle.air.api.openapi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import io.swagger.models.properties.Property
import io.swagger.v3.oas.models.media.Schema

@Composable
fun <T> Schema(schema: Schema<T>) {

    Column {

        Text(schema.title)

        Row {
            Text("Type:")
            Text(schema.type)
        }


        Row {
            Text(text = "Multiple of:")
            Text(schema.multipleOf.toEngineeringString())
        }

        Row {
            Text("Min:")
            Text("${schema.minimum.toEngineeringString()} ${if (schema.exclusiveMinimum) "(Exclusive)" else "(Inclusive)"}")
        }

        Row {
            Text("Max:")
            Text("${schema.maximum.toEngineeringString()} ${if (schema.exclusiveMaximum) "(Exclusive)" else "(Inclusive)"}")
        }

        Column {
            Row {
                Text("Min Length:")
                Text(schema.minLength.toString())
            }
            Row {
                Text("Max Length:")
                Text(schema.maxLength.toString())
            }
        }

        Row {
            Text("Pattern:")
            Text(schema.pattern)
        }

        Row {
            Column {
                Row {
                    Text("Min Items:")
                    Text(schema.minItems.toString())
                }
                Row {
                    Text("Max Items:")
                    Text(schema.maxItems.toString())
                }
                Row {
                    Text(if (schema.uniqueItems) "(Unique)" else "(Non-Unique)")
                }
            }
        }

        Text("Properties:")
        LazyColumn {
            schema.properties.map {
                it as Property
            }.map {
                item {
                    Text(it.name)
                }
            }
        }

        Text("Enum:")
        LazyColumn {
            schema.enum.map {
                item {
                    Text(it.toString())
                }
            }
        }
    }
}