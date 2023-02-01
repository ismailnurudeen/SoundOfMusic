package el.nuru.soundofmusic.domain.utils

sealed class Resource<out R>() {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}
