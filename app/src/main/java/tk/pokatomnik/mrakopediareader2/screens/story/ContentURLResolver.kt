package tk.pokatomnik.mrakopediareader2.screens.story

import java.net.URLEncoder

private val replacements = mapOf(
    "%2F" to "/",
    "%3A" to ":",
    "%2C" to ",",
    "%28" to "(",
    "%29" to ")",
    "%21" to "!",
)

private const val originURL = "https://mrakopedia.net"

fun resolveContentURL(pageTitle: String): String {
    val rawUrl = "$originURL/wiki/${URLEncoder.encode(pageTitle.replace(" ", "_"), "utf-8")}"
    return replacements.entries.fold(rawUrl) { acc, (from, to) ->
        acc.replace(from, to)
    }
}