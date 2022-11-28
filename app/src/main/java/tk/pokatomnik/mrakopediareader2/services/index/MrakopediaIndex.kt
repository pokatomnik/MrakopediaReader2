package tk.pokatomnik.mrakopediareader2.services.index

import kotlinx.coroutines.*
import tk.pokatomnik.mrakopediareader2.domain.Category
import tk.pokatomnik.mrakopediareader2.domain.Index
import tk.pokatomnik.mrakopediareader2.domain.PageMeta
import tk.pokatomnik.mrakopediareader2.services.textassetresolver.TextAssetResolver
import java.lang.Integer.min
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.random.Random


class MrakopediaIndex(
    private val textContentResolver: TextAssetResolver,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private var computedIndex: Index? = null

    val index: Index
        get() = warmUpIndex()

    val uniquePagesTotalComputed: Int
        get() = index.mrakopediaIndex[GENERAL_CATEGORY_TITLE]?.size ?: 0

    @Synchronized
    fun warmUpIndex(): Index {
        val currentIndex = computedIndex
        if (currentIndex != null) return currentIndex

        val jsonString = textContentResolver.resolve("content/index.json")
        val resolvedIndex = resolveIndex(GENERAL_CATEGORY_TITLE, jsonString) { title, metaMap ->
            Category(title, metaMap, textContentResolver)
        }

        computedIndex = resolvedIndex

        return resolvedIndex
    }

    fun getCategory(categoryTitle: String): Category {
        return index.mrakopediaIndex[categoryTitle] ?: Category(
            categoryTitle,
            mapOf(),
            textContentResolver
        )
    }

    fun getCategoryNames(): Set<String> {
        return index.mrakopediaIndex.keys
    }

    fun getCategories(): Collection<Category> {
        return index.mrakopediaIndex.values
    }

    fun getGeneralCategoryTitle(): String {
        return GENERAL_CATEGORY_TITLE
    }

    fun getGoodStories(): List<String> {
        return index.goodStories
    }

    fun getStoriesOfMonth(): List<String> {
        return index.storiesOfMonth
    }

    fun getNewStories(): List<String> {
        return index.newStories
    }

    fun getRandomTitles(desiredAmount: Int): Set<String> {
        val generalCategoryPages  = getCategory(getGeneralCategoryTitle()).pages
        val set = mutableSetOf<String>()
        while (set.size < min(desiredAmount, generalCategoryPages.size)) {
            val randomTitle = generalCategoryPages.random(Random(System.currentTimeMillis())).title
            set.add(randomTitle)
        }
        return set
    }

    private fun searchForCategoriesAsync(searchString: String): Deferred<Collection<Category>> {
        val searchStringLower = searchString.lowercase()
        return coroutineScope.async {
            index.mrakopediaIndex.values.filter {
                it.name.lowercase().contains(searchStringLower)
            }
        }
    }

    private suspend fun searchForPageMetaAsync(searchString: String): Deferred<Collection<PageMeta>> {
        val searchStringLower = searchString.lowercase()
        val allPageMeta = index.mrakopediaIndex.values.map { category ->
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
    
    fun formatCreationDate(format: String): String {
        val formatter = DateTimeFormatter.ofPattern(format)
            .withZone(ZoneId.systemDefault())
        return formatter.format(index.creationDate)
    }

    companion object {
        private const val GENERAL_CATEGORY_TITLE = "Все страницы"
    }
}
