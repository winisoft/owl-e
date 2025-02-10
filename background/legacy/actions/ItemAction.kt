package com.wini.owle.ui.actions

import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.api.Api
import com.wini.owle.air.domain.model.*
import kotlin.reflect.KClass

sealed interface Action

object CancelEdit: Action

sealed interface ItemAction {
    val item: LoItem
}

data class ToggleItemIsExpanded(override val item: LoItem): ItemAction
data class ItemClicked(override val item: LoItem): ItemAction
data class ItemIconClicked(override val item: LoItem): ItemAction
data class ToggleItemIsEnabled(override val item: LoItem): ItemAction
data class OpenApiClicked(override val item: ApiOverride): ItemAction
data class AddChildItemClicked(override val item: LoItem): ItemAction
data class EditItemClicked(override val item: LoItem): ItemAction
data class DeleteItemClicked(override val item: LoItem): ItemAction
data class DuplicateItemClicked(override val item: LoItem): ItemAction
data class CloneItemClicked(override val item: LoItem): ItemAction
data class EditCanceled(override val item: LoItem): ItemAction
data class ExpandRequestJsonClicked(override val item: Call): ItemAction
data class ExpandResponseJsonClicked(override val item: Call): ItemAction
data class ExpandRequestParamsClicked(override val item: Call): ItemAction
data class ExpandRequestHeadersClicked(override val item: Call): ItemAction
data class ExpandResponseHeadersClicked(override val item: Call): ItemAction

data class CancelEditClicked(override val item: LoItem): ItemAction
data class SaveItemEdit<T>(override val item: LoItem, val saveActions: SaveActions<T>): ItemAction

data class AddOpenApiSpec(override val item: LoItem): ItemAction

data class CategoryClicked(override val item: Environment, val category: CategoryType): ItemAction

val list = listOf(String::class)

data class FilterApiAxioms(
    val filter: ((KClass<Api>) -> Boolean) -> List<KClass<Api>>
)