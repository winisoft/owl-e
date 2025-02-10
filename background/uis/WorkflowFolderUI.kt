package ui.component.oelement.folder

import androidx.compose.runtime.Composable
import com.wini.owle.models.GroupedElementType
import com.wini.owle.models.WorkflowFolder
import org.jetbrains.compose.web.css.whiteSpace
import org.jetbrains.compose.web.dom.Div
import ui.component.img.Icons
import ui.component.oelement.region.AppLevelRegion
import ui.component.row.OElementGroupContextRow
import ui.component.row.OElementRow
import ui.component.text.InfoDescription
import ui.component.text.OText
import ui.component.text.OwleText


@Composable
fun FolderUI(
    folder: WorkflowFolder,
    onElementClick: (Long, GroupedElementType) -> Unit
) {

    AppLevelRegion {

        WorkflowFolderHeader(id = folder.id, name = folder.name)

        InfoDescription(folder.description)

        Div({
            classes("flex", "items-center", "w-full", "px-4", "py-2", "space-x-2")
        }) {
            if (folder.hasSubfolders) {
                OElementGroupContextRow(
                    rowSubjectIcon = Icons.Workflow,
                    rowSubjectText = folder.workflowsCountText,
                    groupContextIcon = Icons.Folder,
                    groupContextText = folder.childFoldersText
                )
            } else {
                OElementRow(
                    text = {
                        OwleText(
                            folder.workflowsCountText,
                            OText.Size.LG,
                            OText.Color.ON_SURFACE
                        ) {
                            style { whiteSpace("nowrap") }
                        }
                    },
                    icon = Icons.Workflow
                )
            }
        }
    }
}
