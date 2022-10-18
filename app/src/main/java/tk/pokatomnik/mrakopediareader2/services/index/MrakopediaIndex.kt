package tk.pokatomnik.mrakopediareader2.services.index

import kotlinx.coroutines.*
import tk.pokatomnik.mrakopediareader2.domain.PageMeta
import tk.pokatomnik.mrakopediareader2.services.textassetresolver.TextAssetResolver


class MrakopediaIndex(
    private val textContentResolver: TextAssetResolver,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

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

    fun getCategories(): Collection<Category> {
        return mrakopediaIndex.values
    }

    fun getGeneralCategoryTitle(): String {
        return GENERAL_CATEGORY_TITLE
    }

    private fun searchForCategoriesAsync(searchString: String): Deferred<Collection<Category>> {
        val searchStringLower = searchString.lowercase()
        return coroutineScope.async {
            mrakopediaIndex.values.filter {
                it.name.lowercase().contains(searchStringLower)
            }
        }
    }

    private suspend fun searchForPageMetaAsync(searchString: String): Deferred<Collection<PageMeta>> {
        val searchStringLower = searchString.lowercase()
        val allPageMeta = mrakopediaIndex.values.map { category ->
            coroutineScope.async {
                category.pages.filter { currentPageMeta ->
                    currentPageMeta.title.lowercase().contains(searchStringLower)
                }
            }
        }.awaitAll()
        return coroutineScope.async {
            allPageMeta.fold(mutableMapOf<String, PageMeta>()) { acc, pageMetaInCategory ->
                for (pageMeta in pageMetaInCategory) {
                    acc[pageMeta.title] = pageMeta
                }
                acc
            }.values
        }
    }

    suspend fun globalSearch(searchString: String): SearchResult {
        return SearchResult(
            categories = searchForCategoriesAsync(searchString).await(),
            pageMeta = searchForPageMetaAsync(searchString).await()
        )
    }

    companion object {
        private const val GENERAL_CATEGORY_TITLE = "Все страницы"
    }
}
