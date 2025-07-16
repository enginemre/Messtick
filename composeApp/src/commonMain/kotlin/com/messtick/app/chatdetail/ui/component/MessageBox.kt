package com.messtick.app.chatdetail.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@Composable
fun MessageBox(
    message: RichTextState,
    fromChatOwner: Boolean,
    modifier: Modifier = Modifier
) {
    val shape = remember {
        RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = if (fromChatOwner) 16.dp else 0.dp,
            bottomEnd = if (fromChatOwner) 0.dp else 16.dp
        )
    }
    val color =
        if (fromChatOwner) MaterialTheme.colorScheme.surfaceContainerHigh else MaterialTheme.colorScheme.surfaceContainerLow
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
    ) {
        RichText(
            state = message,
            modifier = Modifier.padding(16.dp),
            textAlign = if (fromChatOwner) TextAlign.End else TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}