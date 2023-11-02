package com.example.tourblog.utils.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tourblog.utils.shimmerEffect


@Composable
fun ShimmeredBox(modifier: Modifier) {
    Box(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clip(shape = MaterialTheme.shapes.small)
            .shimmerEffect()
    )
}