package tk.pokatomnik.mrakopediareader2.services.index

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import tk.pokatomnik.mrakopediareader2.domain.PageMeta

fun resolveIndex(
    generalCategoryTitle: String,
    jsonString: String,
    makeCategory: (name: String, categorySource: Map<String, PageMeta>) -> Category
): Map<String, Category> {
    return try {
        val mrakopediaIndex = mutableMapOf<String, Category>()
        val jsonObject = JsonParser.parseString(jsonString).asJsonObject
        val categories = jsonObject.keySet()

        val generalCategory = mutableMapOf<String, PageMeta>()

        for (category in categories) {
            val pageMetaJSONArray = jsonObject.getAsJsonArray(category)
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

        mrakopediaIndex
    } catch (e: Exception) {
        mapOf()
    }
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
            seeAlso = pageSeeAlsoLinks
        )
    } catch (e: Exception) {
        null
    }
}