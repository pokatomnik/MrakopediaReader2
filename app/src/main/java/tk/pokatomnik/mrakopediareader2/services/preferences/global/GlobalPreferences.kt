package tk.pokatomnik.mrakopediareader2.services.preferences.global

import android.content.SharedPreferences

enum class ThemeIdentifier {
    AUTO,
    LIGHT,
    DARK
}

class GlobalPreferences(private val sharedPreferences: SharedPreferences) {
    private fun getSavedThemeOrFallback(): ThemeIdentifier {
        val savedKey = sharedPreferences.getString(
            THEME_IDENTIFIER_KEY,
            ThemeIdentifier.AUTO.toString()
        )
        return when (savedKey) {
            "LIGHT" -> ThemeIdentifier.LIGHT
            "DARK" -> ThemeIdentifier.DARK
            else -> ThemeIdentifier.AUTO
        }
    }

    var themeIdentifier: ThemeIdentifier = getSavedThemeOrFallback()
        set(value) {
            field = value
            sharedPreferences
                .edit()
                .putString(THEME_IDENTIFIER_KEY, value.toString())
                .apply()
        }

    var savedPath: String? = sharedPreferences.getString(SAVED_NAVIGATION_PATH, null)
        set(value) {
            field = value
            sharedPreferences.edit().putString(SAVED_NAVIGATION_PATH, value).apply()
        }

    companion object {
        const val THEME_IDENTIFIER_KEY = "theme:identifier"
        const val SAVED_NAVIGATION_PATH  = "navigation:saved_path"
    }
}