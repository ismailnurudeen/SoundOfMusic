package el.nuru.soundofmusic.data

import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.data.datasources.local.entities.SongData
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getArtistSongs(permalink: String, artistId: String, refresh: Boolean): NetworkResult<List<SongData>>
    suspend fun getTopArtists(refresh: Boolean): NetworkResult<List<ArtistData>>
    suspend fun searchTopArtists(query: String): Flow<List<ArtistData>>
}
