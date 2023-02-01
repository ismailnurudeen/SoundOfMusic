package el.nuru.soundofmusic.data.datasources.remote.reponses

import el.nuru.soundofmusic.data.datasources.local.entities.SongData

internal fun SongResponse.toSongData(): SongData = SongData(
    artwork_url = artwork_url,
    artwork_url_retina = artwork_url_retina,
    background_url = background_url,
    created_at = created_at,
    description = description,
    download_count = download_count,
    playback_count = playback_count,
    duration = duration,
    genre = genre,
    id = id,
    key = key,
    preview_url = preview_url,
    release_date = release_date,
    reshares_count = reshares_count,
    stream_url = stream_url,
    tags = tags,
    thumb = thumb,
    title = title,
    type = type,
    uri = uri,
    user_id = user_id,
    permalink = permalink,
    waveform_data = waveform_data,
    waveform_url = waveform_url
)
