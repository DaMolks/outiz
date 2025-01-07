package com.example.outiz.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.outiz.R
import com.example.outiz.databinding.FragmentHomeBinding
import com.example.outiz.ui.base.NavigationFragment
import com.example.outiz.ui.adapter.ModuleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : NavigationFragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var moduleAdapter: ModuleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setupModuleList()
        observeModules()
    }

    private fun setupModuleList() {
        moduleAdapter = ModuleAdapter { module ->
            when (module.id) {
                R.id.module_reports -> navigate(HomeFragmentDirections.actionHomeFragmentToReportsFragment())
                R.id.module_sites -> navigate(HomeFragmentDirections.actionHomeFragmentToSitesFragment())
                R.id.module_settings -> navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
            }
        }
        binding.recyclerViewModules.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = moduleAdapter
        }
    }

    private fun observeModules() {
        viewModel.modules.observe(viewLifecycleOwner) { modules ->
            moduleAdapter.submitList(modules)
        }
    }
}