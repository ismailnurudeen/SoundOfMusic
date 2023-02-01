package el.nuru.soundofmusic.presentation.topartistslist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.domain.usecases.GetTopArtists
import el.nuru.soundofmusic.domain.usecases.SearchTopArtists
import el.nuru.soundofmusic.presentation.models.toArtistModel
import el.nuru.soundofmusic.presentation.topartistslist.TopArtistUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopArtistsViewModel @Inject constructor(
    private val getTopArtists: GetTopArtists,
    private val searchTopArtists: SearchTopArtists,
    private val handle: SavedStateHandle
) : ViewModel() {
    private val mutableUiState = MutableStateFlow(TopArtistUiState())
    val uiState: StateFlow<TopArtistUiState> = mutableUiState

    init {
        loadTopArtists()
    }
    fun loadTopArtists(refresh: Boolean = false) {
        mutableUiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getTopArtists(refresh)) {
                is NetworkResult.Error -> {
                    mutableUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exception.localizedMessage
                        )
                    }
                }
                is NetworkResult.Success -> {
                    val topArtists = result.data.map {
                        it.toArtistModel()
                    }
                    mutableUiState.update {
                        it.copy(artists = topArtists, isLoading = false)
                    }
                }
            }
        }
    }
    fun clearErrorMessage() {
        mutableUiState.update {
            it.copy(errorMessage = "")
        }
    }

    fun search(query: String?) {
        if(query.isNullOrEmpty()) {
            loadTopArtists()
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = searchTopArtists(query)) {
                is NetworkResult.Error -> {
                    mutableUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exception.localizedMessage
                        )
                    }
                }
                is NetworkResult.Success -> {
                    val topArtists = result.data.map {
                        it.toArtistModel()
                    }
                    mutableUiState.update {
                        it.copy(artists = topArtists, isLoading = false)
                    }
                }
            }
        }
    }
}
