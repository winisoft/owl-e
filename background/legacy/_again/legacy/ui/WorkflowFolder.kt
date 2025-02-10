package ui

import androidx.compose.runtime.*
import com.wini.owle.models.WorkflowFolder
import org.jetbrains.compose.web.css.px
import ui.action.WorkflowFolderActions
import ui.component.button.IconButton
import ui.component.img.Icon
import ui.component.img.Icons
import ui.component.layout.group.OwleRow
import ui.component.layout.position.Spacer
import ui.component.oelement.OElementHeader
import ui.component.img.Icons.ChevronDownSmall
import ui.component.img.Icons.ChevronRightSmall
import ui.component.layout.position.FillMaxWidth
import ui.component.text.OwleText


@Composable
fun WorkflowFolder(
    depth: Int,
    folder: WorkflowFolder,
    action: (WorkflowFolderActions) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    OwleRow {
        repeat(depth) { Spacer(4.px) }

        if (folder.subfolders.isEmpty())
            Spacer(24.px)
        else
            IconButton(size = 20.px, onClick = { isExpanded = !isExpanded }) {
                Icon(if (isExpanded) ChevronDownSmall else ChevronRightSmall, 16.px)
            }

        OElementHeader(
            icon = Icons.Folder,
            onItemClick = { action(WorkflowFolderActions.View(folder.id)) },
            onMouseDown = { action(WorkflowFolderActions.MouseDown(folder.id)) },
            onMouseUp = { action(WorkflowFolderActions.MouseUp(folder.id)) },
            onMouseOver = { action(WorkflowFolderActions.MouseOver(folder.id)) },
            text = {
                FillMaxWidth {
                    OwleText(folder.name)
                }
            },
            controlPanel = {
                IconButton(icon = Icons.Clone) {
                    action(WorkflowFolderActions.Clone(folder.id))
                }
                IconButton(icon = Icons.Copy) {
                    action(WorkflowFolderActions.Copy(folder.id))
                }
                IconButton(icon = Icons.Trash) {
                    action(WorkflowFolderActions.Trash(folder.id))
                }
            }
        )

        if (isExpanded) {
            folder.subfolders.forEach {
                WorkflowFolder(
                    depth = depth + 1,
                    folder = it,
                    action = action
                )
            }
        }
    }
}