package el.nuru.soundofmusic.domain.usecases

import el.nuru.soundofmusic.data.Repository
import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.domain.entities.ArtistEntity
import el.nuru.soundofmusic.domain.utils.Resource
import el.nuru.soundofmusic.domain.utils.toArtistEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchTopArtists @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(query: String): Flow<List<ArtistEntity>> {
        return repository.searchTopArtists(query).map { list ->
            list.map {
                it.toArtistEntity()
            }
        }
    }
}
