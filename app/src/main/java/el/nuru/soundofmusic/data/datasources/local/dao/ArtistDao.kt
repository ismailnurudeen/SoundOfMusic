package el.nuru.soundofmusic.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtists(list: List<ArtistData>)

    @Query("SELECT * from artists")
    fun getArtists(): List<ArtistData>

    @Query("SELECT * from artists where id=:id")
    fun getArtist(id: String): ArtistData

    @Query("SELECT * from artists where username like '%' || :query || '%'")
    fun searchTopArtists(query: String): List<ArtistData>
}
