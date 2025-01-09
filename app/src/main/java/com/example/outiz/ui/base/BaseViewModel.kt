package com.example.outiz.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.outiz.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

abstract class BaseViewModel : ViewModel() {

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    protected fun setError(message: String?) {
        _error.value = message
    }

    protected fun clearError() {
        _error.value = null
    }

    protected fun <T> Flow<T>.asUiState(): Flow<UiState<T>> {
        return this
            .map<T, UiState<T>> { UiState.Success(it) }
            .onStart { emit(UiState.Loading) }
            .catch { emit(UiState.Error(it)) }
    }
}