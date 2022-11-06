package tk.pokatomnik.mrakopediareader2.services.index

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import tk.pokatomnik.mrakopediareader2.domain.*

fun resolveIndex(
    generalCategoryTitle: String,
    jsonString: String,
    makeCategory: (name: String, categorySource: Map<String, PageMeta>) -> Category
): Index {
    return try {
        val jsonObject = JsonParser.parseString(jsonString).asJsonObject

        val goodStories = getGoodStories(jsonObject)
        val storiesOfMonth = getStoriesOfMonth(jsonObject)
        val mrakopediaIndex = jsonObjectToMrakopediaIndex(
            generalCategoryTitle = generalCategoryTitle,
            jsonObject = jsonObject,
            makeCategory = makeCategory
        )

        Index(
            mrakopediaIndex = mrakopediaIndex,
            storiesOfMonth = StoriesOfMonth(
                storiesOfMonth = storiesOfMonth,
                goodStories = goodStories
            )
        )
    } catch (e: Exception) {
        Index(
            mrakopediaIndex = mapOf(),
            storiesOfMonth = StoriesOfMonth(
                storiesOfMonth = listOf(),
                goodStories = listOf()
            )
        )
    }
}

fun getGoodStories(jsonObject: JsonObject): List<String> {
    val storiesOfMonthObject = jsonObject.getAsJsonObject("storiesOfMonth")
    val jsonArray = storiesOfMonthObject.getAsJsonArray("goodStories")
    val goodStories = mutableListOf<String>()
    for (goodStoryTitle in jsonArray) {
        goodStories.add(goodStoryTitle.asString)
    }
    return goodStories
}

fun getStoriesOfMonth(jsonObject: JsonObject): List<String> {
    val storiesOfMonthObject = jsonObject.getAsJsonObject("storiesOfMonth")
    val jsonArray = storiesOfMonthObject.getAsJsonArray("storiesOfMonth")
    val storiesOfMonth = mutableListOf<String>()
    for (storyOfMonth in jsonArray) {
        storiesOfMonth.add(storyOfMonth.asString)
    }
    return storiesOfMonth
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

fun jsonElementToImageInfo(element: JsonElement): ImageInfo {
    val jsonObject = element.asJsonObject
    val imgPath = jsonObject.getAsJsonPrimitive("imgPath").asString
    val imgCaption = try {
        jsonObject.getAsJsonPrimitive("imgCaption").asString
    } catch (e: Exception) { null }
    return ImageInfo(
        imgPath = imgPath,
        imgCaption = imgCaption
    )
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

        val images =
            pageMetaObject.getAsJsonArray("images").asJsonArray.map { jsonElementToImageInfo(it) }

        return PageMeta(
            title = title,
            rating = rating,
            voted = voted,
            charactersInPage = charactersInPage,
            contentId = contentId,
            categories = pageCategories,
            seeAlso = pageSeeAlsoLinks,
            images = images
        )
    } catch (e: Exception) {
        null
    }
}