package tk.pokatomnik.mrakopediareader2.services.preferences.page

import android.content.SharedPreferences

class PagePreferences(private val sharedPreferences: SharedPreferences) {
    var contentTextSize: Int = sharedPreferences.getInt(FONT_SIZE_KEY, 14)
        set(value) {
            field = value
            sharedPreferences.edit().putInt(FONT_SIZE_KEY, value).apply()
        }

    val minFontSize = 14

    val maxFontSize = 64

    companion object {
        const val FONT_SIZE_KEY = "font:size"
    }
}