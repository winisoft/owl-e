package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.outlined.Whatshot
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.wini.owle.air.domain.model.ParentItem
import com.wini.owle.air.domain.model.prop.RunningState
import com.wini.owle.air.inlines.*


data class LoRootItem(
    val runningState: RunningState,
    override val isExpanded: IsExpanded = IsExpanded(false)
): LoItem(), ParentItem {

    val id: LoRootItemId
        get() = LoRootItemId(0)

    override val loid: Long
        get() = id.value

    override val name: Name
        get() = Name("LackOffice")

    override val isEnabled: IsEnabled
        get() = IsEnabled(runningState is RunningState.Running)

    /**
     * @see LoItem.typeName
     */
    override val typeName: String
        get() = "LackOffice"

    /**
     * @see LoItem.overrideName
     */
    override val overrideName: OverrideName?
        get() = null

    /**
     * @see LoItem.displayTextMain
     */
    override val displayTextMain: String
        get() = typeName

    override val displayTextSub: String?
        get() = null

    /**
     * @see LoItem.depth
     */
    override val depth: Int
        get() = 0

    /**
     * @see LoItem.disabledImplicitIcon
     */
    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.Whatshot

    override val isHorizontalDisable: IsHorizontalDisabled
        get() = IsHorizontalDisabled(false)

    /**
     * @see LoItem.isVisible
     *
     * As the root in the list, this item has no ancestor to collapse and hide it. This is always true.
     */
    override val isVisible: Boolean
        get() = true

    /**
     * @see LoItem.isImplicitDisable
     *
     * As the root of the item list, this item has no ancestor to disable and affect it implicitly. This is always false.
     */
    override val isImplicitDisable: Boolean
        get() = false

    override val disabledExplicitIcon: ImageVector
        get() = Icons.Default.Whatshot
    override val statefulIcon: ImageVector
        get() = if (runningState is RunningState.Running) enabledIcon else Icons.Default.Whatshot

    override val breadcrumbText: String
        get() = "LackOffice"

    private var enabledIconBacking: ImageVector? = null

    override val enabledState: EnabledState
        get() = if (runningState is RunningState.Running) EnabledState.ENABLED else EnabledState.EXPLICITLY_DISABLED

    override val enabledIcon: ImageVector
        get() = enabledIconBacking ?: ImageVector.Builder(
            "LoRootItem.Disabled", 82.0.dp, 82.0.dp, 82.0f, 82.0f
        ).apply {
            listOf(
                Triple(32.0f to 41.0f, 0.0f to 27.0f, listOf(9.0f to -0.0f, 0.0f to -27.0f, -9.0f to -0.0f)),
                Triple(11.0f to 50.0f, 0.0f to 18.0f, listOf(8.5f to -0.0f, 0.0f to -18.0f, -8.5f to -0.0f)),
                Triple(54.0f to 50.0f,  0.0f to 18.0f, listOf(8.5f to -0.0f, 0.0f to -18.0f, -8.5f to -0.0f))
            ).forEach { (start, delta, tower) ->
                path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0x00000000))) {
                    moveTo(start.first, start.second)
                    lineToRelative(delta.first, delta.second)
                    tower.forEach { (x, y) -> repeat(2) { lineToRelative(x, y) } }
                }
            }
        }.build().also { enabledIconBacking = it }

}