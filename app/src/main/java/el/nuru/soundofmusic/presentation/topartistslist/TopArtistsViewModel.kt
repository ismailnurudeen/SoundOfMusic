package el.nuru.soundofmusic.presentation.topartistslist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import el.nuru.soundofmusic.di.IoDispatcher
import el.nuru.soundofmusic.domain.usecases.GetTopArtists
import el.nuru.soundofmusic.domain.usecases.SearchTopArtists
import el.nuru.soundofmusic.domain.utils.Resource
import el.nuru.soundofmusic.presentation.models.toArtistModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopArtistsViewModel @Inject constructor(
    private val getTopArtists: GetTopArtists,
    private val searchTopArtists: SearchTopArtists,
    private val handle: SavedStateHandle,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
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
        viewModelScope.launch(dispatcher) {
            when (val result = getTopArtists(refresh)) {
                is Resource.Error -> {
                    mutableUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exception.localizedMessage
                        )
                    }
                }
                is Resource.Success -> {
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
        if (query.isNullOrEmpty()) {
            loadTopArtists()
            return
        }
        viewModelScope.launch {
            searchTopArtists(query)
                .flowOn(dispatcher)
                .catch {
                    mutableUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to search $query"
                        )
                    }
                }.collectLatest { list ->
                    val topArtists = list.map {
                        it.toArtistModel()
                    }
                    mutableUiState.update {
                        it.copy(artists = topArtists, isLoading = false)
                    }
                }
        }
    }
}
