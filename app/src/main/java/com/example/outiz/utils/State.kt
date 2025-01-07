package com.example.outiz.utils

sealed class State<out T> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val message: String) : State<Nothing>()
    object Loading : State<Nothing>()
}

fun <T> State<T>.onSuccess(action: (T) -> Unit): State<T> {
    if (this is State.Success) {
        action(data)
    }
    return this
}

fun <T> State<T>.onError(action: (String) -> Unit): State<T> {
    if (this is State.Error) {
        action(message)
    }
    return this
}

fun <T> State<T>.onLoading(action: () -> Unit): State<T> {
    if (this is State.Loading) {
        action()
    }
    return this
}