package el.nuru.soundofmusic.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import el.nuru.soundofmusic.data.datasources.local.entities.SongData

@Dao
interface SongsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(list: List<SongData>)

    @Query("SELECT * from songs")
    fun getSongs(): List<SongData>

    @Query("SELECT * from songs where user_id=:artistId")
    fun getArtistSongs(artistId: String): List<SongData>
}
