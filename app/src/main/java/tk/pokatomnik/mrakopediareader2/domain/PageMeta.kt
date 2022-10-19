package tk.pokatomnik.mrakopediareader2.domain

import tk.pokatomnik.mrakopediareader2.ui.components.ratingToEmoji
import kotlin.math.ceil

data class PageMeta(
    val title: String,
    val rating: Int?,
    val voted: Int?,
    val charactersInPage: Int,
    val contentId: String,
    val categories: Set<String>,
    val seeAlso: Set<String>,
) {
    fun formatDescription(): String {
        val readingTime = ceil(charactersInPage.toFloat() / 1500).toInt()
        return "⌚$readingTime мин. ${ratingToEmoji(rating ?: 0)} ${rating ?: 0}%/${voted ?: 0} человек"
    }
}
