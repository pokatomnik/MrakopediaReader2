package tk.pokatomnik.mrakopediareader2.services.preferences.page

import android.content.SharedPreferences

class PagePreferences(private val sharedPreferences: SharedPreferences) {
    var contentTextSize: Int = sharedPreferences.getInt(FONT_SIZE_KEY, DEFAULT_FONT_SIZE)
        set(value) {
            field = value
            sharedPreferences.edit().putInt(FONT_SIZE_KEY, value).apply()
        }

    var likeNotificationShown: Boolean = false

    val minFontSize = MIN_FONT_SIZE

    val maxFontSize = MAX_FONT_SIZE

    companion object {
        private const val FONT_SIZE_KEY = "font:size"
        private const val MIN_FONT_SIZE = 14
        private const val MAX_FONT_SIZE = 64
        private const val DEFAULT_FONT_SIZE = MIN_FONT_SIZE
    }
}