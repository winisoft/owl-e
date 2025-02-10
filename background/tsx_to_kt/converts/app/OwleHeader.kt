package com.wini.owle.ui.app

//import androidx.compose.runtime.*
//import androidx.compose.web.events.SyntheticMouseEvent

//import org.jetbrains.compose.web.css.*
//import org.jetbrains.compose.web.dom.*
//
//@Composable
//fun OwleHeader(
//    newWorkflowClick: (SyntheticMouseEvent) -> Unit,
//    filterWorkflowsClick: () -> Unit,
//    settingsClick: (SyntheticMouseEvent) -> Unit,
//    showTagChips: (SyntheticMouseEvent) -> Unit
//) {
//    var isHovered by remember { mutableStateOf(false) }
//
//    Div(
//        attrs = {
//            classes("gap-2", "w-full", "pb-6")
//            style {
//                paddingBottom(24.px)
//                width(100.percent)
//            }
//            onMouseEnter { isHovered = true }
//            onMouseLeave { isHovered = false }
//        }
//    ) {
//        Div(
//            attrs = {
//                classes("flex", "justify-between", "items-center", "mb-4")
//                style {
//                    marginBottom(16.px)
//                }
//            }
//        ) {
//            // Owle Icon Section
//            Div(attrs = { classes("pl-2", "pr-4") }) {
//                OwleHeaderIcon()
//            }
//
//            // Header Text
//            H1(attrs = {
//                classes("text-4xl", "flex-grow", "font-bold", "text-white")
//                style {
//                    fontSize(36.px)
//                    fontWeight("bold")
//                    color(Color.white)
//                }
//            }) {
//                Text("OWL-E")
//            }
//
//            // Hover Actions
//            if (isHovered) {

//                Div(attrs = {
//                    classes("w-auto", "h-auto", "flex", "space-x-4")
//                }) {
//                    IconButton(Icons.Filter, onClick = showTagChips)
//                    IconButton(Icons.AddItem, onClick = newWorkflowClick)
//                    IconButton(Icons.Settings, onClick = settingsClick)
//                }
//            }
//        }
//    }
//}
