package tk.pokatomnik.mrakopediareader2.services.index

import tk.pokatomnik.mrakopediareader2.services.textassetresolver.TextAssetResolver


class MrakopediaIndex(
    private val textContentResolver: TextAssetResolver,
) {
    private val mrakopediaIndex by lazy {
        val jsonString = textContentResolver.resolve("content/index.json")
        resolveIndex(GENERAL_CATEGORY_TITLE, jsonString) { title, metaMap ->
            Category(title, metaMap, textContentResolver)
        }
    }

    val uniquePagesTotalComputed by lazy { mrakopediaIndex[GENERAL_CATEGORY_TITLE]?.size ?: 0 }

    fun getCategory(categoryTitle: String): Category {
        return mrakopediaIndex[categoryTitle] ?: Category(
            categoryTitle,
            mapOf(),
            textContentResolver
        )
    }

    fun getCategoryNames(): Set<String> {
        return mrakopediaIndex.keys
    }

    companion object {
        private const val GENERAL_CATEGORY_TITLE = "Все страницы"
    }
}
