package tk.pokatomnik.mrakopediareader2.domain

import kotlin.math.ceil

data class PageMeta(
    val title: String,
    val rating: Int?,
    val voted: Int?,
    val charactersInPage: Int,
    val contentId: String,
    val categories: Collection<String>,
    val seeAlso: Collection<String>,
    val images: Collection<ImageInfo>
) {
    fun formatDescription(): String {
        val readingTime = ceil(charactersInPage.toFloat() / 1500).toInt()
        return "$readingTime мин. ${rating ?: 0}%/${voted ?: 0} человек"
    }
}
