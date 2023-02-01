package el.nuru.soundofmusic.presentation.songslist.screen
import el.nuru.soundofmusic.presentation.models.Song

data class SongListUiState(
    val songs: List<Song> = emptyList(),
    val currentlyArtistName: String = "Artist",
    val currentlySong: Song? = null,
    val currentlySongIndex: Int = 0,
    val songStarted: Boolean = false,
    val songPaused: Boolean = false,
    val isRefreshing: Boolean = false
)
