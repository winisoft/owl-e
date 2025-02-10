package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wysiwyg
import androidx.compose.material.icons.outlined.Wysiwyg
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.call.HttpCode
import com.wini.owle.air.domain.call.HttpMethod
import com.wini.owle.air.inlines.*


data class Call(
    val id: CallId,
    override val environmentId: EnvironmentId,
    override val apiOverrideId: ApiOverrideId,
    override val endpointOverrideId: EndpointOverrideId,
    val utility: Utility?,
    override val overrideName: OverrideName?,
    val description: Description?,
    override val isEnabled: IsEnabled,
    val method: HttpMethod,
    val postBody: PostBody?,
    val requestHeaders: RequestHeaders?,
    val queryParams: QueryParams?,
    val responseHeaders: ResponseHeaders?,
    val httpCode: HttpCode,
    val delayMillis: DelayMillis,
    val responseBody: ResponseBody?,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean,
    override val isHorizontalDisable: IsHorizontalDisabled
): LoItem(),
   Environment.ChildOf,
   ApiOverride.ChildOf,
   EndpointOverride.ChildOf {

    override val loid: Long
        get() = id.value

    override val displayTextMain: String
        get() = overrideName?.value.run { if (isNullOrEmpty()) "$typeName $loid" else "" }

    override val displayTextSub: String
        get() = "${method.key} - HTTP ${httpCode.value}"

    override val typeName
        get() = "Call"
    override val depth
        get() = 5

    override val enabledIcon: ImageVector
        get() = Icons.Filled.Wysiwyg

    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.Wysiwyg

    override val name: Name
        get() = Name("${method.key}: HTTP ${httpCode.value}")

    override val breadcrumbText: String
        get() = overrideName?.value ?: "${method.key}: HTTP ${httpCode.value}"

    override val category: ItemCategory
        get() = ItemCategory.FakeApis

    fun isEnabledContextualIcon(
        rootItemEnabled: Boolean,
        environmentEnabled: Boolean,
        apiThingsHeaderEnabled: Boolean,
        apiOverrideEnabled: Boolean,
        endpointOverrideEnabled: Boolean
    ): ImageVector = when {
        rootItemEnabled
        && environmentEnabled
        && apiThingsHeaderEnabled
        && apiOverrideEnabled
        && endpointOverrideEnabled
        && isEnabled.value
            -> enabledIcon
        isEnabled.value.not()
            -> disabledExplicitIcon
        else
            -> disabledExplicitIcon
    }

}