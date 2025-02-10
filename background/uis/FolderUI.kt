package ui

import androidx.compose.runtime.Composable
import com.wini.owle.models.GroupedElementType
import com.wini.owle.models.WorkflowFolder
import org.jetbrains.compose.web.dom.Div


@Composable
fun FolderUI(
    folder: WorkflowFolder,
    onClick: (id: Long, groupedType: GroupedElementType) -> Unit
) {

    Div(
        attrs = {
            onClick {
                onClick(folder.id, GroupedElementType.WorkflowFolder)
            }
            classes("folder shadow-lg rounded-lg p-6")
        }
    ) {

        //WorkflowFolderHeader()

    }
}
//const FolderUI: React.FC<FolderProps> = ({ folder, onElementClick }) => {
//  return (
//      <div
//        onClick={() => onElementClick(folder.id, GroupedElementType.FOLDER)}
//        className=""
//      >
//        <FolderHeader
//            id={folder.id}
//            name={folder.name}
//        />
//        <p className="text-gray-400 mb-4">{folder.description}</p>
//        <div className="flex pl-16 pr-16 justify-between">
//          {folder.subFolders.length === 0 ? (
//            <div>
//              <FolderStat
//                icon={Icons.Workflow}
//                text={`${folder.childWorkflows} ${folder.childWorkflows.length == 1 ? 'Workflow' : 'Workflows'}`}
//              />
//            </div>
//          ) : (
//            <div>
//              <FolderStat
//                icon={Icons.Workflow}
//                text={`${folder.childWorkflows} ${folder.childWorkflows.length == 1 ? 'Workflow' : 'Workflows'}`}
//              />
//              <p className="text-xl font-bold text-white">in</p>
//              <FolderStat
//                icon={Icons.Folder}
//                text={`${folder.subFolders.length} ${folder.subFolders.length == 1 ? 'Child Folder' : 'Child Folders'}`}
//              />
//              </div>
//
//          )}
//
//        </div>
//      </div>
//  );
//}