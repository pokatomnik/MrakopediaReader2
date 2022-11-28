package tk.pokatomnik.mrakopediareader2.services.index

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import tk.pokatomnik.mrakopediareader2.domain.*
import java.time.Instant

fun resolveIndex(
    generalCategoryTitle: String,
    jsonString: String,
    makeCategory: (name: String, categorySource: Map<String, PageMeta>) -> Category
): Index {
    return try {
        val jsonObject = JsonParser.parseString(jsonString).asJsonObject

        val goodStories = getGoodStories(jsonObject)
        val storiesOfMonth = getStoriesOfMonth(jsonObject)
        val newStories = getNewStories(jsonObject)
        val mrakopediaIndex = jsonObjectToMrakopediaIndex(
            generalCategoryTitle = generalCategoryTitle,
            jsonObject = jsonObject,
            makeCategory = makeCategory
        )
        val creationDate = getCreationDate(jsonObject)

        Index(
            mrakopediaIndex = mrakopediaIndex,
            storiesOfMonth = storiesOfMonth,
            goodStories = goodStories,
            newStories = newStories,
            creationDate = creationDate
        )
    } catch (e: Exception) {
        Index(
            mrakopediaIndex = mapOf(),
            storiesOfMonth = listOf(),
            goodStories = listOf(),
            newStories = listOf(),
            creationDate = Instant.now()
        )
    }
}

fun getCreationDate(jsonObject: JsonObject): Instant {
    val dateStr = jsonObject.getAsJsonPrimitive("creationDate").asString
    return Instant.parse(dateStr)
}

fun getGoodStories(jsonObject: JsonObject): List<String> {
    val jsonArray = jsonObject.getAsJsonArray("goodStories")
    val goodStories = mutableListOf<String>()
    for (goodStoryTitle in jsonArray) {
        goodStories.add(goodStoryTitle.asString)
    }
    return goodStories
}

fun getStoriesOfMonth(jsonObject: JsonObject): List<String> {
    val jsonArray = jsonObject.getAsJsonArray("storiesOfMonth")
    val storiesOfMonth = mutableListOf<String>()
    for (storyOfMonth in jsonArray) {
        storiesOfMonth.add(storyOfMonth.asString)
    }
    return storiesOfMonth
}

fun getNewStories(jsonObject: JsonObject): List<String> {
    val jsonArray = jsonObject.getAsJsonArray("newStories")
    val newStories = mutableListOf<String>()
    for (newStory in jsonArray) {
        newStories.add(newStory.asString)
    }
    return newStories
}

fun jsonObjectToMrakopediaIndex(
    generalCategoryTitle: String,
    jsonObject: JsonObject,
    makeCategory: (name: String, categorySource: Map<String, PageMeta>) -> Category
): Map<String, Category> {
    val mrakopediaIndexObject = jsonObject.getAsJsonObject("mrakopediaIndex")
    val mrakopediaIndex = mutableMapOf<String, Category>()
    val categories = mrakopediaIndexObject.keySet()

    val generalCategory = mutableMapOf<String, PageMeta>()
    for (category in categories) {
        val pageMetaJSONArray = mrakopediaIndexObject.getAsJsonArray(category)
        val pagesMetaMap = mutableMapOf<String, PageMeta>()
        for (pageMetaJSONElement in pageMetaJSONArray) {
            val pageMeta = jsonObjectToPageMeta(pageMetaJSONElement)
            pageMeta?.let {
                pagesMetaMap[it.title] = it
                generalCategory[it.title] = it
            }
        }
        if (pagesMetaMap.isNotEmpty()) {
            mrakopediaIndex[category] = makeCategory(category, pagesMetaMap)
        }
    }
    mrakopediaIndex[generalCategoryTitle] = makeCategory(generalCategoryTitle, generalCategory)
    return mrakopediaIndex
}

fun jsonObjectToPageMeta(jsonObject: JsonElement): PageMeta? {
    return try {
        val pageMetaObject = jsonObject.asJsonObject

        val title = pageMetaObject.getAsJsonPrimitive("title").asString
        val rating = try {
            pageMetaObject.getAsJsonPrimitive("rating").asInt
        } catch (e: Exception) { null }
        val voted = try {
            pageMetaObject.getAsJsonPrimitive("voted").asInt
        } catch (e: Exception) { null }
        val charactersInPage =
            pageMetaObject.getAsJsonPrimitive("charactersInPage").asInt
        val contentId = pageMetaObject.getAsJsonPrimitive("contentId").asString

        val pageCategories =
            pageMetaObject.getAsJsonArray("categories").asJsonArray.map { it.asString }
                .toSet()
        val pageSeeAlsoLinks =
            pageMetaObject.getAsJsonArray("seeAlso").asJsonArray.map { it.asString }
                .toSet()

        return PageMeta(
            title = title,
            rating = rating,
            voted = voted,
            charactersInPage = charactersInPage,
            contentId = contentId,
            categories = pageCategories,
            seeAlso = pageSeeAlsoLinks,
        )
    } catch (e: Exception) {
        null
    }
}