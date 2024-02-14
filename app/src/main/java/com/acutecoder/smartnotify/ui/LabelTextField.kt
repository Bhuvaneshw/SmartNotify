package com.acutecoder.smartnotify.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acutecoder.smartnotify.ui.theme.ThemeColors

/**
 * Created by Bhuvaneshwaran
 *
 * 11:09 pm 13-02-2024
 * @author AcuteCoder
 */

@Composable
fun LabeledTextField(
    label: String,
    state: MutableState<String>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .height(IntrinsicSize.Max)
            .clip(RoundedCornerShape(8.dp))
            .background(ThemeColors.primary.copy(alpha = 0.2f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .widthIn(min = 100.dp)
                .background(ThemeColors.primary.copy(alpha = 0.2f))
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(label, fontSize = 16.sp)
        }

        BasicTextField(
            value = state.value,
            onValueChange = { state.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = ThemeColors.dark
            ),
            cursorBrush = SolidColor(ThemeColors.dark),
            interactionSource = MutableInteractionSource()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val text = remember { mutableStateOf("Sample Text\n2") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp), verticalArrangement = Arrangement.Center
    ) {
        LabeledTextField(label = "Label", state = text)
    }
}