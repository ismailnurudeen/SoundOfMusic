package el.nuru.soundofmusic.data.datasources.local

import el.nuru.soundofmusic.data.datasources.Datasource
import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.data.datasources.local.entities.SongData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDatasource @Inject constructor(private val soundOfMusicDb: SoundOfMusicDB) : Datasource {
    override suspend fun getArtistSongs(artistId: String): List<SongData> {
        return soundOfMusicDb.songDao().getArtistSongs(artistId)
    }

    override suspend fun insertSongs(songs: List<SongData>) {
        soundOfMusicDb.songDao().insertSongs(songs)
    }

    override suspend fun insertArtist(artists: List<ArtistData>) {
        soundOfMusicDb.artistDao().insertArtists(artists)
    }

    override suspend fun getTopArtists(): List<ArtistData> = withContext(Dispatchers.IO) {
        soundOfMusicDb.artistDao().getArtists()
    }

    override suspend fun searchTopArtists(query: String) = withContext(Dispatchers.IO) {
        soundOfMusicDb.artistDao().searchTopArtists(query)
    }
}
