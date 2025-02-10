package preview.component.oelement.folder

import androidx.compose.runtime.Composable
import preview.PreviewItems
import ui.ApplicationView


@Composable
fun PreviewWorkflowFolderUIs(isExpandedInitial: Boolean = false) {

    PreviewItems(
        label = "Workflow Folders",
        isExpandedInitial = isExpandedInitial,
        columnCount = 1,
        fractionalWidths = "1fr",
        items = listOf(
            {
                ApplicationView()
            }
//            { PreviewItemWithBottomLabel(
//                block = {
//                    FolderUI(
//                        folder = WorkflowFolder(
//                            id = 0L,
//                            subfolders = listOf(
//
//                            ),
//                            childWorkflowsCount = 0,
//                            parentFolder = null,
//                            name = "Blender Utils",
//                            description = "Workflows useful for operations undertaken using Blender, the popular open source multimedia production application",
//                            img = null,
//                            createdAt = "",
//                            updatedAt = ""
//                        ),
//                        onElementClick = { id, groupedType -> }
//                    )
//                },
//                name = "With Description"
//            )
//            },
//            { PreviewItemWithBottomLabel(
//                block = {
//                    FolderUI(
//                        folder = WorkflowFolder(
//                            id = 0L,
//                            subfolders = listOf(
//
//                            ),
//                            childWorkflowsCount = 0,
//                            parentFolder = null,
//                            name = "Blender Utils",
//                            description = "",
//                            img = null,
//                            createdAt = "",
//                            updatedAt = ""
//                        ),
//                        onElementClick = { id, groupedType ->
//
//                        }
//                    )
//                },
//                name = "Without Description"
//            )
//            },
//            { PreviewItemWithBottomLabel(
//                block = {
//                    FolderUI(
//                        folder = WorkflowFolder(
//                            id = 0L,
//                            subfolders = listOf(
//                                WorkflowFolder(id = 0L, name = "", createdAt = "", updatedAt = "", subfolders = emptyList())
//                            ),
//                            childWorkflowsCount = 3,
//                            parentFolder = null,
//                            name = "Blender Utils",
//                            description = "",
//                            img = null,
//                            createdAt = "",
//                            updatedAt = ""
//                        ),
//                        onElementClick = { id, groupedType ->
//
//                        }
//                    )
//                },
//                name = "Holding 3 in 1 Subfolder"
//            )
//            },
//            { PreviewItemWithBottomLabel(
//                block = {
//                    FolderUI(
//                        folder = WorkflowFolder(
//                            id = 0L,
//                            subfolders = listOf(
//                                WorkflowFolder(id = 0L, name = "", createdAt = "", updatedAt = "", subfolders = emptyList()),
//                                WorkflowFolder(id = 0L, name = "", createdAt = "", updatedAt = "", subfolders = emptyList())
//                            ),
//                            childWorkflowsCount = 5,
//                            parentFolder = null,
//                            name = "Blender Utils",
//                            description = "",
//                            img = null,
//                            createdAt = "",
//                            updatedAt = ""
//                        ),
//                        onElementClick = { id, groupedType ->
//
//                        }
//                    )
//                },
//                name = "Holding 5 in 2 Subfolders"
//            )
//            }
        )
    )



}