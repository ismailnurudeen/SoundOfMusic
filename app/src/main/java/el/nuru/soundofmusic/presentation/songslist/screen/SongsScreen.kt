package el.nuru.soundofmusic.presentation.songslist.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import el.nuru.soundofmusic.presentation.models.Song
import el.nuru.soundofmusic.presentation.songplayer.SongPlayer
import el.nuru.soundofmusic.presentation.utils.formatTime
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongsScreen(onNavigateBack: () -> Unit = {}, songsViewModel: SongsViewModel = viewModel()) {
    val uiState by songsViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        songsViewModel.loadSongs()
    }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Songs by ${uiState.currentlyArtistName}",
                        style = MaterialTheme.typography.h5,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "navigate back",
                        modifier = Modifier
                            .clickable {
                                onNavigateBack()
                            }
                            .padding(start = 16.dp)
                    )
                }
            )
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation = 16.dp,
        sheetShape = RoundedCornerShape(
            bottomStart = 0.dp,
            bottomEnd = 0.dp,
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        sheetContent = {
            val currentSong = uiState.currentlySong
            SongPlayer(
                songTitle = currentSong?.title ?: "No song playing",
                artistName = uiState.currentlyArtistName,
                albumArtUrl = currentSong?.artwork_url ?: "",
                streamUrl = currentSong?.stream_url ?: "",
                isExpanded = bottomSheetScaffoldState.bottomSheetState.isExpanded,
                onTogglePlay = {
                    songsViewModel.toggleSong()
                },
                onPrev = songsViewModel::previousSong,
                onNext = songsViewModel::nextSong,
                onClick = {
                    scope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                },
                songStarted = {
                    songsViewModel.songStarted(it)
                }
            )
        },
        // This is the height in collapsed state
        sheetPeekHeight = 75.dp
    ) {
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = uiState.isRefreshing)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SwipeRefresh(state = swipeRefreshState, onRefresh = {
                songsViewModel.loadSongs(true)
            }) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        end = 8.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(
                        items = uiState.songs,
                        key = { _, song -> song.id }
                    ) { index, song ->
                        SongItem(
                            song = song,
                            isSelected = uiState.currentlySong?.id == song.id,
                            isPlaying = uiState.songStarted,
                            isPaused = uiState.songPaused,
                            onClick = {
                                songsViewModel.playSong(song, index)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongItem(song: Song, isSelected: Boolean = false, isPlaying: Boolean = false, isPaused: Boolean, onClick: (song: Song) -> Unit) {
    Card(
        onClick = { onClick(song) },
        shape = RoundedCornerShape(50),
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = song.artwork_url,
                contentDescription = "",
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = song.title, maxLines = 2)
                Text(text = formatTime((song.duration.toLongOrNull() ?: 0L) * 1000L))
            }
            AnimatedVisibility(isSelected && !isPaused && !isPlaying) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
