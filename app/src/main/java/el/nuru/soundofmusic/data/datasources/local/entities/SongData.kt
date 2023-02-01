package el.nuru.soundofmusic.data.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongData(
    val artwork_url: String = "",
    val artwork_url_retina: String = "",
    val background_url: String = "",
    val created_at: String = "",
    val description: String = "",
    val download_count: String = "",
    val duration: String = "",
    val genre: String = "",
    @PrimaryKey
    val id: String = "",
    val key: String = "",
    val permalink: String = "",
    val playback_count: String = "",
    val preview_url: String = "",
    val release_date: String = "",
    val reshares_count: String = "",
    val stream_url: String = "",
    val tags: String = "",
    val thumb: String = "",
    val title: String = "",
    val type: String = "",
    val uri: String = "",
    val user_id: String = "",
    val waveform_data: String = "",
    val waveform_url: String = ""
)
