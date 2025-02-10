package ui.component.oelement.region

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.px

@Composable
fun AppLevelRegion(
    content: @Composable () -> Unit
) {

    OElementRegion(content = content)

}

@Composable
fun WorkflowRegion(
    content: @Composable () -> Unit
) {

    OElementRegion(
        minWidth = 900.px,
        content = content
    )

}
