package com.gm.lo.desktop.ui.style

import androidx.compose.ui.graphics.ImageBitmap
import java.io.File
import java.io.InputStream


object Res {

//    val openApiIcon: ImageBitmap
//        get() = javaClass.classLoader
//            ?.getResourceAsStream("icon_openapi.png")?.run {
//                loadImageBitmap(this)
//            } ?:
//                ImageBitmap(0, 0)

    val apiSpecDoc: String?
        get() = javaClass.classLoader
            ?.getResourceAsStream("dist/apispec.html")
            ?.use {
                it.reader().readText()
            }

    fun <V> withInputStream(name: String, block: (InputStream) -> V) {
        javaClass.classLoader.getResourceAsStream(name)?.use {
            block(it)
        }
    }

    fun getResFile(name: String): File? = javaClass.classLoader
        ?.getResource(name)
        ?.path
        ?.run { File(this) }

    fun getResFileText(path: String): String? = javaClass.classLoader
            ?.getResourceAsStream(path)
            ?.use { it.reader().readText() }

    class SwaggerUI(val spec: String) {
        val html: String?
            get() = javaClass.classLoader.getResourceAsStream("dist/apispec.html")
                .use { it?.reader()?.readText() }
                ?.run {
                    replace("window.onload = () => { window.ui = SwaggerUIBundle({ ",
                        "window.onload = () => { window.ui = SwaggerUIBundle({ $spec"
                    )
                }
    }

    object Svg {
        var inputStream = javaClass.classLoader.getResourceAsStream("file.txt")

    }

    const val noDpiDrawables: String = "/img/drawable-nodpi"
    const val htmlDir: String = "/specpages"

    object html {
        val base = "$htmlDir/base_index.html"
        val cabalerts = "$htmlDir/cabalerts_index.html"
        val cdbid = "$htmlDir/cdbid_index.html"
    }

    object css {
        val darkSwagger = "/dark-swagger-user.css"
    }

    object drawable {
        val starscape = "$noDpiDrawables/starscape2.png"
        val skylineProper = "$noDpiDrawables/skyline_core_black.png"
        val landing = "$noDpiDrawables/skyline.png"
        val iconCircle = "$noDpiDrawables/icon_circle.png"
        val resurgetIcon: String = "$noDpiDrawables/resurget_350.png"
        val speramusIcon: String = "$noDpiDrawables/speramus_350.png"
        val iconRenCenEnabledPng: String = "$noDpiDrawables/icon_rencen_enabled.png"
        val iconRenCenDisabledPng: String = "$noDpiDrawables/icon_rencen_disabled.png"
        val iconRenCenDisabledSvg = "img/drawable-nodpi/icon_rencen_disabled.svg"
        val iconRenCenEnabledSvg = "img/drawable-nodpi/icon_rencen_enabled.svg"
        val speramusWoman: String = "$noDpiDrawables/speramus_girl.png"
        val resurgetWoman: String = "$noDpiDrawables/resurget_girl.png"
        val openApi = "$noDpiDrawables/open_api.svg"
        val fireSky = "$noDpiDrawables/fire_background.png"
//        val p1 = "drawable-nodpi/p1.jpeg"
//        val p2 = "drawable-nodpi/p2.jpeg"
//        val p3 = "drawable-nodpi/p3.jpeg"
//        val p6 = "drawable-nodpi/p6.jpeg"
//
//        val ic_instagram = "drawable/ic_instagram.xml"
//        val ic_send = "drawable/ic_send.xml"
//        val ic_twitter = "drawable/ic_twitter.xml"
    }

    object string {
//        val spotify_nav_home = "Home"
//        val spotify_nav_search = "Search"
//        val spotify_nav_library = "Your Library"
    }
}