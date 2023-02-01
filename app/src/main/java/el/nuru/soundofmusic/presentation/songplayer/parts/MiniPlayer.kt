package el.nuru.soundofmusic.presentation.songplayer.parts

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.android.exoplayer2.ExoPlayer

@Composable
fun MiniPlayer(
    player: ExoPlayer,
    albumArtUrl: String,
    artistName: String,
    songTitle: String,
    onTogglePlay: () -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit
) {
    val scrollState = rememberScrollState()
    var shouldAnimate by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = shouldAnimate) {
        scrollState.animateScrollTo(
            scrollState.maxValue,
            animationSpec = tween(10000, 200, easing = CubicBezierEasing(0f, 0f, 0f, 0f))
        )
        scrollState.scrollTo(0)
        shouldAnimate = !shouldAnimate
    }
    Surface(
        color = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Box(modifier = Modifier.padding(8.dp), contentAlignment = Alignment.Center) {
            Row {
                AsyncImage(
                    model = albumArtUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = songTitle,
                        style = MaterialTheme.typography.body2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.horizontalScroll(scrollState)
                    )
                    Text(text = artistName, style = MaterialTheme.typography.caption)
                }
                Spacer(modifier = Modifier.width(8.dp))
                ExoPlayerControls(player, onTogglePlay, onNext, onPrev, Modifier.padding(end = 8.dp))
            }
        }
    }
}
