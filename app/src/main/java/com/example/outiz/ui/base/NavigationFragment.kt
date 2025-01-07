package com.example.outiz.ui.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

open class NavigationFragment : Fragment() {
    protected fun navigate(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    protected fun navigateUp() {
        findNavController().navigateUp()
    }
}