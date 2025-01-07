package com.example.outiz.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.outiz.R
import com.example.outiz.databinding.FragmentSplashBinding
import com.example.outiz.ui.base.NavigationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : NavigationFragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        viewModel.isTechnicianProfileCreated.observe(viewLifecycleOwner) { isCreated ->
            if (isCreated) {
                navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            } else {
                navigate(SplashFragmentDirections.actionSplashFragmentToTechnicianProfileFragment())
            }
        }
    }
}