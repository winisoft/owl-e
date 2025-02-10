package com.wini.owle.preview

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wini.owle.ui.theme.OwleTheme

@Composable
fun ColumnScope.PreviewItemLabel(text: String) {

    Text(
        text = text,
        color = OwleTheme.colors.onBackground,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
    )
}