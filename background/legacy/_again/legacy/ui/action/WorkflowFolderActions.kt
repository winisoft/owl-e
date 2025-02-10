package ui.action


sealed class WorkflowFolderActions {

    data class View(val id: Long) : WorkflowFolderActions()

    data class Trash(val id: Long) : WorkflowFolderActions()

    data class Clone(val id: Long) : WorkflowFolderActions()

    data class Copy(val id: Long) : WorkflowFolderActions()

    data class MouseDown(val id: Long) : WorkflowFolderActions()

    data class MouseOver(val id: Long) : WorkflowFolderActions()

    data class MouseUp(val id: Long) : WorkflowFolderActions()

    data class Move(val id: Long, val newParentId: Long) : WorkflowFolderActions()

}