package el.nuru.soundofmusic.presentation.songplayer

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import el.nuru.soundofmusic.presentation.songplayer.parts.ExpandedPlayer
import el.nuru.soundofmusic.presentation.songplayer.parts.MiniPlayer
import kotlinx.coroutines.launch

@Composable
fun SongPlayer(
    modifier: Modifier = Modifier,
    songTitle: String,
    artistName: String,
    streamUrl: String,
    albumArtUrl: String,
    onTogglePlay: () -> Unit = {},
    onPrev: () -> Unit = {},
    onNext: () -> Unit = {},
    isExpanded: Boolean = false,
    songStarted: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
    onError: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isPlayingNow by remember { mutableStateOf(false) }

    val exoPlayer: ExoPlayer by remember {
        derivedStateOf {
            val player: ExoPlayer = ExoPlayer.Builder(context).build()
            player
        }
    }

    DisposableEffect(
        key1 = exoPlayer,
        effect = {
            val listener = object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    scope.launch {
                        isPlayingNow = isPlaying
                        songStarted(isPlayingNow)
                    }
                }
                override fun onPlayerError(error: PlaybackException) {
                    error.message?.let { onError(it) }
                }
            }
            exoPlayer.addListener(listener)
            onDispose {
                isPlayingNow = false
                exoPlayer.stop()
                exoPlayer.removeListener(listener)
                exoPlayer.release()
            }
        }
    )
    LaunchedEffect(streamUrl) {
        val mediaItem: MediaItem = MediaItem.fromUri(streamUrl)
        exoPlayer.apply {
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
            play()
        }
    }

    Column(
        modifier = modifier
            .animateContentSize(
                animationSpec = tween()
            )
            .clickable {
                onClick()
            }
    ) {
        if (isExpanded) {
            ExpandedPlayer(exoPlayer, albumArtUrl, artistName, songTitle, onTogglePlay, onNext, onPrev)
        } else {
            MiniPlayer(exoPlayer, albumArtUrl, artistName, songTitle, onTogglePlay, onNext, onPrev)
        }
    }
}
