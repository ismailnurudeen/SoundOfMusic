package el.nuru.soundofmusic.presentation.models

import el.nuru.soundofmusic.domain.entities.ArtistEntity
import el.nuru.soundofmusic.domain.entities.SongEntity

internal fun ArtistEntity.toArtistModel() = Artist(
    avatar_url = avatar_url,
    caption = caption,
    id = id,
    uri = uri,
    username = username,
    permalink = permalink
)
internal fun SongEntity.toSongModel() = Song(
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
