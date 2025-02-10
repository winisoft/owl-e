package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.model.ParentItem
import com.wini.owle.air.domain.repo.ModelToEntityMapper
import com.wini.owle.air.inlines.*


sealed class LoItem {

    abstract val loid: Long

    /**
     * The text to be displayed as a summary of this item in the list at panel left. If the text is two-row text,
     * it only holds the top row.
     */
    abstract val displayTextMain: String

    /**
     * In items that display as two rows of text, this is the lower.
     */
    abstract val displayTextSub: String?

    /**
     * Indicates the number of ancestors this implementation of LoItem has, to be used in spacing elements
     * of the list item as appropriate in the hierarchy.
     */
    abstract val depth: Int

    /**
     * The icon representing this item, styled as a 'filled' material icon to suggest at a glance the
     * item's active or inactive function in the app.
     */
    abstract val enabledIcon: ImageVector

    /**
     * The icon representing this item, styled as an 'outline' material icon to suggest at a glance that
     * it is not currently an element the user can expect mock results for. When any item is disabled,
     * the items it is logically ancestor to are themselves disabled until it is again enabled. Thus when an
     * API is disabled, all of the mock calls under all of its endpoints are disabled.
     */
    abstract val disabledImplicitIcon: ImageVector

    /**
     * Represents whether this item is explicitly set enabled, to be an accessible mock data element returned
     * under the conditions that trigger it as the intercepted call's / intercepted property request's result,
     * or disabled explicitly, such that even if its conditions are met, the result will not be triggered.
     */
    abstract val isEnabled: IsEnabled

    abstract val name: Name

    /**
     * Offers the user an alternative name to be presented for this item that explains its purpose at a glance.
     * For example, if an environment has a Owner Manual API configured so that no data is returned by any endpoint,
     * one can simply override its name to 'No Results Owner Manual' and thus not need to drill down into it to examine
     * its configured [Call]s to understand its purpose in context.
     *
     * For the purposes of noting the behavior of some configuration beyond this ~100 characters, use [LoItem.description]
     */
    abstract val overrideName: OverrideName?

    /**
     * Convenience property that describes in user-friendly text what this item models. This serves as a fallback
     * in the [LoItem.displayTextMain] property, but also adds clarity in debugging / logging.
     */
    abstract val typeName: String

    /**
     * Indicates whether this item is currently meant to be displayed in the list. An item is only visible when all
     * ancestor items above it in the hierarchy have an [isExpanded] value of true.
     */
    abstract val isVisible: Boolean

    /**
     * Indicates whether this item has an 'implicit' disabled state, owing to one of its ancestors being disabled.
     * It is possible for both this and [IsEnabled] to be false concurrently, that is to say, explicitly disabling this
     * item does not negate an implicit disablement and result in the property being set false, nor does enabling it
     * when all of its ancestors are disabled only then trigger this value to be set true.
     */
    abstract val isImplicitDisable: Boolean

    abstract val isHorizontalDisable: IsHorizontalDisabled

    abstract val breadcrumbText: String

    fun getDisablementMessage(ancestors: List<LoItem>): String {
        return when {
            ancestors.all { it.isEnabled.value }
            && enabledState == EnabledState.ENABLED
                -> "This $typeName is enabled and responding."
            enabledState == EnabledState.IMPLICITLY_DISABLED
            && ancestors.count { it.isEnabled.value.not() } > 1
                -> "This $typeName is explicitly disabled (and implicitly disabled under " +
                    "${ancestors.count { it.isEnabled.value.not() }} disabled parents)"
            enabledState == EnabledState.IMPLICITLY_DISABLED
                -> "This $typeName is explicitly disabled (and implicitly disabled under its parent " +
                        "${ancestors.first { it.isEnabled.value.not() }.typeName})"
            enabledState == EnabledState.EXPLICITLY_DISABLED
                -> "This $typeName is explicitly disabled."
            else ->
                "" // nonsense condition
        }
    }

    /**
     * The commonly used icon that indicates this item is explicitly set disabled, as opposed to being
     * disabled implicitly with an explicitly disabled ancestor. If an item is both implicitly and explicitly
     * disabled, the explicit indicator is displayed, as it will remain disabled even if its every ancestor
     * becomes enabled.
     */
    open val disabledExplicitIcon: ImageVector
        get() = Icons.Default.Dangerous

    /**
     * Determines which of three icons this item should currently display, one indicatin the item is enabled, disabled
     * implicitly, or explicitly disabled.
     *
     * @param isServerRunning Because the [LoRootItem] has an [IsEnabled]
     *      state bound to the current status of the server, that is, it is not immediately enabled or disabled like all
     *      other items but rather only enabled or disabled as the server starts and stops, and because as the first ancestor
     *      it affects all other items by triggering implicit disablement of all the rest, and must be taken into account.

     */
    open val statefulIcon: ImageVector
        get() = when (enabledState) {
            EnabledState.ENABLED -> enabledIcon
            EnabledState.IMPLICITLY_DISABLED -> disabledImplicitIcon
            else -> disabledExplicitIcon
        }

    //TODO: RENAME IS_IMPLICIT_DISABLE to IS_VERTICAL_DISABLE -- the db should be comprehensibility prioritized,
    //so it was a mistake to combine vertical & horizontal into implicit disable there. it has been updated already to not consider
    //a horizontal disable as qualifying for an implicit disable already
    open val enabledState: EnabledState
        get() = when {
            !isImplicitDisable && !isHorizontalDisable.value && isEnabled.value -> EnabledState.ENABLED
            isImplicitDisable || isHorizontalDisable.value -> EnabledState.IMPLICITLY_DISABLED
            else -> EnabledState.EXPLICITLY_DISABLED
        }

    val breadCrumb: Triple<ImageVector, String, LoItem>
        get() = Triple(statefulIcon, breadcrumbText, this)

    val isTwoRowText: Boolean
        get() = displayTextSub != null

    //this is always an LoItem; compiler fails to note, but an "if (this is LoItem)" check warns 'always true'
    @Suppress("UNCHECKED_CAST")
    val <T : LoItem> T.copyEnabledFalse: T get() =
        if (!isEnabled.value)
            this
        else
            copyEnabledToggled() as T

    fun copyEnabledToggled(): LoItem = IsEnabled(isEnabled.value.not()).let {
        when (this) {
            is LoRootItem -> return this
            is Environment ->
                copy(isEnabled = it)
            is CalOverrideGroup ->
                copy(isEnabled = it)
            is CalOverride ->
                copy(isEnabled = it)
            is CarOverrideGroup ->
                copy(isEnabled = it)
            is CarPropOverride ->
                copy(isEnabled = it)
            is SysPropOverrideGroup ->
                copy(isEnabled = it)
            is SystemPropOverride ->
                copy(isEnabled = it)
            is SharedPrefOverrideGroup ->
                copy(isEnabled = it)
            is SharedPrefOverride ->
                copy(isEnabled = it)
            is ApisGroup ->
                copy(isEnabled = it)
            is ApiOverride ->
                copy(isEnabled = it)
            is EndpointOverride ->
                copy(isEnabled = it)
            is Call ->
                copy(isEnabled = it)
            else -> error("")
        }.run {
            this as LoItem
        }
    }


    fun copyExpandedToggled(): LoItem = if (this !is ParentItem) this else IsExpanded(isExpanded.value.not()).let { toggledIsExpanded ->
        when (this) {
            is LoRootItem ->
                copy(isExpanded = toggledIsExpanded)
            is Environment ->
                copy(isExpanded = toggledIsExpanded)
            is CalOverrideGroup ->
                copy(isExpanded = toggledIsExpanded)
            is CarOverrideGroup ->
                copy(isExpanded = toggledIsExpanded)
            is SysPropOverrideGroup ->
                copy(isExpanded = toggledIsExpanded)
            is SharedPrefOverrideGroup ->
                copy(isExpanded = toggledIsExpanded)
            is ApisGroup ->
                copy(isExpanded = toggledIsExpanded)
            is ApiOverride ->
                copy(isExpanded = toggledIsExpanded)
            is EndpointOverride ->
                copy(isExpanded = toggledIsExpanded)
            else ->
                this
        }
    }

    enum class EnabledState {
        ENABLED,
        IMPLICITLY_DISABLED,
        EXPLICITLY_DISABLED
    }

    open fun breadcrumbHoverOver(items: List<LoItem>): String = items.run {
        if (this@LoItem is LoRootItem) typeName else "$typeName '$breadcrumbText'" + when {
            enabledState == EnabledState.EXPLICITLY_DISABLED -> " is explicitly disabled."
            isHorizontalDisable.value && enabledState == EnabledState.ENABLED -> " is horizontally disabled, i.e. another $typeName is enabled instead."
            enabledState == EnabledState.ENABLED -> " is current enabled."
            else -> {
                " is implicitly disabled under its parent " + items.filter { !it.isEnabled.value }.run {
                    size to map { it.typeName }
                }.let { (count, names) ->
                    when (count) {
                        1 -> names[0]
                        2 -> "${names[0]} and ${names[1]}"
                        else -> StringBuilder().apply {
                            names.subList(0, count - 1).forEach { append("$it, ") }
                            append("and " + names.last())
                        }.toString()
                    }
                }.let {
                    if (!isHorizontalDisable.value)
                        it
                    else
                        "$it, and horizontally disabled, i.e. another $typeName is active instead"
                }
            }
        }
    }

    interface LoItemBuilder<T: Any>: ModelToEntityMapper<LoItemBuilder<T>, T> {
        operator fun invoke(block: LoItemBuilder<T>.() -> Unit) = run { apply(block); build() }

        val isValid: MutableState<Boolean>

        fun build(): SaveActions<T>
    }
}