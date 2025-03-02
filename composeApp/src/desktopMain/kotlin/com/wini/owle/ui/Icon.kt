package com.wini.owle.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import com.wini.owle.res.*
import org.jetbrains.compose.resources.*


data class OwleIcon(
    val drawable: DrawableResource,
    val nameStrResource: StringResource
) {

    val name: @Composable () -> String get() = {
        stringResource(nameStrResource)
    }

    val contentDescription: @Composable () -> String get() = {
        "${stringResource(nameStrResource)} icon"
    }
}

object Icons {

    @Composable
    fun Icon(drawable: DrawableResource) {
        Image(
            painter = painterResource(resource = drawable),
            contentDescription = ""
        )
    }


    object OElement {
        object Workflow {
            val Item = OwleIcon(Res.drawable.workflow, Res.string.app_name)
            val AltFilled = OwleIcon(Res.drawable.workflow_alt_filled, Res.string.app_name)
            val AltOutline = OwleIcon(Res.drawable.workflow_alt_outline, Res.string.app_name)
        }
        object WorkflowFolder {
            val Items = OwleIcon(Res.drawable.workflow_folders, Res.string.app_name)
            val Item = OwleIcon(Res.drawable.workflow_folder, Res.string.app_name)
            val Root = OwleIcon(Res.drawable.workflow_folder_root, Res.string.app_name)
            val RootAlt = OwleIcon(Res.drawable.workflow_folder_root_alt, Res.string.app_name)
            val RootCollapsed = OwleIcon(Res.drawable.workflow_folder_root_collapsed, Res.string.app_name)
            val RootExpanded = OwleIcon(Res.drawable.workflow_folder_root_expanded, Res.string.app_name)
        }
        object Folder {}
        object DataPoint {
            val Item = OwleIcon(Res.drawable.datapoint, Res.string.app_name)
            val Add = OwleIcon(Res.drawable.datapoint_add, Res.string.app_name)
            val Dependencies = OwleIcon(Res.drawable.datapoint_dependencies, Res.string.app_name)
            val Dependency = OwleIcon(Res.drawable.datapoint_dependency, Res.string.app_name)
            val Readonly = OwleIcon(Res.drawable.datapoint_readonly, Res.string.app_name)
            val Refresh = OwleIcon(Res.drawable.datapoint_refresh, Res.string.app_name)
            val Search = OwleIcon(Res.drawable.datapoint_search, Res.string.app_name)
            val Update = OwleIcon(Res.drawable.datapoint_update, Res.string.app_name)
            val Updates = OwleIcon(Res.drawable.datapoint_updates, Res.string.app_name)
            val Items = OwleIcon(Res.drawable.datapoints, Res.string.app_name)
            val ItemsSearch = OwleIcon(Res.drawable.datapoint_search, Res.string.app_name)
        }
        object Step {
            val Items = OwleIcon(Res.drawable.steps, Res.string.app_name)
            val Item = OwleIcon(Res.drawable.step, Res.string.app_name)
            val Empty = OwleIcon(Res.drawable.step_empty, Res.string.app_name)
            val Add = OwleIcon(Res.drawable.step, Res.string.app_name)
            val Remove = OwleIcon(Res.drawable.step, Res.string.app_name)
            val DefaultNext = OwleIcon(Res.drawable.step, Res.string.app_name)
            val Succeeded = OwleIcon(Res.drawable.step, Res.string.app_name)
            val BookmarkAdd = OwleIcon(Res.drawable.step, Res.string.app_name)
            val BookmarkRemove = OwleIcon(Res.drawable.step, Res.string.app_name)
            val ExecutionTime = OwleIcon(Res.drawable.step, Res.string.app_name)
            val Alert = OwleIcon(Res.drawable.step, Res.string.app_name)
            val Help = OwleIcon(Res.drawable.step, Res.string.app_name)
            val All = listOf(Items, Item, Empty, Add, Remove, DefaultNext, Succeeded, BookmarkAdd, BookmarkRemove, ExecutionTime, Alert, Help)
        }
        object ErrorWatch {
            val Item = OwleIcon(Res.drawable.error_watch, Res.string.app_name)
            val FnFilter = OwleIcon(Res.drawable.error_watch_fn, Res.string.app_name)
            val Triggered = OwleIcon(Res.drawable.error_watch_triggered, Res.string.app_name)
        }
        object Junction {
            val Items = OwleIcon(Res.drawable.junctions, Res.string.app_name)
            val Item = OwleIcon(Res.drawable.junction, Res.string.app_name)
            val Add = OwleIcon(Res.drawable.junction_add, Res.string.app_name)
            val Ignored = OwleIcon(Res.drawable.junction_ignored, Res.string.app_name)
            val Remove = OwleIcon(Res.drawable.junction_remove, Res.string.app_name)
        }
        object PreReqInjection {
            val Item = OwleIcon(Res.drawable.prerequisite, Res.string.app_name)
        }
        object Validator {
            val Items = OwleIcon(Res.drawable.validators, Res.string.app_name)
            val Item = OwleIcon(Res.drawable.validator, Res.string.app_name)
            val Add = OwleIcon(Res.drawable.validator_add, Res.string.app_name)
            val Body = OwleIcon(Res.drawable.validator_body, Res.string.app_name)
            val Empty = OwleIcon(Res.drawable.validator_empty, Res.string.app_name)
            val Ignored = OwleIcon(Res.drawable.validator_ignored, Res.string.app_name)
            val Passed = OwleIcon(Res.drawable.validator_passed, Res.string.app_name)
            val Redirected = OwleIcon(Res.drawable.validator_redirected, Res.string.app_name)
            val Remove = OwleIcon(Res.drawable.validator_remove, Res.string.app_name)
            val Restart = OwleIcon(Res.drawable.validator_restart, Res.string.app_name)
            val Running = OwleIcon(Res.drawable.validator_running, Res.string.app_name)
            val SkipSubsequent = OwleIcon(Res.drawable.validator_skip_subseq, Res.string.app_name)
            val Triggered = OwleIcon(Res.drawable.validator_triggered, Res.string.app_name)
        }
        val All: List<OwleIcon> = Step.All
    }

    object UiCore {

        object Checkmark {
            val Simple = OwleIcon(Res.drawable.check, Res.string.app_name)
            val Circled = OwleIcon(Res.drawable.check_circle, Res.string.app_name)
            val CircledAlt = OwleIcon(Res.drawable.check_circle, Res.string.app_name)
            val All: List<OwleIcon> = listOf(Simple, Circled, CircledAlt)
        }

        object X {
            val Simple = OwleIcon(Res.drawable.x_circle, Res.string.app_name)
            val Circled = OwleIcon(Res.drawable.x_circle, Res.string.app_name)
            val CircledAlt = OwleIcon(Res.drawable.x_circle, Res.string.app_name)
            val All: List<OwleIcon> = listOf(Simple, Circled, CircledAlt)
        }

        object ExecutionControls {
            val PlayFilled = OwleIcon(Res.drawable.execution_start_solid, Res.string.app_name)
            val PlayOutline = OwleIcon(Res.drawable.execution_start_solid, Res.string.app_name)
            val StopFilled = OwleIcon(Res.drawable.execution_start_solid, Res.string.app_name)
            val StopOutline = OwleIcon(Res.drawable.execution_start_solid, Res.string.app_name)
            val Pause = OwleIcon(Res.drawable.execution_start_solid, Res.string.app_name)
            val All: List<OwleIcon> = listOf(PlayFilled, PlayOutline, StopFilled, StopOutline, Pause)
        }

        val Copy = OwleIcon(Res.drawable.copy, Res.string.app_name)
        val Paste = OwleIcon(Res.drawable.paste, Res.string.app_name)
        val OverflowMenu = OwleIcon(Res.drawable.show_overflow_items_circle, Res.string.app_name)
        val Pencil = OwleIcon(Res.drawable.pencil, Res.string.app_name)
        val Settings = OwleIcon(Res.drawable.settings, Res.string.app_name)
        val Trash = OwleIcon(Res.drawable.trash, Res.string.app_name)
        val Clone = OwleIcon(Res.drawable.item_clone, Res.string.app_name)
        val Grip = OwleIcon(Res.drawable.grip_indicator, Res.string.app_name)
        val Alert = OwleIcon(Res.drawable.alert, Res.string.app_name)
        val Hint = OwleIcon(Res.drawable.help_circle, Res.string.app_name)

        val All: List<OwleIcon> = listOf(Copy, Paste, OverflowMenu, Pencil, Settings, Trash, Clone, Grip, Alert, Hint) + Checkmark.All + X.All + ExecutionControls.All
    }

    object Directional {

        object Chevron {
            val Left = OwleIcon(Res.drawable.chevron_left, Res.string.app_name)
            val Right = OwleIcon(Res.drawable.chevron_left, Res.string.app_name)
            val Up = OwleIcon(Res.drawable.chevron_left, Res.string.app_name)
            val Down = OwleIcon(Res.drawable.chevron_down, Res.string.app_name)
            val All = listOf(Left, Right, Up, Down)
        }

        object ArrowCircled {
            val Left = OwleIcon(Res.drawable.arrow_left_circle, Res.string.app_name)
            val Right = OwleIcon(Res.drawable.arrow_right_circle, Res.string.app_name)
            val Up = OwleIcon(Res.drawable.arrow_up_circle, Res.string.app_name)
            val Down = OwleIcon(Res.drawable.arrow_down_circle, Res.string.app_name)
            val All = listOf(Left, Right, Up, Down)
        }

        object Arrow {
            val Left = OwleIcon(Res.drawable.arrow_left, Res.string.app_name)
            val Right = OwleIcon(Res.drawable.arrow_right, Res.string.app_name)
            object All : List<OwleIcon> by listOf(Left, Right)
        }

        val All: List<OwleIcon> = Chevron.All + ArrowCircled.All + Arrow.All
    }

    object ViewResize {

        object Region {
            val Expand = OwleIcon(Res.drawable.expand_fullscreen, Res.string.app_name)
            val Collapse = OwleIcon(Res.drawable.collapse_fullscreen, Res.string.app_name)
            object All : List<OwleIcon> by listOf(Expand, Collapse)
        }

        object Item {
            val Expand = OwleIcon(Res.drawable.expand_item, Res.string.app_name)
            val Collapse = OwleIcon(Res.drawable.collapse_item, Res.string.app_name)
            object All : List<OwleIcon> by listOf(Expand, Collapse)
        }

        object Items {
            val Expand = OwleIcon(Res.drawable.expand_all, Res.string.app_name)
            val Collapse = OwleIcon(Res.drawable.collapse_all, Res.string.app_name)
            object All : List<OwleIcon> by listOf(Expand, Collapse)
        }

        object AllItems {
            val Expand = OwleIcon(Res.drawable.expand_all, Res.string.app_name)
            val Collapse = OwleIcon(Res.drawable.collapse_all, Res.string.app_name)
            object All : List<OwleIcon> by listOf(Expand, Collapse)
        }

        object All : List<OwleIcon> by Region.All + Item.All + Items.All + AllItems.All

    }

    object ToggleStates {

        object Filters {
            val On = OwleIcon(Res.drawable.filter, Res.string.app_name)
            val Off = OwleIcon(Res.drawable.filter_off, Res.string.app_name)
            val All = listOf(On, Off)
        }

        object AccountLogin {
            val LoggedIn = OwleIcon(Res.drawable.account_outline, Res.string.app_name)
            val LoggedOut = OwleIcon(Res.drawable.account_filled, Res.string.app_name)
            val All = listOf(LoggedIn, LoggedOut)
        }

        object CloudExecutions {
            val On = OwleIcon(Res.drawable.execution_cloud, Res.string.app_name)
            val Off = OwleIcon(Res.drawable.execution_cloud_filled, Res.string.app_name)
            val All = listOf(On, Off)
        }

        object Bookmarked {
            val On = OwleIcon(Res.drawable.star, Res.string.app_name)
            val Off = OwleIcon(Res.drawable.circle_bookmark_item, Res.string.app_name)
            val All = listOf(On, Off)
        }

        object All : List<OwleIcon> by Filters.All + AccountLogin.All + CloudExecutions.All + Bookmarked.All

    }

    object All : List<OwleIcon> by OElement.All + UiCore.All + Directional.All
}

//        Icons.OElement.Step.Add,
//        Icons.OElement.Step.Help,
//        Icons.OElement.Step.Item,
//        Icons.OElement.Step.Remove,
//        Icons.OElement.Step.Alert,
//        Icons.OElement.Step.DefaultNext,
//        Icons.OElement.Step.Empty,
//        Icons.OElement.Step.ExecutionTime,
//        Icons.OElement.Step.Items,
//        Icons.OElement.Step.Succeeded,
//        Icons.ActionsIndicators.Directional.Chevron.Left,
//        Icons.ActionsIndicators.Directional.Chevron.Right,
//        Icons.ActionsIndicators.Directional.Chevron.Left,
//        Icons.ActionsIndicators.Directional.Chevron.Down,
//        Icons.ActionsIndicators.Directional.ArrowCircled.Left,
//        Icons.ActionsIndicators.Directional.ArrowCircled.Right,
//        Icons.ActionsIndicators.Directional.ArrowCircled.Up,
//        Icons.ActionsIndicators.Directional.ArrowCircled.Down,
//        Icons.ActionsIndicators.Directional.Arrow.Left,
//        Icons.ActionsIndicators.Directional.Arrow.Right,
//        Icons.ActionsIndicators.ViewResize.Region.Expand,
//        Icons.ActionsIndicators.ViewResize.Region.Collapse,
//        Icons.ActionsIndicators.ViewResize.Item.Expand,
//        Icons.ActionsIndicators.ViewResize.Item.Collapse,
//        Icons.ActionsIndicators.ViewResize.Items.Expand,
//        Icons.ActionsIndicators.ViewResize.Items.Collapse,
//        Icons.ActionsIndicators.ViewResize.AllItems.Expand,
//        Icons.ActionsIndicators.ViewResize.AllItems.Collapse,
//        Icons.ActionsIndicators.ToggleStates.Filters.On,
//        Icons.ActionsIndicators.ToggleStates.Filters.Off,
//        Icons.ActionsIndicators.ToggleStates.AccountLogin.LoggedIn,
//        Icons.ActionsIndicators.ToggleStates.AccountLogin.LoggedOut,
//        Icons.ActionsIndicators.ToggleStates.CloudExecutions.On,
//        Icons.ActionsIndicators.ToggleStates.CloudExecutions.Off,
//        Icons.ActionsIndicators.ToggleStates.Bookmarked.On,
//        Icons.ActionsIndicators.ToggleStates.Bookmarked.Off,
//        Icons.UiCore.Checkmark.Simple,
//        Icons.UiCore.Checkmark.Circled,
//        Icons.UiCore.Checkmark.CircledAlt,
//        Icons.UiCore.X.Simple,
//        Icons.UiCore.X.Circled,
//        Icons.UiCore.X.CircledAlt,
//        Icons.UiCore.Copy,
//        Icons.UiCore.Paste,
//        Icons.UiCore.OverflowMenu,
//        Icons.UiCore.Pencil,
//        Icons.UiCore.Settings,
//        Icons.UiCore.Trash,
//        Icons.UiCore.Grip,
//        Icons.UiCore.Alert,
//        Icons.UiCore.Hint,
//        Icons.UiCore.ExecutionControls.PlayFilled,
//        Icons.UiCore.ExecutionControls.PlayOutline,
//        Icons.UiCore.ExecutionControls.StopFilled,
//        Icons.UiCore.ExecutionControls.StopOutline,
//        Icons.UiCore.ExecutionControls.Pause
