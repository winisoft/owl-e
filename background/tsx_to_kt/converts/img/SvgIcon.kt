package com.wini.owle.ui.img
//
//import androidx.compose.runtime.Composable
//import org.jetbrains.compose.web.css.height
//import org.jetbrains.compose.web.css.px
//import org.jetbrains.compose.web.css.width
//import org.jetbrains.compose.web.dom.TagElement
//import org.w3c.dom.svg.SVGElement
//import org.w3c.dom.svg.SVGPathElement
//
//@Composable
//fun DefaultOwleIcon(iconContent: @Composable () -> Unit) {
//    TagElement<SVGElement>("svg", {
//        attr("width", "24")
//        attr("height", "24")
//        attr("viewBox", "0 0 24 24")
//        attr("fill", "none")
//        attr("xmlns", "http://www.w3.org/2000/svg")
//        style {
//            width(24.px)
//            height(24.px)
//        }
//    }) {
//        iconContent()
//    }
//}
//
//@Composable
//fun SvgIcon(iconContent: @Composable () -> Unit) {
//
//    TagElement<SVGElement>("svg", {
//        attr("width", "24")
//        attr("height", "24")
//        attr("viewBox", "0 0 24 24")
//        attr("fill", "none")
//        attr("xmlns", "http://www.w3.org/2000/svg")
//        style {
//            width(24.px)
//            height(24.px)
//        }
//    }) {
//        TagElement<SVGPathElement>("path", {
//            attr("d", "M9 5l7 7-7 7")
//        }) {}
//    }
//}