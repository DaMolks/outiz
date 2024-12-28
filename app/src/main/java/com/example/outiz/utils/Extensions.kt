package com.example.outiz.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.asLiveData(): LiveData<T> {
    // Implémentation de conversion Flow vers LiveData
    return androidx.lifecycle.asLiveData()
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer { value ->
        value?.let(observer)
    })
}