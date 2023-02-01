package el.nuru.soundofmusic.presentation.topartistslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import el.nuru.soundofmusic.databinding.ActivityMainBinding
import el.nuru.soundofmusic.presentation.songslist.SongsActivity
import el.nuru.soundofmusic.presentation.songslist.SongsActivity.Companion.EXTRA_ARTIST_ID
import el.nuru.soundofmusic.presentation.songslist.SongsActivity.Companion.EXTRA_ARTIST_NAME
import el.nuru.soundofmusic.presentation.songslist.SongsActivity.Companion.EXTRA_ARTIST_PERMALINK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopArtistsActivity : AppCompatActivity() {
    private val topArtistsViewModel: TopArtistsViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val topArtistAdapter = TopArtistsAdapter { artist ->
            artist?.let {
                val songsActivityIntent = Intent(this, SongsActivity::class.java)
                songsActivityIntent.apply {
                    putExtra(EXTRA_ARTIST_NAME, it.username)
                    putExtra(EXTRA_ARTIST_PERMALINK, it.permalink)
                    putExtra(EXTRA_ARTIST_ID, it.id)
                }
                startActivity(songsActivityIntent)
            }
        }
        binding.recyclerViewArtists.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewArtists.adapter = topArtistAdapter
        topArtistsViewModel.uiState.observeIn(this) {
            binding.swiperefresh.isRefreshing = false
            if (it.isLoading) {
                // show loading indicator
            } else if (!it.errorMessage.isNullOrEmpty()) {
                // show error message
                topArtistsViewModel.clearErrorMessage()
            } else {
                topArtistAdapter.submitList(it.artists)
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                topArtistsViewModel.search(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                topArtistsViewModel.search(newText)
                return false
            }
        })

        binding.swiperefresh.setOnRefreshListener {
            topArtistsViewModel.loadTopArtists(true)
        }
    }
}

// Extension function to make flow observation in UI simpler
inline fun <T> Flow<T>.observeIn(viewLifecycleOwner: LifecycleOwner, crossinline observer: (value: T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .collectLatest {
                observer(it)
            }
    }
}
