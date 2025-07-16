package com.messtick.app.home.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.skydoves.landscapist.coil3.CoilImage


@Composable
fun ChatItemComponent(
    fromPerson: String,
    lastMessage: String,
    imageUrl: String?,
    unread: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        onClick = onClick
    ) {
        ListItem(
            modifier = Modifier.padding(horizontal = 4.dp),
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            leadingContent = {
                if (imageUrl == null) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                } else {
                    CoilImage(
                        imageModel = { imageUrl },
                        modifier = Modifier.size(60.dp).clip(CircleShape),
                        failure = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        },
                        loading = {
                            CircularProgressIndicator()
                        }
                    )
                }
            },
            headlineContent = {
                Text(
                    text = fromPerson,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            },
            supportingContent = {
                if (lastMessage.isNotBlank()) {
                    val state = RichTextState().apply { setMarkdown(lastMessage) }
                    RichText(
                        state = state,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            trailingContent = {
                if (unread) {
                    Badge()
                }
            }
        )
    }
}