package tk.pokatomnik.mrakopediareader2.domain

import tk.pokatomnik.mrakopediareader2.services.textassetresolver.TextAssetResolver

class Category(
    val name: String,
    private val items: Map<String, PageMeta>,
    private val textContentResolver: TextAssetResolver
) {
    val size: Int
    get() = items.size

    val pages: List<PageMeta>
    get() = items.values.toList()

    fun getPageContentByTitle(title: String): String {
        val contentId = items[title]?.contentId ?: return ""
        return try {
            textContentResolver.resolve("content/$contentId.md")
        } catch (e: Exception) { "" }
    }

    val avgRating by lazy {
        items.values.fold(0) { acc, pageMeta ->
            acc + (pageMeta.rating ?: 0)
        } / size
    }

    val avgVoted by lazy {
        items.values.fold(0) { acc, pageMeta ->
            acc + (pageMeta.voted ?: 0)
        } / size
    }

    fun formatDescription(): String {
        return "$size историй. $avgRating%/$avgVoted человек"
    }

    fun getPageMetaByTitle(title: String): PageMeta? {
        return items[title]
    }
}