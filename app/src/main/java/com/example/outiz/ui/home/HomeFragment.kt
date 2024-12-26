package com.example.outiz.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.outiz.R
import com.example.outiz.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        binding.modulesRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setupObservers() {
        viewModel.modules.observe(viewLifecycleOwner) { modules ->
            binding.modulesRecyclerView.adapter = ModulesAdapter(modules) { module ->
                viewModel.onModuleClicked(module)
            }
        }

        viewModel.navigateToModule.observe(viewLifecycleOwner) { moduleId ->
            moduleId?.let {
                when (it) {
                    "reports" -> findNavController().navigate(R.id.action_homeFragment_to_reportsFragment)
                    "sites" -> findNavController().navigate(R.id.action_homeFragment_to_sitesFragment)
                }
                viewModel.onNavigationHandled()
            }
        }
    }

    private fun setupListeners() {
        binding.settingsFab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}