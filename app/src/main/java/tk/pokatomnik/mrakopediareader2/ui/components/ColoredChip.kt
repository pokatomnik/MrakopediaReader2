package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.concurrent.ConcurrentHashMap

val colorsMap: MutableMap<String, Pair<Color, Color>> = ConcurrentHashMap()
fun memoizeAndGetColor(title: String): Pair<Color, Color> {
    val existing = colorsMap[title]
    return existing ?: colorsMap.let {
        val rb = (180..255).random()
        val gb = (180..255).random()
        val bb = (180..255).random()
        val background = Color(rb, gb, bb)
        val rf = 255 - rb
        val gf = 255 - gb
        val bf = 255 - bb
        val grey = ((rf + gf + bf) / 3)
        val foreground = Color(grey, grey, grey)
        val pair = background to foreground
        colorsMap[title] = pair
        pair
    }
}

@Composable
fun ColoredChip(
    text: String,
    modifier: Modifier = Modifier,
    maxChars: Int = 10,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .border(
                border = ButtonDefaults.outlinedBorder,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                onClick = onClick,
                indication = rememberRipple(bounded = true),
                enabled = true,
                interactionSource = remember { MutableInteractionSource() }
            )
            .background(memoizeAndGetColor(text).first)
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .height(IntrinsicSize.Min),
    ) {
        Text(
            text = if (text.length > maxChars) "${text.slice(0..maxChars)}…" else text,
            color = memoizeAndGetColor(text).second, maxLines = 1
        )
    }
}