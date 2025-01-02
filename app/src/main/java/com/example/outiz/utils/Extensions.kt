package com.example.outiz.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.toLiveData(): LiveData<T> = this.asLiveData()