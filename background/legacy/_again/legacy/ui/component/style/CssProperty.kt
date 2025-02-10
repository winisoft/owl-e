package ui.component.style

import org.jetbrains.compose.web.css.*
import ui.component.layout.align.Alignment
import ui.component.layout.align.Arrangement



sealed class CssProperty {

    abstract val key: String
    abstract val value: String

    operator fun StyleScope.invoke() {
        property(key, value)
    }

    fun StyleScope.apply() {
        property(key, value)
    }

    fun CSSStyleRuleBuilder.apply() {
        property(key, value)
    }

    operator fun CSSBuilder.invoke() {
        property(key, value)
    }

    operator fun invoke(scope: StyleScope) {
        scope.property(key, value)
    }

    operator fun invoke(builder: CSSBuilder) {
        builder.property(key, value)
    }

    /**
     * Supports using a more type-safe API for interacting with CSS display properties
     */
    sealed class Display(override val value: String): CssProperty() {

        override val key: String = "display"

        data object Block: Display("block")

        data object Flex: Display("flex")

        data object Grid: Display("grid")

        data object Inline: Display("inline")

        data object None: Display("none")

    }

    /**
     * Supports using a more type-safe API for interacting with CSS position properties
     */
    sealed class Position(override val value: String): CssProperty() {

        override val key: String = "position"

        data object Static: Position("static")

        data object Relative: Position("relative")

        data object Absolute: Position("absolute")

        data object Fixed: Position("fixed")

        data object Sticky: Position("sticky")

    }

    sealed class FlexDirection(override val value: String) : CssProperty() {
        override val key: String = "flex-direction"

        data object Row : FlexDirection("row")
        data object RowReverse : FlexDirection("row-reverse")
        data object Column : FlexDirection("column")
        data object ColumnReverse : FlexDirection("column-reverse")
    }


    data class AlignItems(override val value: String): CssProperty() {
        override val key: String = "align-items"
        constructor(value: Alignment.Vertical): this(value.cssValue)
        constructor(value: Alignment.Horizontal): this(value.cssValue)
    }

    class JustifyContent(override val value: String) : CssProperty() {
        override val key: String = "justify-content"
        constructor(value: Alignment.Vertical) : this(value.cssValue)
        constructor(value: Alignment.Horizontal) : this(value.cssValue)
        constructor(value: Arrangement) : this(value.cssValue)
    }

    sealed class FlexWrap(override val value: String) : CssProperty() {
        override val key: String = "flex-wrap"

        data object NoWrap : FlexWrap("nowrap")
        data object Wrap : FlexWrap("wrap")
        data object WrapReverse : FlexWrap("wrap-reverse")
    }

    sealed class AlignSelf(override val value: String) : CssProperty() {
        override val key: String = "align-self"

        data object Auto : AlignSelf("auto")
        data object FlexStart : AlignSelf("flex-start")
        data object FlexEnd : AlignSelf("flex-end")
        data object Center : AlignSelf("center")
        data object Baseline : AlignSelf("baseline")
        data object Stretch : AlignSelf("stretch")
    }

    sealed class TextAlign(override val value: String) : CssProperty() {
        override val key: String = "text-align"

        data object Left : TextAlign("left")
        data object Right : TextAlign("right")
        data object Center : TextAlign("center")
        data object Justify : TextAlign("justify")
    }



    sealed class Gap private constructor(
        override val value: String
    ) : CssProperty() {

        data class Both(override val value: String): Gap(value) {
            override val key: String = "gap"
            constructor(value: CSSNumeric): this("$value")
            constructor(row: CSSNumeric, column: CSSNumeric): this("$row $column")
        }

        data class Row(override val value: String): Gap(value) {
            override val key: String = "row-gap"
            constructor(value: CSSNumeric): this("$value")
        }

        data class Column(override val value: String): Gap(value) {
            override val key: String = "column-gap"
            constructor(value: CSSNumeric): this("$value")
        }

    }


    sealed class GridTemplateColumns(override val value: String): CssProperty() {

        data class Auto(val size: Int) : GridTemplateColumns("auto-fit, $size")

        data class Repeat(
            override val value: String
        ) : GridTemplateColumns(value) {
            constructor(count: Int, size: Int): this("repeat(${count},${size}fr)")
            constructor(
                fractionalWidths: List<Int>
            ) : this(
                fractionalWidths.map { "${it}fr" }
            )
            constructor(
                fractionalWidths: List<String>
            ) : this(
                "repeat(${fractionalWidths.size},${fractionalWidths.forEach { "${it.toString()} " } })"
            )
        }

        override val key: String = "grid-template-columns"

        override fun toString() = value

    }

    data class CssVariable(val name: String) : CssProperty() {
        override val key: String = "--$name"
        override val value: String = "var(--$name)"
    }

    class Padding(override val value: String): CssProperty() {
        constructor(l: CSSNumeric, r: CSSNumeric, t: CSSNumeric, b: CSSNumeric): this("$t $r $b $l")
        constructor(value: CSSpxValue): this("$value")
        constructor(vertical: CSSpxValue, horizontal: CSSpxValue): this("$vertical $horizontal")
        constructor(top: CSSpxValue, horizontal: CSSpxValue, bottom: CSSpxValue): this("$top $horizontal $bottom")
        constructor(l: CSSpxValue, r: CSSpxValue, t: CSSpxValue, b: CSSpxValue): this("$t $r $b $l")
        constructor(vertical: CSSPercentageValue, horizontal: CSSPercentageValue): this("$vertical $horizontal")
        constructor(top: CSSPercentageValue, horizontal: CSSPercentageValue, bottom: CSSPercentageValue): this("$top $horizontal $bottom")
        constructor(l: CSSPercentageValue, r: CSSPercentageValue, t: CSSPercentageValue, b: CSSPercentageValue): this("$t $r $b $l")

        override val key: String = "padding"
    }

}