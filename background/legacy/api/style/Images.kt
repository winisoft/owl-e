package com.gm.lo.desktop.ui.style

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Density
import java.io.File
import java.net.URL

//fun loadImageBitmap(file: File): ImageBitmap =
//    file.inputStream().buffered().use(::loadImageBitmap)
//
//fun loadSvgPainter(file: File, density: Density): Painter =
//    file.inputStream().buffered().use {
//        loadSvgPainter(it, density)
//    }
//
//fun loadXmlImageVector(file: File, density: Density): ImageVector =
//    file.inputStream().buffered().use {
//        loadXmlImageVector(InputSource(it), density)
//    }
//
///* Loading from network with java.net API */
//
//fun loadImageBitmap(url: String): ImageBitmap =
//    URL(url).openStream().buffered().use(::loadImageBitmap)
//
//fun loadSvgPainter(url: String, density: Density): Painter =
//    URL(url).openStream().buffered().use {
//        loadSvgPainter(it, density)
//    }
//
//fun loadXmlImageVector(url: String, density: Density): ImageVector =
//    URL(url).openStream().buffered().use {
//        loadXmlImageVector(InputSource(it), density)
//    }