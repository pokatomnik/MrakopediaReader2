package tk.pokatomnik.mrakopediareader2.services.preferences.categories

import android.content.SharedPreferences

class CategoriesPreferences(private val sharedPreferences: SharedPreferences) {
    var sortingType: String? = sharedPreferences.getString(
        SORTING_TYPE_KEY,
        null
    )
        set(value) {
            field = value
            sharedPreferences.edit().putString(SORTING_TYPE_KEY, value).apply()
        }

    companion object {
        private const val SORTING_TYPE_KEY = "sorting:type"
    }
}