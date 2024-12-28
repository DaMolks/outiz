package com.example.outiz.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseViewModel : ViewModel() {
    protected fun <T> Flow<T>.mapToList(): Flow<List<T>> = map { listOf(it) }
}