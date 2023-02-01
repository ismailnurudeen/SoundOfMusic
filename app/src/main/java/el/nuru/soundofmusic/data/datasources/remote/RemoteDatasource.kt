package el.nuru.soundofmusic.data.datasources.remote

import el.nuru.soundofmusic.data.datasources.Datasource
import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.data.datasources.local.entities.SongData
import el.nuru.soundofmusic.data.datasources.remote.reponses.toSongData
import javax.inject.Inject

class RemoteDatasource @Inject constructor(private val apiClient: HearThisApiService) : Datasource {
    override suspend fun getArtistSongs(permalink: String): List<SongData> {
        val response = apiClient.getArtistSongs(permalink)
        if (!response.isSuccessful) throw IOException("Failed to fetch artist tracks")
        val songs = response.body() ?: emptyList()
        return songs.map {
            it.toSongData()
        }
    }

    override suspend fun getTopArtists(): List<ArtistData> {
        val response = apiClient.getPopularSongs()
        if (!response.isSuccessful) throw IOException("Failed to fetch top artists")

        val topSongs = response.body() ?: emptyList()
        return topSongs.map {
            it.user
        }
    }
    override suspend fun insertSongs(songs: List<SongData>) {
        // no-impl
    }

    override suspend fun insertArtist(artists: List<ArtistData>) {
        // no-impl
    }

    override suspend fun searchTopArtists(query: String): List<ArtistData> {
        return emptyList()
    }
}

class IOException(val errorMessage: String?) : Exception(errorMessage)
