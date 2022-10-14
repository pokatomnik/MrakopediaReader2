package tk.pokatomnik.mrakopediareader2.domain

data class PageMeta(
    val title: String,
    val rating: Int?,
    val voted: Int?,
    val charactersInPage: Int,
    val contentId: String,
    val categories: Set<String>,
    val seeAlso: Set<String>,
)
