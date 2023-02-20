package com.pavelrorecek.core.design

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable

@Composable
public fun BaseScreen(
    content: @Composable () -> Unit,
) {
    Surface(color = MaterialTheme.colors.background) {
        content()
    }
}
