package el.nuru.soundofmusic.data.datasources

import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.data.datasources.local.entities.SongData
import kotlinx.coroutines.flow.Flow

interface LocalDatasourceSpec {
    suspend fun insertSongs(songs: List<SongData>)
    suspend fun insertArtist(artists: List<ArtistData>)
    suspend fun searchTopArtists(query: String): Flow<List<ArtistData>>
}
