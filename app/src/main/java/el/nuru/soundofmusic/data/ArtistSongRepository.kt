package el.nuru.soundofmusic.data

import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.data.datasources.local.LocalDatasource
import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.data.datasources.local.entities.SongData
import el.nuru.soundofmusic.data.datasources.remote.RemoteDatasource
import javax.inject.Inject

class ArtistSongRepository @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val localDatasource: LocalDatasource
) : Repository {
    override suspend fun getArtistSongs(permalink: String, artistId: String, refresh: Boolean): NetworkResult<List<SongData>> {
        return try {
            val songsInDb = localDatasource.getArtistSongs(artistId)
            if (songsInDb.isEmpty() || refresh) {
                val newSongs = remoteDatasource.getArtistSongs(permalink)
                localDatasource.insertSongs(newSongs)
            }
            NetworkResult.Success(localDatasource.getArtistSongs(artistId))
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    override suspend fun getTopArtists(refresh: Boolean): NetworkResult<List<ArtistData>> {
        return try {
            val artistsInDb = localDatasource.getTopArtists()
            if (artistsInDb.isEmpty() || refresh) {
                val newArtists = remoteDatasource.getTopArtists()
                localDatasource.insertArtist(newArtists)
            }
            NetworkResult.Success(localDatasource.getTopArtists())
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    override suspend fun searchTopArtists(query: String): NetworkResult<List<ArtistData>> {
        return try {
            NetworkResult.Success(localDatasource.searchTopArtists(query))
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}
