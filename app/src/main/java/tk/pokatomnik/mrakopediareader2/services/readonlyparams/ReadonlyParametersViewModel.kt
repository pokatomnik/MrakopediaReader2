package tk.pokatomnik.mrakopediareader2.services.readonlyparams

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadonlyParametersViewModel @Inject constructor(
    val readonlyParameters: ReadonlyParameters
) : ViewModel()