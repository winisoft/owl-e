package com.wini.owle.air.api.openapi.junk

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wini.owle.ui.style.LoTheme
import com.wini.owle.air.api.openapi.ApiAction
import io.swagger.models.apideclaration.Operation
import io.swagger.models.resourcelisting.ApiListingReference
import io.swagger.models.resourcelisting.ResourceListing

@Composable
fun SwaggerUISpec(resourceListing: ResourceListing, action: (ApiAction) -> Unit) {

//    resourceListing.apply {
//        ApiInfo(
//            info = info,
//            swaggerVersion = swaggerVersion,
//            apiVersion = apiVersion,
//            action = action
//        )
//
//        LazyColumn {
//            apis.map {
//                item {
//                    ApiListingReference(it)
//                }
//            }
//        }
//    }
}

@Composable
fun ApiListingReference(apiListing: ApiListingReference) {

    var isExpanded = remember { false }

    Row(modifier = Modifier.clickable {
        isExpanded = !isExpanded
    }) {
        Text(
            text = apiListing.path,
            style = LoTheme.typography.h1
        )
        Text(
            text = apiListing.description,
            style = LoTheme.typography.subtitle
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.ArrowDownward,
                modifier = Modifier.align(Alignment.End),
                contentDescription = null
            )
        }

    }

}

@Composable
fun Operations(operations: List<Operation>) {

//    val method: Method? = null
//    val summary: String? = null
//    val notes: String? = null
//    val nickname: String? = null
//    val authorizations: Map<String, List<AuthorizationScope>> = HashMap()
//    val parameters: List<Parameter> = ArrayList()
//    val responseMessages: List<ResponseMessage> = ArrayList()
//    val produces: List<String> = ArrayList()
//    val consumes: List<String> = ArrayList()
//    val deprecated: Boolean? = null
}