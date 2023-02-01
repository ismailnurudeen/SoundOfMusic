package el.nuru.soundofmusic.presentation.songplayer.parts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.android.exoplayer2.ExoPlayer

@Composable
fun ExpandedPlayer(
    player: ExoPlayer,
    albumArtUrl: String,
    artistName: String,
    songTitle: String,
    onTogglePlay: () -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit
) {
    Surface(color = MaterialTheme.colors.surface) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = albumArtUrl,
                    contentDescription = "",
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = songTitle, style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = artistName, style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(32.dp))
                ExoPlayerControls(player, onTogglePlay, onNext, onPrev, Modifier.padding(end = 8.dp))
            }
        }
    }
}
