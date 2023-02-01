package el.nuru.soundofmusic.presentation.topartistslist

import el.nuru.soundofmusic.presentation.models.Artist

data class TopArtistUiState(
    val artists: List<Artist> = emptyList(),
    val page: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
