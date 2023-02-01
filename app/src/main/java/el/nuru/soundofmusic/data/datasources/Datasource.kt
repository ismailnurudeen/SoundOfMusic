package el.nuru.soundofmusic.data.datasources

import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.data.datasources.local.entities.SongData

interface Datasource {
    suspend fun getArtistSongs(permalink: String): List<SongData>
    suspend fun insertSongs(songs: List<SongData>)
    suspend fun insertArtist(artists: List<ArtistData>)
    suspend fun getTopArtists(): List<ArtistData>
    suspend fun searchTopArtists(query: String): List<ArtistData>
}
