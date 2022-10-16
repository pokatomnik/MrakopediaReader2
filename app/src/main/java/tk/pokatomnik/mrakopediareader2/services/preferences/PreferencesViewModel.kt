package tk.pokatomnik.mrakopediareader2.services.preferences

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    val preferences: Preferences
) : ViewModel()