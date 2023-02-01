package el.nuru.soundofmusic.presentation.models

data class Song(
    val artwork_url: String,
    val background_url: String,
    val created_at: String,
    val description: String,
    val download_count: String,
    val duration: String,
    val genre: String,
    val id: String,
    val key: String,
    val preview_url: String,
    val release_date: String,
    val stream_url: String,
    val tags: String,
    val thumb: String,
    val title: String,
    val type: String,
    val uri: String,
    val user_id: String,
    val waveform_data: String,
    val waveform_url: String
)
