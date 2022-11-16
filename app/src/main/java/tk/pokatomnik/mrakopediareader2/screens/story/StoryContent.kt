package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import dev.jeziellago.compose.markdowntext.MarkdownText
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences

@Composable
fun StoryContent(
    content: String,
    fontSize: Int,
) {
    val pagePreferences = rememberPreferences().pagePreferences
    val fontResourceId = pagePreferences.storyFontsMap[pagePreferences.contentFontFamily]
    key(fontSize) {
        MarkdownText(
            markdown = content,
            textAlign = TextAlign.Justify,
            fontSize = fontSize.sp,
            disableLinkMovementMethod = true,
            fontResource = fontResourceId
        )
    }
}