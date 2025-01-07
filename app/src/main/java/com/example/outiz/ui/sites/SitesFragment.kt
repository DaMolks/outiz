package com.example.outiz.ui.sites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.outiz.R
import com.example.outiz.ui.base.NavigationFragment
import com.example.outiz.databinding.FragmentSitesBinding
import com.example.outiz.ui.adapter.SitesAdapter
import com.example.outiz.ui.viewmodel.SiteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SitesFragment : NavigationFragment(R.layout.fragment_sites) {
    private lateinit var binding: FragmentSitesBinding
    private val viewModel: SiteViewModel by viewModels()
    private lateinit var adapter: SitesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSitesBinding.bind(view)
        setupRecyclerView()
        observeSites()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = SitesAdapter { site ->
            val action = SitesFragmentDirections.actionSitesFragmentToEditSiteFragment(site.id)
            navigate(action)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun observeSites() {
        viewModel.allSites.observe(viewLifecycleOwner) { sites ->
            adapter.submitList(sites)
            binding.emptyView.visibility = if (sites.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupListeners() {
        binding.fabAddSite.setOnClickListener {
            val action = SitesFragmentDirections.actionSitesFragmentToEditSiteFragment(-1L)
            navigate(action)
        }
    }
}