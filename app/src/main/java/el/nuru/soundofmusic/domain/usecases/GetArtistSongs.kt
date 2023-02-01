package el.nuru.soundofmusic.domain.usecases

import el.nuru.soundofmusic.data.Repository
import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.domain.entities.SongEntity
import el.nuru.soundofmusic.domain.utils.Resource
import el.nuru.soundofmusic.domain.utils.toSongEntity
import javax.inject.Inject

class GetArtistSongs @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(permalink: String, artistId: String, refresh: Boolean = false): Resource<List<SongEntity>> {
        return when (val response = repository.getArtistSongs(permalink, artistId, refresh)) {
            is NetworkResult.Error -> Resource.Error(response.exception)
            is NetworkResult.Success -> Resource.Success(
                response.data.map {
                    it.toSongEntity()
                }
            )
        }
    }
}
