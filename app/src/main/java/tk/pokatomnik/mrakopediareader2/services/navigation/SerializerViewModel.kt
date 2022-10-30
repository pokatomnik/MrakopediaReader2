package tk.pokatomnik.mrakopediareader2.services.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SerializerViewModel @Inject constructor(
    val serializer: Serializer
) : ViewModel()