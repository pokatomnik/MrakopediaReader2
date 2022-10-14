package tk.pokatomnik.mrakopediareader2.services.index

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MrakopediaIndexViewModel @Inject constructor(
    val mrakopediaIndex: MrakopediaIndex
) : ViewModel()