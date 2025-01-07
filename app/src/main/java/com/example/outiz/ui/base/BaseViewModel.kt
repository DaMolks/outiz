package com.example.outiz.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.outiz.utils.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorEvent = MutableLiveData<String?>()
    val errorEvent: LiveData<String?> = _errorEvent

    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    protected fun showError(message: String) {
        _errorEvent.value = message
    }

    protected fun clearError() {
        _errorEvent.value = null
    }

    protected suspend fun <T> safeCall(
        loadingEnabled: Boolean = true,
        block: suspend () -> T
    ): State<T> {
        return try {
            if (loadingEnabled) setLoading(true)
            val result = block()
            State.Success(result)
        } catch (e: Exception) {
            State.Error(e.message ?: "An unexpected error occurred")
        } finally {
            if (loadingEnabled) setLoading(false)
        }
    }
}