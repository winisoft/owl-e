package com.wini.owle.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import com.wini.owle.res.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringArrayResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringArrayResource

enum class ResourceGroup(val id: Int) {
    ICONS(1),
    STRINGS(2)
}

enum class IconGroup(val id: Int) {
    OELEMENT(1),
    ACTIONS_INDICATORS(2),
    MODULE_CONTENT(3),
    PAYMENTS(4)
}

enum class OElementGroup(val id: Int) {
    WORKFLOW(1),
    FOLDER(2),
    DATA_ITEM(3),
    STEP(4),
    ERROR_WATCH(5),
    JUNCTION(6),
    PREREQ_INJECTION(7),
    VALIDATOR(8);
}

data class OwleIcon(
    val drawable: DrawableResource,
    val stringArray: StringArrayResource
) {

    val name: @Composable () -> String get() = {
        stringArrayResource(stringArray).run{
            if (isNotEmpty()) "" else get(0)
        }
    }

    val contentDescription: @Composable () -> String get() = {
        stringArrayResource(stringArray).run{
            if (size < 2) "" else get(1)
        }
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
        object Workflow {}
        object Folder {}
        object DataItem {}
        object Step {
            val Items = OwleIcon(drawable = Res.drawable.steps, stringArray = Res.array.ic_1_1_1)
            val Item = OwleIcon(drawable = Res.drawable.step, stringArray = Res.array.ic_1_1_2)
            val Empty = OwleIcon(drawable = Res.drawable.step_empty, stringArray = Res.array.ic_1_1_3)
            val Add = OwleIcon(drawable = Res.drawable.step, stringArray = Res.array.ic_1_1_4)
            val Remove = OwleIcon(drawable = Res.drawable.step, stringArray = Res.array.ic_1_1_5)
            val DefaultNext = OwleIcon(drawable = Res.drawable.step, stringArray = Res.array.ic_1_1_6)
            val Succeeded = OwleIcon(drawable = Res.drawable.step, stringArray = Res.array.ic_1_1_7)
            val BookmarkAdd = OwleIcon(drawable = Res.drawable.step, stringArray = Res.array.ic_1_1_8)
            val BookmarkRemove = OwleIcon(drawable = Res.drawable.step, stringArray = Res.array.ic_1_1_9)
            val ExecutionTime = OwleIcon(drawable = Res.drawable.step, stringArray = Res.array.ic_1_1_10)
            val Alert = OwleIcon(drawable = Res.drawable.step, stringArray = Res.array.ic_1_1_11)
            val Help = OwleIcon(Res.drawable.step, Res.array.ic_1_1_12)
            val All = listOf(Items, Item, Empty, Add, Remove, DefaultNext, Succeeded, BookmarkAdd, BookmarkRemove, ExecutionTime, Alert, Help)
        }
        object ErrorWatch {}
        object Junction {}
        object PreReqInjection {}
        object Validator {}
        val All: List<OwleIcon> = Step.All
    }

    object UiCore {

        object Checkmark {
            val Simple = OwleIcon(Res.drawable.check, Res.array.ic_1_2_8_1_1)
            val Circled = OwleIcon(Res.drawable.check_circle, Res.array.ic_1_2_8_1_2)
            val CircledAlt = OwleIcon(Res.drawable.check_circle, Res.array.ic_1_2_8_1_3)
            val All: List<OwleIcon> = listOf(Simple, Circled, CircledAlt)
        }

        object X {
            val Simple = OwleIcon(Res.drawable.x_circle, Res.array.ic_1_2_8_2_1)
            val Circled = OwleIcon(Res.drawable.x_circle, Res.array.ic_1_2_8_2_1)
            val CircledAlt = OwleIcon(Res.drawable.x_circle, Res.array.ic_1_2_8_2_1)
            val All: List<OwleIcon> = listOf(Simple, Circled, CircledAlt)
        }

        object ExecutionControls {
            val PlayFilled = OwleIcon(Res.drawable.execution_start_solid, Res.array.ic_1_2_9_1)
            val PlayOutline = OwleIcon(Res.drawable.execution_start_solid, Res.array.ic_1_2_9_2)
            val StopFilled = OwleIcon(Res.drawable.execution_start_solid, Res.array.ic_1_2_9_3)
            val StopOutline = OwleIcon(Res.drawable.execution_start_solid, Res.array.ic_1_2_9_4)
            val Pause = OwleIcon(Res.drawable.execution_start_solid, Res.array.ic_1_2_9_5)
            val All: List<OwleIcon> = listOf(PlayFilled, PlayOutline, StopFilled, StopOutline, Pause)
        }

        val Copy = OwleIcon(Res.drawable.copy, Res.array.ic_1_2_8_3)
        val Paste = OwleIcon(Res.drawable.paste, Res.array.ic_1_2_8_4)
        val OverflowMenu = OwleIcon(Res.drawable.show_overflow_items_circle, Res.array.ic_1_2_8_5)
        val Pencil = OwleIcon(Res.drawable.pencil, Res.array.ic_1_2_8_6)
        val Settings = OwleIcon(Res.drawable.settings, Res.array.ic_1_2_8_7)
        val Trash = OwleIcon(Res.drawable.trash, Res.array.ic_1_2_8_8)
        val Clone = OwleIcon(Res.drawable.item_clone, Res.array.ic_1_2_8_9)
        val Grip = OwleIcon(Res.drawable.grip_indicator, Res.array.ic_1_2_8_10)
        val Alert = OwleIcon(Res.drawable.alert, Res.array.ic_1_2_8_11)
        val Hint = OwleIcon(Res.drawable.help_circle, Res.array.ic_1_2_8_12)

        val All: List<OwleIcon> = listOf(Copy, Paste, OverflowMenu, Pencil, Settings, Trash, Clone, Grip, Alert, Hint) + Checkmark.All + X.All + ExecutionControls.All
    }

    object Directional {

        object Chevron {
            val Left = OwleIcon(Res.drawable.chevron_left, Res.array.ic_1_2_1_1)
            val Right = OwleIcon(Res.drawable.chevron_left, Res.array.ic_1_2_1_2)
            val Up = OwleIcon(Res.drawable.chevron_left, Res.array.ic_1_2_1_3)
            val Down = OwleIcon(Res.drawable.chevron_down, Res.array.ic_1_2_1_4)
            val All = listOf(Left, Right, Up, Down)
        }

        object ArrowCircled {
            val Left = OwleIcon(Res.drawable.arrow_left_circle, Res.array.ic_1_2_2_1)
            val Right = OwleIcon(Res.drawable.arrow_right_circle, Res.array.ic_1_2_2_2)
            val Up = OwleIcon(Res.drawable.arrow_up_circle, Res.array.ic_1_2_2_3)
            val Down = OwleIcon(Res.drawable.arrow_down_circle, Res.array.ic_1_2_2_4)
            val All = listOf(Left, Right, Up, Down)
        }

        object Arrow {
            val Left = OwleIcon(Res.drawable.arrow_left, Res.array.ic_1_2_3_1)
            val Right = OwleIcon(Res.drawable.arrow_right, Res.array.ic_1_2_3_1)
            object All : List<OwleIcon> by listOf(Left, Right)
        }

        val All: List<OwleIcon> = Chevron.All + ArrowCircled.All + Arrow.All
    }

    object ViewResize {

        object Region {
            val Expand = OwleIcon(Res.drawable.expand_fullscreen, Res.array.ic_1_2_4_1_1)
            val Collapse = OwleIcon(Res.drawable.collapse_fullscreen, Res.array.ic_1_2_4_1_2)
            object All : List<OwleIcon> by listOf(Expand, Collapse)
        }

        object Item {
            val Expand = OwleIcon(Res.drawable.expand_item, Res.array.ic_1_2_4_2_1)
            val Collapse = OwleIcon(Res.drawable.collapse_item, Res.array.ic_1_2_4_2_2)
            object All : List<OwleIcon> by listOf(Expand, Collapse)
        }

        object Items {
            val Expand = OwleIcon(Res.drawable.expand_all, Res.array.ic_1_2_4_3_1)
            val Collapse = OwleIcon(Res.drawable.collapse_all, Res.array.ic_1_2_4_3_2)
            object All : List<OwleIcon> by listOf(Expand, Collapse)
        }

        object AllItems {
            val Expand = OwleIcon(Res.drawable.expand_all, Res.array.ic_1_2_4_4_1)
            val Collapse = OwleIcon(Res.drawable.collapse_all, Res.array.ic_1_2_4_4_2)
            object All : List<OwleIcon> by listOf(Expand, Collapse)
        }

        object All : List<OwleIcon> by Region.All + Item.All + Items.All + AllItems.All

    }

    object ToggleStates {

        object Filters {
            val On = OwleIcon(Res.drawable.filter, Res.array.ic_1_2_7_1_1)
            val Off = OwleIcon(Res.drawable.filter_off, Res.array.ic_1_2_7_1_2)
            val All = listOf(On, Off)
        }

        object AccountLogin {
            val LoggedIn = OwleIcon(Res.drawable.account_outline, Res.array.ic_1_2_7_2_1)
            val LoggedOut = OwleIcon(Res.drawable.account_filled, Res.array.ic_1_2_7_2_1)
            val All = listOf(LoggedIn, LoggedOut)
        }

        object CloudExecutions {
            val On = OwleIcon(Res.drawable.execution_cloud, Res.array.ic_1_2_7_3_1)
            val Off = OwleIcon(Res.drawable.execution_cloud_filled, Res.array.ic_1_2_7_3_1)
            val All = listOf(On, Off)
        }

        object Bookmarked {
            val On = OwleIcon(Res.drawable.star, Res.array.ic_1_2_7_4_1)
            val Off = OwleIcon(Res.drawable.circle_bookmark_item, Res.array.ic_1_2_7_4_1)
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
