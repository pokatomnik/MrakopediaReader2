package tk.pokatomnik.mrakopediareader2.ui.components

import android.net.Uri
import androidx.compose.runtime.Composable
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import java.net.URLDecoder

private val mrakopediaPageUrlPatterns = setOf(
    "https://mrakopedia.net/wiki/",
    "http://mrakopedia.net/wiki/"
)

private fun extractPageTitle(deeplink: Uri): String? {
    val path = deeplink.toString()
    val pageUrl = mrakopediaPageUrlPatterns.find { path.startsWith(it) }
    return if (pageUrl == null) null else {
        path.replace(pageUrl, "")
    }
}

@Composable
fun rememberComputedPageTitle(deeplink: Uri?): String? {
    val mrakopediaIndex = rememberMrakopediaIndex()
    return if (deeplink == null) null else {
        val desiredPageTitle = extractPageTitle(deeplink)
        val generalCategory = mrakopediaIndex
            .getCategory(mrakopediaIndex.getGeneralCategoryTitle())

        desiredPageTitle?.let { existingDesiredPageTitle ->
            generalCategory.getPageMetaByTitle(existingDesiredPageTitle)?.title
        } ?: try {
            val decoded = URLDecoder.decode(desiredPageTitle, "utf-8").replace("_", " ")
            generalCategory.getPageMetaByTitle(decoded)?.title
        } catch (e: Exception) { null }
    }
}