package el.nuru.soundofmusic.domain.usecases

import el.nuru.soundofmusic.data.Repository
import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.domain.entities.ArtistEntity
import el.nuru.soundofmusic.domain.entities.toArtistEntity
import javax.inject.Inject

class SearchTopArtists @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(query: String): NetworkResult<List<ArtistEntity>> {
        return when (val response = repository.searchTopArtists(query)) {
            is NetworkResult.Error -> NetworkResult.Error(response.exception)
            is NetworkResult.Success -> NetworkResult.Success(
                response.data.map {
                    it.toArtistEntity()
                }
            )
        }
    }
}
