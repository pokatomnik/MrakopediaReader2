package tk.pokatomnik.mrakopediareader2.services.db

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MrakopediaDatabaseViewModel @Inject constructor(
    val database: MrakopediaDatabase
) : ViewModel()