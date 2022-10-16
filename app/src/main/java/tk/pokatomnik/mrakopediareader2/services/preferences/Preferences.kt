package tk.pokatomnik.mrakopediareader2.services.preferences

import android.content.Context
import android.content.SharedPreferences

class Preferences(private val context: Context) {
    private fun getPreferencesByName(name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    val pagePreferences by lazy { PagePreferences(getPreferencesByName("PAGE")) }

    val globalPreferences by lazy { GlobalPreferences(getPreferencesByName("GLOBAL")) }
}