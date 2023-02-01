package el.nuru.soundofmusic.presentation.songslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import el.nuru.soundofmusic.presentation.songslist.screen.SongsScreen
import el.nuru.soundofmusic.presentation.songslist.ui.theme.SoundOfMusicTheme

@AndroidEntryPoint
class SongsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoundOfMusicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SongsScreen(onNavigateBack = {
                        onBackPressedDispatcher.onBackPressed()
                    })
                }
            }
        }
    }

    companion object {
        const val EXTRA_ARTIST_NAME = "ARTIST_NAME"
        const val EXTRA_ARTIST_PERMALINK = "ARTIST_PERMALINK"
        const val EXTRA_ARTIST_ID = "ARTIST_ID"
    }
}
