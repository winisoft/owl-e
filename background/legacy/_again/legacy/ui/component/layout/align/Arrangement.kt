package ui.component.layout.align

enum class Arrangement(val cssValue: String) {

    Start("flex-start"),
    End("flex-end"),
    Center("center"),
    SpaceBetween("space-between"),
    SpaceAround("space-around"),
    SpaceEvenly("space-evenly");

    fun toVerticalAlignment(): Alignment.Vertical = when (this) {
        Start -> Alignment.Vertical.Top
        End -> Alignment.Vertical.Bottom
        Center -> Alignment.Vertical.Center
        SpaceBetween,
        SpaceAround,
        SpaceEvenly -> throw IllegalArgumentException(
            "Arrangement.$this cannot be directly mapped to an Alignment.Vertical value."
        )
    }

    fun toHorizontalAlignment(): Alignment.Horizontal = when (this) {
        Start -> Alignment.Horizontal.Start
        End -> Alignment.Horizontal.End
        Center -> Alignment.Horizontal.Center
        SpaceBetween,
        SpaceAround,
        SpaceEvenly -> throw IllegalArgumentException(
            "Arrangement.$this cannot be directly mapped to an Alignment.Horizontal value."
        )
    }
}

