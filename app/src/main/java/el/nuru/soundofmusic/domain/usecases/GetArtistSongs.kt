package el.nuru.soundofmusic.domain.usecases

import el.nuru.soundofmusic.data.Repository
import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.domain.entities.SongEntity
import el.nuru.soundofmusic.domain.entities.toSongEntity
import javax.inject.Inject

class GetArtistSongs @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(permalink: String, artistId: String, refresh: Boolean = false): NetworkResult<List<SongEntity>> {
        return when (val response = repository.getArtistSongs(permalink, artistId, refresh)) {
            is NetworkResult.Error -> NetworkResult.Error(response.exception)
            is NetworkResult.Success -> NetworkResult.Success(
                response.data.map {
                    it.toSongEntity()
                }
            )
        }
    }
}
