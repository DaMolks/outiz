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
import com.example.outiz.databinding.FragmentSiteListBinding
import com.example.outiz.models.Site

class SiteListFragment : Fragment() {
    private var _binding: FragmentSiteListBinding? = null
    private val binding get() = _binding!!
    private lateinit var sitesAdapter: SitesAdapter
    private val viewModel: SitesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSiteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Initialisation de l'adaptateur avec les callbacks
        sitesAdapter = SitesAdapter(
            onEditClick = { site ->
                // Navigation vers l'écran d'édition avec l'ID du site
                findNavController().navigate(
                    R.id.action_sitesFragment_to_editSiteFragment,
                    Bundle().apply {
                        putString("siteId", site.id)
                    }
                )
            },
            onDeleteClick = { site ->
                // Suppression du site via le ViewModel
                viewModel.deleteSite(site)
            }
        )

        binding.recyclerViewSites.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sitesAdapter
        }

        viewModel.sites.observe(viewLifecycleOwner) { sites ->
            sitesAdapter.submitList(sites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}