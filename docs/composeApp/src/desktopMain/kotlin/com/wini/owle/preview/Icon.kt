package com.wini.owle.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wini.owle.ui.OwleIcon
import com.wini.owle.ui.Icons
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun PreviewIcon(
    icon: OwleIcon
) {

    Column {

        Box(
            modifier = Modifier
                .size(36.dp)
                .padding(6.dp)
        ) {
            Image(
                painter = painterResource(resource = icon.drawable),
                contentDescription = icon.contentDescription()
            )
        }

        PreviewItemLabel(text = icon.name())
    }


}

@Composable
@Preview
fun PreviewIcons(icons: List<OwleIcon> = Icons.All) {

    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(8),
        modifier = Modifier,
        state = lazyGridState,
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        items(icons.size) {
            PreviewIcon(icons[it])
        }
    }
}

