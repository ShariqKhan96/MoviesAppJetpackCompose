package com.example.movies.core.resource

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null,
    val isLoading: Boolean? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(error: String, data: T? = null) : Resource<T>(data, error)
    class Loading<T>(data: T? = null, isLoading: Boolean?) : Resource<T>(data, null, isLoading)
}