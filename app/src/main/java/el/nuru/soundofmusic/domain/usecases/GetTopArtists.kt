package el.nuru.soundofmusic.domain.usecases

import el.nuru.soundofmusic.data.Repository
import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.domain.entities.ArtistEntity
import el.nuru.soundofmusic.domain.utils.Resource
import el.nuru.soundofmusic.domain.utils.toArtistEntity
import javax.inject.Inject

class GetTopArtists @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(refresh: Boolean = false): Resource<List<ArtistEntity>> {
        return when (val response = repository.getTopArtists(refresh)) {
            is NetworkResult.Error -> Resource.Error(response.exception)
            is NetworkResult.Success -> Resource.Success(
                response.data.map {
                    it.toArtistEntity()
                }
            )
        }
    }
}
