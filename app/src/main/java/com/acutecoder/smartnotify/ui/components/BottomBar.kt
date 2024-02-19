package com.acutecoder.smartnotify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acutecoder.smartnotify.ui.on
import com.acutecoder.smartnotify.ui.theme.ThemeColors

/**
 * Created by Bhuvaneshwaran
 *
 * 10:19 am 19-02-2024
 * @author AcuteCoder
 */

@Composable
fun BottomBar(page: Int, onPageChange: (Int) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(ThemeColors.light)
            .padding(6.dp)
    ) {
        BotMenuItem("Home", Icons.Default.Home, page == 0) { onPageChange(0) }
        BotMenuItem("Template", Icons.Default.Settings, page == 1) { onPageChange(1) }
        BotMenuItem("Help", Icons.Default.Info, page == 2) { onPageChange(2) }
    }
}

@Composable
fun RowScope.BotMenuItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .weight(1f)
            .clip(RoundedCornerShape(5.dp))
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon, contentDescription = null,
            modifier = Modifier
                .width(65.dp)
                .height(45.dp)
                .padding(4.dp)
                .clip(CircleShape)
                .on(selected) {
                    background(ThemeColors.lightBlue)
                }
                .padding(8.dp)
        )
        Text(title, fontSize = 12.sp)
    }
}
