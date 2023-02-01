package el.nuru.soundofmusic.presentation.songplayer.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.ExoPlayer
import el.nuru.soundofmusic.R
import el.nuru.soundofmusic.presentation.utils.formatTime

@Composable
fun ExoPlayerControls(exoPlayer: ExoPlayer, onTogglePlay: () -> Unit, onNext: () -> Unit, onPrev: () -> Unit, modifier: Modifier = Modifier) {
    val duration = exoPlayer.duration
    val currentPosition = exoPlayer.currentPosition
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(
                painter = painterResource(id = R.drawable.ic_previous),
                contentDescription = "previous",
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        onPrev()
                    }
            )
            Icon(
                painter = painterResource(id = if (exoPlayer.isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                contentDescription = "pause/play",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(30.dp)
                    .clickable {
                        exoPlayer.playWhenReady = !exoPlayer.isPlaying
                        onTogglePlay()
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = "next",
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        onNext()
                    }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "${formatTime(currentPosition)} / ${formatTime(duration)}",
            style = MaterialTheme.typography.caption
        )
    }
}
