package com.messtick.app.chatdetail.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import messtick.composeapp.generated.resources.Res
import messtick.composeapp.generated.resources.message_placeholder
import messtick.composeapp.generated.resources.send
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTextField(
    onSendClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val richTextState = rememberRichTextState()
    val enableSave by derivedStateOf {
        richTextState.copy().toText().isNotBlank()
    }
    Column {
        RichTextStyleRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            state = richTextState
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RichTextEditor(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                placeholder = { Text(text = stringResource(Res.string.message_placeholder)) },
                keyboardActions = KeyboardActions(onSend = {
                    onSendClick(richTextState.toMarkdown())
                    richTextState.clear()
                }),
                colors = RichTextEditorDefaults.richTextEditorColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = Color.Transparent,
                ),
                state = richTextState,
            )
            /*TextField(
                value = userMessage,
                onValueChange = onUserMessageChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = stringResource(Res.string.message_placeholder)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    onUserMessageChange(userMessage)
                }),
                shape = RoundedCornerShape(32.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )*/

            Button(
                onClick = {
                    onSendClick(richTextState.toMarkdown())
                    richTextState.clear()
                },
                enabled = enableSave,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = stringResource(Res.string.send)
                )
            }
        }
    }

}