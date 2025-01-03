package com.example.outiz.ui.sites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.R
import com.example.outiz.databinding.FragmentSitesBinding

class SitesFragment : Fragment() {
    private var _binding: FragmentSitesBinding? = null
    private val binding get() = _binding!!
    private lateinit var sitesAdapter: SitesAdapter
    private val viewModel: SitesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSitesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        observeSites()
    }

    private fun setupRecyclerView() {
        sitesAdapter = SitesAdapter(
            onEditClick = { site ->
                findNavController().navigate(
                    R.id.action_sitesFragment_to_editSiteFragment,
                    Bundle().apply {
                        putString("siteId", site.id)
                    }
                )
            },
            onDeleteClick = { site ->
                viewModel.deleteSite(site)
            }
        )

        binding.recyclerViewSites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sitesAdapter
        }
    }

    private fun setupListeners() {
        binding.fabAddSite.setOnClickListener {
            findNavController().navigate(R.id.action_sitesFragment_to_editSiteFragment)
        }
    }

    private fun observeSites() {
        viewModel.sites.observe(viewLifecycleOwner) { sites ->
            sitesAdapter.submitList(sites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}