package com.wini.owle.model.workflows




interface OwleGroup {
    val groupType: GroupType
    val allChildren: List<GroupSubject>
}

interface GroupSubject {
    val id: Long
    val name: String
    val description: String?
    val elementType: GroupedElementType
    val parent: OwleGroup?
}

/**
 *
 */
enum class FolderSort {
    NAME_ASC,
    NAME_DESC,
    CREATED_ASC,
    CREATED_DESC
}

enum class DataPointsSort {
    NAME_ASC,
    NAME_DESC,
    CREATED_ASC,
    CREATED_DESC
}

enum class DataPointsFilter {
    NAME,
    DATA_TYPE,
    PACKAGE
}

/**
 * Represents an element's quality of being a grouping for some other type or types,
 * allowing each type to provide semantic meaning about the hierarchy above and below it.
 *
 * A WorkflowFolder may be the parent to a collection of either Workflows and/or other WorkflowFolders.
 * A Workflow may be the parent to a collection of both (or either) Phases and/or Steps.
 * A Phase may be the parent to a collection of both (or either) Steps and/or other Phases.
 *
 */
sealed interface GroupType {

    val childTypes: List<GroupedElementType>

    data object NullWorkflowFolder : GroupType {
        override val childTypes
            get() = listOf(
                GroupedElementType.WorkflowFolder,
                GroupedElementType.Workflow
            )
    }

    data object WorkflowFolder : GroupType {
        override val childTypes
            get() = listOf(
                GroupedElementType.WorkflowFolder,
                GroupedElementType.Workflow
            )
    }

    data object Workflow : GroupType {
        override val childTypes
            get() = listOf(
                GroupedElementType.Phase,
                GroupedElementType.Step
            )
    }

    data object Phase : GroupType {
        override val childTypes
            get() = listOf(
                GroupedElementType.Phase,
                GroupedElementType.Step
            )
    }
}

/**
 * Represents an element's quality of being grouped within the scope of another type,
 * allowing each type to provide semantic meaning about the hierarchy above and below it.
 *
 * A WorkflowFolder may be grouped within another WorkflowFolder (or none).
 * A Workflow may be grouped within a WorkflowFolder (or none).
 * A Phase may be grouped within another Phase, or no other Phase (by default grouped within a Workflow at its root scope).
 * A Step may be grouped within a Phase, or no Phase (by default grouped within a Workflow at its root scope).
 *
 * Note that, by extension, both Phase and Step are necessarily grouped within a
 */
sealed interface GroupedElementType {

    val parentTypes: List<GroupType>

    data object WorkflowFolder : GroupedElementType {
        override val parentTypes
            get() = listOf(
                GroupType.NullWorkflowFolder,
                GroupType.WorkflowFolder
            )
    }

    data object Workflow : GroupedElementType {
        override val parentTypes
            get() = listOf(
                GroupType.NullWorkflowFolder,
                GroupType.WorkflowFolder
            )
    }

    data object Phase : GroupedElementType {
        override val parentTypes
            get() = listOf(
                GroupType.Workflow,
                GroupType.Phase
            )
    }

    data object Step : GroupedElementType {
        override val parentTypes
            get() = listOf(
                GroupType.Workflow,
                GroupType.Phase
            )
    }
}