package el.nuru.soundofmusic.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import el.nuru.soundofmusic.data.datasources.Datasource
import el.nuru.soundofmusic.data.datasources.local.LocalDatasource
import el.nuru.soundofmusic.data.datasources.remote.RemoteDatasource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    @Binds
    @Singleton
    abstract fun bindRemoteDatasource(remoteDatasource: RemoteDatasource): Datasource

    @Binds
    @Singleton
    abstract fun bindLocalDatasource(localDatasource: LocalDatasource): Datasource
}
