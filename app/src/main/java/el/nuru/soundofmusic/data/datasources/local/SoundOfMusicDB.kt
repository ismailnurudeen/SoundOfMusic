package el.nuru.soundofmusic.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import el.nuru.soundofmusic.data.datasources.local.dao.ArtistDao
import el.nuru.soundofmusic.data.datasources.local.dao.SongsDao
import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.data.datasources.local.entities.SongData

@Database(
    entities = [
        SongData::class,
        ArtistData::class
    ],
    version = 1
)
abstract class SoundOfMusicDB : RoomDatabase(){
    abstract fun songDao(): SongsDao
    abstract fun artistDao(): ArtistDao
}
