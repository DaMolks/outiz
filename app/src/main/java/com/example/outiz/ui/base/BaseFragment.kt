package com.example.outiz.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {
    protected fun navigateUp() {
        findNavController().navigateUp()
    }

    protected fun navigateTo(actionId: Int, args: Bundle? = null) {
        findNavController().navigate(actionId, args)
    }
}