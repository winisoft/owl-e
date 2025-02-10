package com.wini.owle.ui.img
//
//import androidx.compose.runtime.*
//import androidx.compose.web.events.SyntheticMouseEvent
//import org.jetbrains.compose.web.css.*
//import org.jetbrains.compose.web.dom.*
//
//
//@Composable
//fun IconButton(
//    icon: Icons,
//    customClass: String = "",
//    size: CSSNumeric = 24.px,
//    onClick: (SyntheticMouseEvent) -> Unit = {}
//) {
//    Button(
//        attrs = {
//            classes(
//                "relative", "w-6", "h-6", "flex", "items-center", "justify-center", "overflow-hidden",
//                "transition-all", "duration-200", "ease-in-out", "hover:bg-gray-700", "active:bg-gray-600",
//                "hover:shadow-md", "active:shadow-lg"
//            )
//            classes(customClass)
//            style {
//                width(size)
//                height(size)
//            }
//            onClick { event -> onClick(event) }
//        }
//    ) {
//        Span(
//            attrs = {
//                classes(icon.cssClass, "transition-all", "transform", "hover:scale-105", "active:scale-110")
//                style {
//                    width(size)
//                    height(size)
//                }
//            }
//        )
//    }
//}
