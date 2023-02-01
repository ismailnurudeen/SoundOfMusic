package el.nuru.soundofmusic.data.datasources.remote

import el.nuru.soundofmusic.data.datasources.remote.reponses.SongResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HearThisApiService {
    @GET("feed/?type=popular")
    suspend fun getPopularSongs(
        @Query("page") page: Int = 1,
        @Query("count") count: Int = 20
    ): Response<List<SongResponse>>

    @GET("{permalink}/?type=tracks")
    suspend fun getArtistSongs(
        @Path("permalink") permalink: String,
        @Query("page") page: Int = 1,
        @Query("count") count: Int = 20
    ): Response<List<SongResponse>>
}
