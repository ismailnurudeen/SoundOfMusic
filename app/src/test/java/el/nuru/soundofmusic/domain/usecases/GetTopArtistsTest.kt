package el.nuru.soundofmusic.domain.usecases

import el.nuru.soundofmusic.data.Repository
import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.domain.utils.Resource
import el.nuru.soundofmusic.domain.utils.toArtistEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

internal class GetTopArtistsTest {

    private lateinit var getTopArtists: GetTopArtists
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = mock()
        getTopArtists = GetTopArtists(repository)
    }

    @Test
    fun `given success result when fetching top artists then return success result`() = runBlocking {
        val refresh = false

        val data = listOf(ArtistData())
        val expectedResult = Resource.Success(data.map { it.toArtistEntity() })

        `when`(repository.getTopArtists(refresh))
            .thenReturn(NetworkResult.Success(data))

        val result = getTopArtists(refresh)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given error result when fetching top artists then return error result`() = runBlocking {
        val refresh = false
        val exception = Exception("Error")
        val expectedResult = Resource.Error(exception)

        `when`(repository.getTopArtists(refresh)).thenReturn(NetworkResult.Error(exception))

        val result = getTopArtists(refresh)

        assertEquals(expectedResult, result)
    }
}
