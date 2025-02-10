package ui.component.layout.align


class Alignment {

    enum class Vertical(val cssValue: String) {
        Top("flex-start"),
        Bottom("flex-end"),
        Center("center");

        fun toArrangement(): Arrangement {
            return when (this) {
                Top -> Arrangement.Start
                Center -> Arrangement.Center
                Bottom -> Arrangement.End
            }
        }
    }

    enum class Horizontal(val cssValue: String) {
        Start("flex-start"),
        End("flex-end"),
        Center("center");

        fun toArrangement(): Arrangement {
            return when (this) {
                Alignment.Horizontal.Start -> Arrangement.Start
                Alignment.Horizontal.Center -> Arrangement.Center
                Alignment.Horizontal.End -> Arrangement.End
            }
        }
    }
}
