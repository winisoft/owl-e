package ui.datapoint

//@Composable
//fun DataPointCard(
//    workflowId: Int,
//    dataPoint: DataPoint,
//    onEdit: (DataPoint) -> Unit,
//    onDelete: (DataPoint) -> Unit
//) {
//    Div(attrs = {
//        classes("bg-black", "rounded-lg", "p-6", "mb-4", "shadow-lg", "w-full")
//        attr("tabIndex", "0")
//    }) {
//        // Header section
//        Div(attrs = {
//            classes("flex", "items-center", "justify-between", "mb-4")
//        }) {
//            // Left section: Icon and title
//            Div(attrs = {
//                classes("flex", "items-center", "space-x-4")
//            }) {
//                Icon(
//                    size = 35,
//                    icon = if (dataPoint.isReadOnly) Icons.DataItemReadonly else Icons.DataItem,
//                    className = "w-8 h-8 text-white"
//                )
//                H1(attrs = {
//                    classes("text-3xl", "font-bold", "text-white")
//                }) {
//                    Text(dataPoint.name)
//                }
//            }
//
//            // Right section: Edit and Delete buttons
//            Div(attrs = {
//                classes("flex", "space-x-2")
//            }) {
//                IconButton(
//                    size = 35,
//                    icon = Icons.DataItemEdit,
//                    onClick = { onEdit(dataPoint) }
//                )
//                IconButton(
//                    size = 35,
//                    icon = Icons.RemoveItem,
//                    onClick = { onDelete(dataPoint) }
//                )
//            }
//        }
//
//        // Content section
//        Div(attrs = {
//            classes("flex", "w-full", "space-x-4")
//        }) {
//            // Left column
//            Div(attrs = {
//                classes("w-3/5")
//            }) {
//                Div(attrs = {
//                    classes("text-2xl", "text-gray-300", "mb-2")
//                }) {
//                    Text(dataPoint.typeDeclaration.typeDefinition.name)
//                }
//
//                dataPoint.description?.let {
//                    P(attrs = {
//                        classes("text-xl", "text-orange-400", "mb-2")
//                    }) {
//                        Text(it)
//                    }
//                }
//
//                if (!dataPoint.isReadOnly) {
//                    P(attrs = {
//                        classes("text-xl", "text-blue-400", "mb-2")
//                    }) {
//                        Text(dataPoint.textValue ?: "")
//                    }
//                }
//            }
//
//            // Right column
//            Div(attrs = {
//                classes("w-2/5")
//            }) {
//                DataPointBreakdown(
//                    isReadonly = dataPoint.isReadOnly
//                    // provisions = dataPoint.provisions
//                    // revisions = dataPoint.revisions
//                    // inquisitions = dataPoint.inquisitions
//                )
//            }
//        }
//    }
//}
//
//// Mock Icon component
//@Composable
//fun Icon(size: Int, icon: String, className: String) {
//    Div(attrs = {
//        classes(className)
//        style { width(size.px); height(size.px) }
//    }) {
//        Text(icon) // Placeholder for actual icon rendering
//    }
//}
//
//// Mock IconButton component
//@Composable
//fun IconButton(size: Int, icon: String, onClick: () -> Unit) {
//    Button(attrs = {
//        style { width(size.px); height(size.px) }
//        onClick { onClick() }
//    }) {
//        Text(icon) // Placeholder for actual icon rendering
//    }
//}
//
//// Mock DataPointBreakdown component
//@Composable
//fun DataPointBreakdown(isReadonly: Boolean) {
//    Div(attrs = {
//        classes("breakdown-placeholder")
//    }) {
//        Text("Breakdown component: readonly = $isReadonly")
//    }
//}
//
//// DataPoint model class
//data class DataPoint(
//    val name: String,
//    val isReadOnly: Boolean,
//    val typeDeclaration: TypeDeclaration,
//    val description: String?,
//    val textValue: String?
//)
//
//// TypeDeclaration mock
//data class TypeDeclaration(val typeDefinition: TypeDefinition)
//data class TypeDefinition(val name: String)
