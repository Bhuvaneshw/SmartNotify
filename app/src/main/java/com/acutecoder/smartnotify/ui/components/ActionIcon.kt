package com.acutecoder.smartnotify.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * Created by Bhuvaneshwaran
 *
 * 09:46 pm 16-02-2024
 * @author AcuteCoder
 */

@Composable
fun ActionIcon(icon: ImageVector, onClick: () -> Unit) {
    Icon(
        imageVector = icon, contentDescription = null,
        modifier = Modifier
            .width(45.dp)
            .height(45.dp)
            .padding(4.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(8.dp)
    )
}
