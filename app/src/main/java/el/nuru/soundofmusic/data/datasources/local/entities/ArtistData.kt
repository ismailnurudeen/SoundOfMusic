package el.nuru.soundofmusic.data.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistData(
    val avatar_url: String = "",
    val caption: String = "",
    @PrimaryKey val id: String = "",
    val permalink: String = "",
    val permalink_url: String = "",
    val uri: String = "",
    val username: String = ""
)
