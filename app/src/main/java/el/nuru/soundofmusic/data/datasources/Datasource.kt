package el.nuru.soundofmusic.data.datasources

import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.data.datasources.local.entities.SongData
import kotlinx.coroutines.flow.Flow

interface Datasource {
    suspend fun getArtistSongs(permalink: String): List<SongData>
    suspend fun getTopArtists(): List<ArtistData>
}
