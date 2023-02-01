package el.nuru.soundofmusic.domain.usecases

import el.nuru.soundofmusic.data.Repository
import el.nuru.soundofmusic.data.datasources.NetworkResult
import el.nuru.soundofmusic.data.datasources.local.entities.SongData
import el.nuru.soundofmusic.domain.entities.toSongEntity
import el.nuru.soundofmusic.domain.usecases.GetArtistSongs
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GetArtistSongsTest {

    private lateinit var getArtistSongs: GetArtistSongs
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = mock()
        getArtistSongs = GetArtistSongs(repository)
    }

    @Test
    fun `given success result when fetching artist songs then return success result`() = runBlocking {
        val permalink = "permalink"
        val artistId = "artistId"
        val refresh = false

        val data = listOf(SongData())
        val expectedResult = NetworkResult.Success(data.map { it.toSongEntity() })

        `when`(repository.getArtistSongs(permalink, artistId, refresh))
            .thenReturn(NetworkResult.Success(data))

        val result = getArtistSongs(permalink, artistId, refresh)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given error result when fetching artist songs then return error result`() = runBlocking {
        val permalink = "permalink"
        val artistId = "artistId"
        val refresh = false
        val exception = Exception("Error")
        val expectedResult = NetworkResult.Error(exception)

        `when`(repository.getArtistSongs(permalink, artistId, refresh)).thenReturn(expectedResult)

        val result = getArtistSongs(permalink, artistId, refresh)

        assertEquals(expectedResult, result)
    }
}

