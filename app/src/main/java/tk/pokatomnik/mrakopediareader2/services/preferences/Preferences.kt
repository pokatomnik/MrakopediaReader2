package tk.pokatomnik.mrakopediareader2.services.preferences

import android.content.Context
import android.content.SharedPreferences
import tk.pokatomnik.mrakopediareader2.services.preferences.categories.CategoriesPreferences
import tk.pokatomnik.mrakopediareader2.services.preferences.global.GlobalPreferences
import tk.pokatomnik.mrakopediareader2.services.preferences.page.PagePreferences
import tk.pokatomnik.mrakopediareader2.services.preferences.stories.StoriesPreferences

class Preferences(private val context: Context) {
    private fun getPreferencesByName(name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    val pagePreferences by lazy { PagePreferences(getPreferencesByName("PAGE")) }

    val globalPreferences by lazy { GlobalPreferences(getPreferencesByName("GLOBAL")) }

    val categoriesPreferences by lazy { CategoriesPreferences(getPreferencesByName("CATEGORIES")) }

    val storiesPreferences by lazy { StoriesPreferences(getPreferencesByName("STORIES")) }
}