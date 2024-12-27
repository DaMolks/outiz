package com.example.outiz.ui.sites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.R
import com.example.outiz.databinding.FragmentSitesBinding
import com.example.outiz.ui.adapter.SitesAdapter

class SitesFragment : Fragment() {
    private var _binding: FragmentSitesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SitesViewModel
    private lateinit var adapter: SitesAdapter

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
        viewModel = ViewModelProvider(this)[SitesViewModel::class.java]

        setupRecyclerView()
        setupAddSiteButton()
        observeSites()
    }

    private fun setupRecyclerView() {
        adapter = SitesAdapter(
            onSiteClick = { site -> navigateToReports(site.id) },
            onSiteEdit = { site -> navigateToEditSite(site.id) },
            onSiteDelete = { site -> deleteSite(site) }
        )
        binding.sitesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@SitesFragment.adapter
        }
    }

    private fun navigateToEditSite(siteId: String?) {
        val args = Bundle().apply { putString("siteId", siteId) }
        findNavController().navigate(R.id.editSiteFragment, args)
    }

    private fun navigateToReports(siteId: String?) {
        val args = Bundle().apply { putString("siteId", siteId) }
        findNavController().navigate(R.id.reportsFragment, args)
    }

    private fun deleteSite(site: com.example.outiz.models.Site) {
        viewModel.deleteSite(site)
    }

    private fun setupAddSiteButton() {
        binding.root.findViewById<View>(R.id.addSiteButton).setOnClickListener {
            findNavController().navigate(R.id.editSiteFragment)
        }
    }

    private fun observeSites() {
        viewModel.sites.observe(viewLifecycleOwner) { sites ->
            adapter.submitList(sites)
            binding.root.findViewById<View>(R.id.emptySitesText).visibility = if (sites.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}