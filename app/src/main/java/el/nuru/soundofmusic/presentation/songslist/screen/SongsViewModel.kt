package el.nuru.soundofmusic.presentation.songslist.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import el.nuru.soundofmusic.di.IoDispatcher
import el.nuru.soundofmusic.domain.usecases.GetArtistSongs
import el.nuru.soundofmusic.domain.utils.Resource
import el.nuru.soundofmusic.presentation.models.Song
import el.nuru.soundofmusic.presentation.models.toSongModel
import el.nuru.soundofmusic.presentation.songslist.SongsActivity.Companion.EXTRA_ARTIST_ID
import el.nuru.soundofmusic.presentation.songslist.SongsActivity.Companion.EXTRA_ARTIST_NAME
import el.nuru.soundofmusic.presentation.songslist.SongsActivity.Companion.EXTRA_ARTIST_PERMALINK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsViewModel @Inject constructor(
    private val getArtistSongs: GetArtistSongs,
    private val savedStateHandle: SavedStateHandle,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val mutableUiState = MutableStateFlow(SongListUiState())
    val uiState: StateFlow<SongListUiState> = mutableUiState

    fun loadSongs(refresh: Boolean = false) {
        if (refresh) {
            mutableUiState.update {
                it.copy(isRefreshing = true)
            }
        }
        viewModelScope.launch(dispatcher) {
            val artistName = savedStateHandle.get<String>(EXTRA_ARTIST_NAME) ?: return@launch
            val artistPermalink = savedStateHandle.get<String>(EXTRA_ARTIST_PERMALINK) ?: return@launch
            val artistId = savedStateHandle.get<String>(EXTRA_ARTIST_ID) ?: return@launch

            when (val result = getArtistSongs(artistPermalink, artistId, refresh)) {
                is Resource.Error -> {
                    mutableUiState.update {
                        it.copy(isRefreshing = false)
                    }
                }
                is Resource.Success -> {
                    val songs = result.data.map { it.toSongModel() }
                    mutableUiState.update {
                        it.copy(songs = songs, currentlyArtistName = artistName, isRefreshing = false)
                    }
                }
            }
        }
    }
    fun playSong(song: Song, index: Int) {
        if (uiState.value.currentlySong?.id == song.id) return
        mutableUiState.update {
            it.copy(currentlySong = song, currentlySongIndex = index)
        }
    }
    fun toggleSong() {
        mutableUiState.update {
            it.copy(songPaused = !it.songPaused)
        }
    }
    fun nextSong() {
        val nextSong = uiState.value.songs.getOrNull(uiState.value.currentlySongIndex + 1)
        nextSong?.let {
            mutableUiState.update {
                it.copy(currentlySong = nextSong, currentlySongIndex = it.currentlySongIndex + 1)
            }
        }
    }
    fun previousSong() {
        val prevSong = uiState.value.songs.getOrNull(uiState.value.currentlySongIndex - 1)
        prevSong?.let {
            mutableUiState.update {
                it.copy(currentlySong = prevSong, currentlySongIndex = it.currentlySongIndex - 1)
            }
        }
    }

    fun songStarted(started: Boolean) {
        mutableUiState.update {
            it.copy(songStarted = started)
        }
    }
}
