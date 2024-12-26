package com.example.outiz.ui.sites

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.R
import com.example.outiz.databinding.FragmentSitesBinding
import com.example.outiz.models.Site
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class SitesFragment : Fragment() {

    private var _binding: FragmentSitesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SitesViewModel by viewModels()

    private lateinit var sitesAdapter: SitesAdapter

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
        setupObservers()
        setupMenu()
        setupListeners()
    }

    private fun setupRecyclerView() {
        sitesAdapter = SitesAdapter(
            onSiteClick = { site -> navigateToReports(site) },
            onSiteEdit = { site -> navigateToEdit(site) },
            onSiteDelete = { site -> showDeleteConfirmation(site) }
        )

        binding.sitesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sitesAdapter
        }
    }

    private fun setupObservers() {
        viewModel.sites.observe(viewLifecycleOwner) { sites ->
            sitesAdapter.submitList(sites)
            binding.emptyView.visibility = if (sites.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_sites, menu)

                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let { viewModel.searchSites(it) }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { viewModel.searchSites(it) }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupListeners() {
        binding.addSiteFab.setOnClickListener {
            navigateToEdit(null)
        }
    }

    private fun navigateToReports(site: Site) {
        val action = SitesFragmentDirections.actionSitesFragmentToReportsFragment(site.id)
        findNavController().navigate(action)
    }

    private fun navigateToEdit(site: Site?) {
        val action = SitesFragmentDirections.actionSitesFragmentToEditSiteFragment(site?.id)
        findNavController().navigate(action)
    }

    private fun showDeleteConfirmation(site: Site) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_site)
            .setMessage(getString(R.string.delete_site_confirmation, site.name))
            .setPositiveButton(R.string.delete) { _, _ -> viewModel.deleteSite(site) }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}