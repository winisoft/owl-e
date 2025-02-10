package com.lo.desktop.ui.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lo.desktop.ui.style.Res.drawable.fireGif
import com.lo.desktop.ui.style.Res.drawable.skyline
import com.lo.desktop.ui.style.Res.drawable.starscape
import java.io.File
import java.io.InputStream
import kotlin.io.use


object Res {

    private var enabledIconBacking: ImageVector? = null

    val appIconBitmap: ImageBitmap?
        get() = javaClass.classLoader
            .getResourceAsStream(drawable.icon256)
            ?.buffered()?.use {
                it.run { loadImageBitmap(this) }
            }

    fun getResFile(name: String): File? = javaClass.classLoader
        ?.getResource(name)
        ?.path
        ?.run { File(this) }

    fun getFireGifResourceStream(): InputStream? = javaClass.classLoader?.getResource(fireGif)?.openStream()

    const val noDpiDrawables: String = "img/drawable-nodpi"
    const val icons: String = "img/icons"

    @Composable
    fun StarscapePainter() = painterResource(starscape)
    @Composable
    fun SkylinePainter() = painterResource(skyline)

    object drawable {
        val fireGif = "$noDpiDrawables/fire-flames.gif"
        val starscape = "$noDpiDrawables/starscape.png"
        val skyline = "$noDpiDrawables/skyline.png"
        val lo_icon = "$noDpiDrawables/lo_icon.png"
        val iconRenCenDisabledSvg = "$noDpiDrawables/icon_rencen_disabled.svg"
        val iconRenCenEnabledSvg = "$noDpiDrawables/icon_rencen_enabled.svg"
        val openApi = "$noDpiDrawables/open_api.svg"
        val icon44 = "$icons/icon44.png"
        val icon48 = "$icons/icon48.png"
        val icon54 = "$icons/icon54.png"
        val icon128 = "$icons/icon128.png"
        val icon150 = "$icons/icon150.png"
        val icon256 = "$icons/icon256.png"
    }

    val loRootItemEnabled: ImageVector get() = enabledIconBacking ?: ImageVector.Builder(
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