package com.example.myapplication.network


sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val value: T?) : NetworkResult<T>()
    data class Error(val cause: Exception? = null, val msg: String? = null) : NetworkResult<Nothing>()
}

suspend inline fun <T : Any> apiCall(
    notNull: Boolean = true,
    call: suspend () -> T?
): NetworkResult<T> {
    return try {
        val networkResult = call.invoke()
        if (notNull) {
            requireNotNull(networkResult)
        }
        NetworkResult.Success(networkResult)
    } catch (e: Exception) {
        NetworkResult.Error(e)
    }
}