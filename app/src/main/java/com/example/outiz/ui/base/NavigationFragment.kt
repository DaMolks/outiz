package com.example.outiz.ui.base

import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

open class NavigationFragment(@LayoutRes layoutId: Int) : BaseFragment(layoutId) {
    
    protected fun navigate(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    protected fun navigateUp() {
        findNavController().navigateUp()
    }

    protected fun setupBackNavigation(onBackPressed: () -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onBackPressed()
        }
    }
}