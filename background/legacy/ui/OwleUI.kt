package com.wini.owle.ui

import androidx.compose.runtime.*
import com.wini.owle.models.*
import com.wini.owle.ui.app.OwleHeader
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.attributes.*

@Composable
fun OwleUI(
    elements: List<GroupSubject> = emptyList(),
    folderBreadcrumb: List<WorkflowFolder> = emptyList(),
    onElementClick: (id: Int, groupType: GroupType) -> Unit = { id, gt -> },
    onBreadcrumbClick: (folderIndex: Int) -> Unit = { i -> }
) {
    var expandedGroups by remember { mutableStateOf(listOf<GroupSubject>()) }

    fun pushElementGroup(group: GroupSubject) {
        expandedGroups = expandedGroups + group
    }

    fun popToGroup(groupType: GroupType, id: Long) {
        val targetIndex = expandedGroups.indexOfFirst { group ->
            when (group.elementType) {
                GroupedElementType.FOLDER -> (group as WorkflowFolder).id == id
                GroupedElementType.WORKFLOW_SUMMARY -> (group as WorkflowSummary).id == id
                GroupedElementType.WORKFLOW -> TODO()
                GroupedElementType.PHASE -> TODO()
                GroupedElementType.STEP -> TODO()
            }
        }

        if (targetIndex != -1) {
            expandedGroups = expandedGroups.subList(0, targetIndex + 1)
        }
    }

    @Composable
    fun renderFolderBreadcrumb() {
        Div(attrs = {
            classes("folder-breadcrumb")
            style {
                color(Color.white)
                marginBottom(16.px)
            }
        }) {
            folderBreadcrumb.forEachIndexed { index, folder ->
                Span {
                    A(
                        href = "#",
                        attrs = {
                            onClick { onBreadcrumbClick(index) }
                            classes("underline", "font-bold", "text-gray-300", "hover:text-white")
                        }
                    ) {
                        Text(folder.name)
                    }
                    if (index < folderBreadcrumb.size - 1) {
                        Text(" > ")
                    }
                }
            }
        }
    }

    @Composable
    fun renderOwleHeader() {
        OwleHeader(
            newWorkflowClick = { /* Handle create workflow */ },
            filterWorkflowsClick = { /* Handle filter workflows */ },
            settingsClick = { /* Handle settings click */ },
            showTagChips = { /* Handle show tag chips */ }
        )
    }

    Div(attrs = {
        classes("bg-gray-900", "min-h-screen", "p-2")
        style {
            backgroundColor(Color("#1E1E1E")) // Dark gray
        }
    }) {
        renderOwleHeader()

        if (folderBreadcrumb.isNotEmpty()) {
            renderFolderBreadcrumb()
        }

//        if (expandedGroups.isEmpty()) {
//            FolderOrSummaryListView(elements = elements, onElementClick = onElementClick)
//        } else {
//            Div {
//                ExpandedGroupsUI(expandedGroups = expandedGroups, onNavigateBack = { groupType, id ->
//                    popToGroup(groupType, id)
//                })
//            }
//        }
    }
}
