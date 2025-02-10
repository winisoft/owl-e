package ui.theme

import org.jetbrains.compose.web.css.*


object OwleTheme {

    enum class Spacing(val value: CSSpxValue) {
        SMALL(8.px),
        MEDIUM(16.px),
        LARGE(24.px);
    }

    object Text {

        enum class Style(val value: String) {
            BOLD("text-bold"),
            NORMAL("text-normal"),
            ITALIC("text-italic");

            override fun toString(): String {
                return value
            }
        }

        enum class Size(val rem: CSSNumeric, val tailwindClass: String) {
            XSM(0.75.cssRem, "text-xs"),
            SM(0.875.cssRem, "text-sm"),
            BASE(1.0.cssRem, "text-base"),
            LG(1.125.cssRem, "text-lg"),
            XL(1.25.cssRem, "text-xl"),
            XL2(1.5.cssRem, "text-2xl"),
            XL3(1.875.cssRem, "text-3xl"),
            XL4(2.25.cssRem, "text-4xl"),
            XL5(3.cssRem, "text-5xl"),
            XL6(3.75.cssRem, "text-6xl"),
            XL7(4.5.cssRem, "text-7xl"),
            XL8(6.cssRem, "text-8xl"),
            XL9(8.cssRem, "text-9xl");
        }

    }


    object Color {

        private val defaultBgAlpha: Float get() = 0.05F
        private val defaultStrokeAlpha: Float get() = 0.5F
        private val hoverBgAlpha: Float get() = 0.12F
        private val hoverStrokeAlpha: Float get() = 0.75F

        private val DefaultTextOn: ColorValue
            get() = object: ColorValue, CSSColorValue by rgba(255, 255, 255, 1.0) {
                override val varName: String get() = "text-active-on-primary"
            }

        private val DefaultTextOnInactive: CSSColorValue
            get() = object: ColorValue, CSSColorValue by rgba(126, 126, 126, 1.0) {
                override val varName: String get() = "text-inactive-on-primary"
            }

        data object Background: ColorValue, CSSColorValue by rgba(12, 12, 12, 1.0F) {

            override val varName: String get() = "background"

            val TextOn: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(210, 210, 210, 1.0F) {
                    override val varName: String get() = "text-active-on-background"
                }

            val TextOnInactive: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(90, 90, 90, 1.0F) {
                    override val varName: String get() = "text-inactive-on-background"
                }
        }

        data object Surface: ColorValue, CSSColorValue by rgba(20, 18, 24, 1.0F) {

            override val varName: String get() = "surface"

            val TextOn: ColorValue
                get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                    override val varName: String get() = "text-active-on-surface"
                }

            val TextOnInactive: ColorValue
                get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                    override val varName: String get() = "text-inactive-on-surface"
                }

            val CardBorder: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(66, 54, 95, 1.0F) {
                    override val varName: String get() = "surface-border"
                }

            val CardBackgroundHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(23, 22, 26, 1.0F) {
                    override val varName: String get() = "surface"
                }

            val CardBorderHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(101, 85, 143, 1.0F) {
                    override val varName: String get() = "surface"
                }
        }

        data object SurfaceTier2: ColorValue, CSSColorValue by rgba(27, 27, 27, 1.0F) {

            override val varName: String get() = "surface-tier2"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-active-on-surface"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-inactive-on-surface"
            }

            val CardBorder: ColorValue get() = object: ColorValue, CSSColorValue by rgba(63, 63, 63, 1.0F) {
                override val varName: String get() = "surface-border"
            }

            val CardBackgroundHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(49, 49, 49, 1.0) {
                    override val varName: String get() = "surface-tier2-hover"
                }

            val CardBorderHovered: ColorValue get() = object: ColorValue, CSSColorValue by rgba(117, 117, 117, 0.85F) {
                override val varName: String get() = "surface-border"
            }

        }

        object Primary: ColorValue, CSSColorValue by rgba(97, 175, 254, 1.0F) {

            override val varName: String get() = "primary"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn  {
                override val varName: String get() = "text-on-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-inactive-primary"
            }

            val CardBackground: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(97, 175, 254,  defaultBgAlpha) {
                    override val varName: String get() = "primary-bg"
                }

            val CardBorder: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(97, 175, 254,  defaultStrokeAlpha) {
                    override val varName: String get() = "primary-border"
                }

            val CardBackgroundHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(97, 175, 254,  hoverBgAlpha) {
                    override val varName: String get() = "primary-bg-hover"
                }

            val CardBorderHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(97, 175, 254,  hoverStrokeAlpha) {
                    override val varName: String get() = "primary-border-hover"
                }
        }

        data object Secondary: ColorValue, CSSColorValue by rgba(96, 221, 161, 1.0F) {

            override val varName: String get() = "secondary"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-on-active-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-inactive-primary"
            }

            val CardBackground: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(96, 221, 161,  defaultBgAlpha) {
                    override val varName: String get() = "secondary-bg"
                }

            val CardBorder: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(96, 221, 161,  defaultStrokeAlpha) {
                    override val varName: String get() = "secondary-border"
                }

            val CardBackgroundHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(96, 221, 161,  hoverBgAlpha) {
                    override val varName: String get() = "secondary-bg-hover"
                }

            val CardBorderHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(97, 175, 254, hoverStrokeAlpha) {
                    override val varName: String get() = "secondary-border-hover"
                }
        }

        data object Tertiary: ColorValue, CSSColorValue by rgba(151, 71, 255, 1.0F) {
            override val varName: String get() = "tertiary"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-on-active-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-active-primary"
            }

            val CardBackground: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(151, 71, 255,  defaultBgAlpha) {
                    override val varName: String get() = "tertiary-bg"
                }

            val CardBorder: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(151, 71, 255,  defaultStrokeAlpha) {
                    override val varName: String get() = "tertiary-border"
                }

            val CardBackgroundHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(151, 71, 255,  hoverBgAlpha) {
                    override val varName: String get() = "tertiary-bg-hover"
                }

            val CardBorderHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by  rgba(151, 71, 255, hoverStrokeAlpha) {
                    override val varName: String get() = "tertiary-border-hover"
                }
        }

        data object Info: ColorValue, CSSColorValue by rgba(255, 167, 129, 1.0F) {

            override val varName: String get() = "info"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-active-on-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-inactive-on-primary"
            }

            val CardBackground: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(255, 167, 129,  defaultBgAlpha) {
                    override val varName: String get() = "info-bg"
                }

            val CardBorder: ColorValue
                get() = object: ColorValue, CSSColorValue by  rgba(255, 167, 129,  defaultStrokeAlpha) {
                    override val varName: String get() = "info-border"
                }

            val CardBackgroundHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(255, 167, 129,  hoverBgAlpha) {
                    override val varName: String get() = "info-bg-hovered"
                }

            val CardBorderHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(255, 167, 129, hoverStrokeAlpha) {
                    override val varName: String get() = "info-border-hovered"
                }
        }

        data object PrimaryVariant: ColorValue, CSSColorValue by rgba(45, 86, 125, 1.0F) {

            override val varName: String get() = "primary-variant"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-on-active-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-inactive-primary"
            }
        }

        data object InfoVariant: ColorValue, CSSColorValue by rgba(255, 146, 85, 1.0F) {

            override val varName: String get() = "info-variant"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-on-active-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-inactive-primary"
            }
        }

        data object Error: ColorValue, CSSColorValue by rgba(179, 38, 30, 1.0F){

            override val varName: String get() = "error"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-on-active-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-active-primary"
            }

        }

        data object SurfaceVariant: ColorValue, CSSColorValue by rgba(0, 0, 0, 1.0F) {

            override val varName: String get() = "surface-variant"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-on-active-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-active-primary"
            }

            val CardBorder: ColorValue
                get() = object: ColorValue, CSSColorValue by  rgba(255, 167, 129,  defaultStrokeAlpha) {
                    override val varName: String get() = "surface-variant-border"
                }

            val CardBackgroundHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(0, 0, 0,  hoverBgAlpha) {
                    override val varName: String get() = "surface-variant"
                }

            val CardBorderHovered: ColorValue
                get() = object: ColorValue, CSSColorValue by rgba(255, 255, 255, hoverStrokeAlpha) {
                    override val varName: String get() = "surface-variant-border-hover"
                }
        }

        data object SecondaryVariant: ColorValue, CSSColorValue by rgba(51, 187, 176, 1.0F){

            override val varName: String get() = "secondary-variant"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-on-active-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-active-primary"
            }
        }

        data object TertiaryVariant: ColorValue, CSSColorValue by rgba(101, 85, 143, 1.0F){

            override val varName: String get() = "tertiary-variant"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-on-active-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-active-primary"
            }
        }

        data object PythonFunction: ColorValue, CSSColorValue by rgba(220, 220, 170, 1.0F){

            override val varName: String get() = "python-function"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-on-active-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-active-primary"
            }
        }

        data object PythonModule: ColorValue, CSSColorValue by rgba(51, 187, 176, 1.0F){

            override val varName: String get() = "python-module"

            val TextOn: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOn {
                override val varName: String get() = "text-on-active-primary"
            }

            val TextOnInactive: ColorValue get() = object: ColorValue, CSSColorValue by DefaultTextOnInactive {
                override val varName: String get() = "text-on-active-primary"
            }
        }

    }

    interface ColorValue: CSSColorValue {
        val varName: String
        val textClass: String get() = "text-(--$varName)"
        val bgClass: String get() = "bg-(--$varName)"
        val value: CSSColorValue get() = this
    }

}

