package el.nuru.soundofmusic.domain.entities

import el.nuru.soundofmusic.data.datasources.local.entities.ArtistData
import el.nuru.soundofmusic.data.datasources.local.entities.SongData

internal fun ArtistData.toArtistEntity() = ArtistEntity(
    avatar_url = avatar_url,
    caption = caption,
    id = id,
    uri = uri,
    username = username,
    permalink = permalink
)
internal fun SongData.toSongEntity() = SongEntity(
    artwork_url = artwork_url,
    background_url = background_url,
    created_at = created_at,
    description = description,
    download_count = download_count,
    duration = duration,
    genre = genre,
    id = id,
    key = key,
    preview_url = preview_url,
    release_date = release_date,
    stream_url = stream_url,
    tags = tags,
    thumb = thumb,
    title = title,
    type = type,
    uri = uri,
    user_id = user_id,
    waveform_data = waveform_data,
    waveform_url = waveform_url
)
