package el.nuru.soundofmusic.data.datasources

sealed class NetworkResult<out R> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
}
