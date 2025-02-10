package com.wini.owle.ui.workflows
//
//import androidx.compose.runtime.*
//import com.wini.owle.models.Step
//import org.jetbrains.compose.web.css.*
//import org.jetbrains.compose.web.dom.*
//import org.w3c.dom.svg.SVGElement
//import org.w3c.dom.svg.SVGPathElement
//
//
//@Composable
//fun StepUI(step: Step, onEdit: () -> Unit = {}, onDelete: () -> Unit = {}) {
//    var isHovered by remember { mutableStateOf(false) }
//
//    Div(
//        attrs = {
//            classes("bg-gray-600", "p-2", "rounded-lg", "mt-2", "flex", "justify-between", "items-center")
//            onMouseEnter { isHovered = true }
//            onMouseLeave { isHovered = false }
//        }
//    ) {
//        // Step name with icon
//        Div(
//            attrs = {
//                classes("flex", "items-center")
//            }
//        ) {
//            // Icon
//
//
//            // Step name
//            Span(
//                attrs = {
//                    classes("text-white")
//                }
//            ) {
//                Text(step.name)
//            }
//        }
//
//        // Hover buttons
//        if (isHovered) {
//            Div(
//                attrs = {
//                    classes("flex", "space-x-2")
//                }
//            ) {
//                // Edit Button
//                Button(
//                    attrs = {
//                        classes("text-blue-300", "hover:text-blue-100")
//                        onClick { onEdit() }
//                    }
//                ) {
//                    Text("Edit")
//                }
//
//                // Delete Button
//                Button(
//                    attrs = {
//                        classes("text-blue-300", "hover:text-blue-100")
//                        onClick { onDelete() }
//                    }
//                ) {
//                    Text("Delete")
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun SvgIcon() {
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
//        }) {
//
//        }
//    }
//}