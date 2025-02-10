package com.wini.owle.ui
//
//import androidx.compose.runtime.Composable
//import com.wini.owle.MockModels.step
//import com.wini.owle.ui.img.IconButton
//import com.wini.owle.ui.img.Icons
//import com.wini.owle.ui.workflows.StepUI
//import org.jetbrains.compose.web.css.Color
//import org.jetbrains.compose.web.css.backgroundColor
//import org.jetbrains.compose.web.dom.Div
//
//val mockIcons = listOf(
//    Icons.AddItem, Icons.ArrowLeft, Icons.Check, Icons.Clone, Icons.CollapseFullscreen, Icons.CollapseSection,
//    Icons.CollapseAll, Icons.Data, Icons.DataItem, Icons.DataItemAdd, Icons.DataItemEdit, Icons.DataItemReadonly,
//    Icons.DataItemSearch, Icons.DataSearch, Icons.DataType, Icons.Edit, Icons.ErrorWatch, Icons.ExpandAll,
//    Icons.ExpandFullscreen, Icons.ExpandItem, Icons.ExpandSection, Icons.Filter, Icons.FilterOff, Icons.Folder,
//    Icons.FolderRefresh, Icons.Function, Icons.Functions, Icons.InspectLater, Icons.Inspector, Icons.InspectorBad,
//    Icons.InspectorClass, Icons.Inspectors, Icons.Junction, Icons.Param, Icons.Paste, Icons.Pencil, Icons.Phase,
//    Icons.PhaseAdd, Icons.Phases, Icons.Provision, Icons.Provisions, Icons.RemoveItem, Icons.Revision,
//    Icons.Revisions, Icons.Settings, Icons.Step, Icons.StepAdd, Icons.StepNext, Icons.StepRemove, Icons.StepSettings,
//    Icons.Steps, Icons.Trash, Icons.Workflow, Icons.Workspace, Icons.Payment.ACH, Icons.Payment.AliPay,
//    Icons.Payment.AMEX, Icons.Payment.ApplePay, Icons.Payment.BACS, Icons.Payment.BanContact, Icons.Payment.BECS,
//    Icons.Payment.Blik, Icons.Payment.CartesBancaires, Icons.Payment.CashApp, Icons.Payment.ChUnionPay,
//    Icons.Payment.Diners, Icons.Payment.Discover, Icons.Payment.EPS, Icons.Payment.FPX, Icons.Payment.GooglePay,
//    Icons.Payment.GrabPay, Icons.Payment.Ideal, Icons.Payment.JCB, Icons.Payment.Link, Icons.Payment.MasterCard,
//    Icons.Payment.MobilePay, Icons.Payment.P24, Icons.Payment.PayNow, Icons.Payment.PayPal, Icons.Payment.PromptPay,
//    Icons.Payment.RemCom, Icons.Payment.RevolutPay, Icons.Payment.SEPA, Icons.Payment.Twint, Icons.Payment.VISA,
//    Icons.Payment.WeChatPay
//)
//
//@Composable
//fun Playground(){
//
//    Div(attrs = {
//        classes("bg-gray-900", "min-h-screen", "p-2")
//        style {
//            backgroundColor(Color("#1E1E1E")) // Dark gray
//        }
//    }) {
//        IconButton(icon = mockIcons[3])
////        LazyVerticalGrid(columns = GridCells.Fixed(5), state = gridState) {
////            items(count = mockIcons.size) {
////                IconButton(icon = mockIcons[it])
////            }
////        }
//    }
//
//}