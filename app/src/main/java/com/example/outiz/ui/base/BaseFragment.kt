package com.example.outiz.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.outiz.utils.ErrorHandler
import com.example.outiz.utils.ViewExt.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    protected lateinit var errorHandler: ErrorHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorHandler = ErrorHandler(view)

        getViewModel()?.let { viewModel ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isLoading.collect { isLoading ->
                        handleLoading(isLoading)
                    }
                }
            }

            viewModel.errorEvent.observe(viewLifecycleOwner) { error ->
                error?.let {
                    handleError(it)
                    viewModel.clearError()
                }
            }
        }
    }

    protected open fun getViewModel(): BaseViewModel? = null

    protected open fun handleLoading(isLoading: Boolean) {}

    protected open fun handleError(error: String) {
        view?.showSnackbar(error)
    }
}