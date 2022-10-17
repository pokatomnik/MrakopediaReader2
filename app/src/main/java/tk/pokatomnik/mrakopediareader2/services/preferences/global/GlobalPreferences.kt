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

    companion object {
        const val THEME_IDENTIFIER_KEY = "theme:identifier"
    }
}