package tk.pokatomnik.mrakopediareader2.services.preferences.page

import android.content.SharedPreferences
import tk.pokatomnik.mrakopediareader2.R

class PagePreferences(private val sharedPreferences: SharedPreferences) {
    var contentTextSize: Int = sharedPreferences.getInt(FONT_SIZE_KEY, DEFAULT_FONT_SIZE)
        set(value) {
            field = value
            sharedPreferences.edit().putInt(FONT_SIZE_KEY, value).apply()
        }

    var likeNotificationShown: Boolean = false

    val minFontSize = MIN_FONT_SIZE

    val maxFontSize = MAX_FONT_SIZE

    var contentFontFamily: String? = sharedPreferences.getString(FONT_FAMILY_KEY, null)
        set(value) {
            field = value
            sharedPreferences.edit().putString(FONT_FAMILY_KEY, value).apply()
        }

    val defaultFontKey = "По умолчанию"
    val storyFontsMap = mapOf(
        defaultFontKey to null,
        "Alegreya" to R.font.alegreyascregular,
        "Lora" to R.font.loraregular,
        "Philosopher" to R.font.philosopherregular
    )

    companion object {
        private const val FONT_SIZE_KEY = "font:size"
        private const val FONT_FAMILY_KEY = "font:family"
        private const val MIN_FONT_SIZE = 14
        private const val MAX_FONT_SIZE = 64
        private const val DEFAULT_FONT_SIZE = MIN_FONT_SIZE
    }
}