package el.nuru.soundofmusic.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import el.nuru.soundofmusic.data.ArtistSongRepository
import el.nuru.soundofmusic.data.Repository
import el.nuru.soundofmusic.data.datasources.local.LocalDatasource
import el.nuru.soundofmusic.data.datasources.local.SoundOfMusicDB
import el.nuru.soundofmusic.data.datasources.remote.HearThisApiService
import el.nuru.soundofmusic.data.datasources.remote.RemoteDatasource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideModule {
    const val BASE_URL = "https://api-v2.hearthis.at/"

    @Provides
    @Singleton
    fun provideHearThisApiClient(): HearThisApiService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(HearThisApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideSoundOfMusicDB(@ApplicationContext context: Context): SoundOfMusicDB {
        return Room.databaseBuilder<SoundOfMusicDB>(context, SoundOfMusicDB::class.java, "songartist.db")
            .build()
    }
    @Provides
    @Singleton
    fun provideRepository(remoteDatasource: RemoteDatasource, localDatasource: LocalDatasource): Repository {
        return ArtistSongRepository(remoteDatasource, localDatasource)
    }
    @Provides
    @Singleton
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
