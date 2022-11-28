package tk.pokatomnik.mrakopediareader2.services.preferences.page

import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
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

    var colorPresetID: String? = sharedPreferences.getString(COLOR_PRESET_KEY, null)
        set(value) {
            field = value
            sharedPreferences.edit().putString(COLOR_PRESET_KEY, value).apply()
        }
    val defaultColorPresetID = "DEFAULT"
    val colorPresets = mapOf(
        defaultColorPresetID to ColorPreset(
            id = defaultColorPresetID,
            name = "По умолчанию",
            backgroundColor = null,
            contentColor = null
        ),
        "CREME" to ColorPreset(
            id = "CREME",
            name = "Кремовый",
            backgroundColor = Color(0xfffff3e0),
            contentColor = Color(0xff000000)
        ),
        "LIGHT_CYAN" to ColorPreset(
            id = "LIGHT_CYAN",
            name = "Светлый циан",
            backgroundColor = Color(0xffe0f7fa),
            contentColor = Color(0xff000000),
        ),
        "BLACK_WHITE" to ColorPreset(
            id = "BLACK_WHITE",
            name = "Черно-белый",
            backgroundColor = Color(0xff000000),
            contentColor = Color(0xffffffff)
        ),
        "WHITE_BLACK" to ColorPreset(
            id = "WHITE_BLACK",
            name = "Бело-черный",
            backgroundColor = Color(0xffffffff),
            contentColor = Color(0xff000000)
        ),
        "DARK_DEEP_BLUE" to ColorPreset(
            id = "DARK_DEEP_BLUE",
            name = "Темный синевато-черный",
            backgroundColor = Color(0xff424242),
            contentColor = Color(0xffffffff)
        ),
        "TERRACOTTA" to ColorPreset(
            id = "TERRACOTTA",
            name = "Темный терракотовый",
            backgroundColor = Color(0xff4e342e),
            contentColor = Color(0xffffffff)
        )
    )

    companion object {
        private const val FONT_SIZE_KEY = "font:size"
        private const val FONT_FAMILY_KEY = "font:family"
        private const val COLOR_PRESET_KEY = "colors:preset_id"
        private const val MIN_FONT_SIZE = 14
        private const val MAX_FONT_SIZE = 64
        private const val DEFAULT_FONT_SIZE = MIN_FONT_SIZE
    }
}