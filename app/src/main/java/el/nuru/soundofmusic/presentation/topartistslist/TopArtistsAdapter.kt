package el.nuru.soundofmusic.presentation.topartistslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import el.nuru.soundofmusic.databinding.TopArtistItemBinding
import el.nuru.soundofmusic.presentation.models.Artist

class TopArtistsAdapter(private val onClick: (Artist?) -> Unit): ListAdapter<Artist, TopArtistsAdapter.ViewHolder>(TopArtistsAdapter.ArtistDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TopArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        binding.root.setOnClickListener{
            onClick(getItem(holder.bindingAdapterPosition))
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: TopArtistItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(artist: Artist) = with(binding){
            textViewArtistName.text = artist.username
            textViewArtistDescription.text = artist.caption
            Glide.with(imageViewArtistThumbnail.context)
                .load(artist.avatar_url)
                .into(imageViewArtistThumbnail)
        }
    }

    class ArtistDiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.id == newItem.id &&
                oldItem.avatar_url == newItem.avatar_url &&
                oldItem.permalink == newItem.permalink &&
                oldItem.username == newItem.username &&
                oldItem.uri == newItem.uri &&
                oldItem.caption == newItem.caption
        }
    }
}
