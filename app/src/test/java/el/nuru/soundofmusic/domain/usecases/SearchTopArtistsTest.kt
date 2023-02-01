package el.nuru.soundofmusic.domain.usecases

import el.nuru.soundofmusic.data.Repository
import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.domain.utils.Resource
import el.nuru.soundofmusic.domain.utils.toArtistEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

internal class SearchTopArtistsTest {
    private lateinit var searchTopArtists: SearchTopArtists
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = Mockito.mock()
        searchTopArtists = SearchTopArtists(repository)
    }

    @Test
    fun `given success result when querying for top artists then return success result`() = runBlocking {
        val query = ""

        val data = listOf(ArtistData())
        val expectedResult = Resource.Success(data.map { it.toArtistEntity() })

        Mockito.`when`(repository.searchTopArtists(query))
            .thenReturn(NetworkResult.Success(data))

        val result = searchTopArtists(query)

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `given error result when querying for top artists then return error result`() = runBlocking {
        val query = ""
        val exception = Exception("Error")
        val expectedResult = Resource.Error(exception)

        Mockito.`when`(repository.searchTopArtists(query)).thenReturn(NetworkResult.Error(exception))

        val result = searchTopArtists(query)

        Assert.assertEquals(expectedResult, result)
    }
}
