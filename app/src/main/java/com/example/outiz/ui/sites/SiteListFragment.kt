package com.example.outiz.ui.sites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentSiteListBinding

class SiteListFragment : Fragment() {
    private var _binding: FragmentSiteListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SiteListViewModel by viewModels()

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
        
        binding.recyclerViewSites.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SiteListAdapter()
        }

        binding.fabAddSite.setOnClickListener {
            // Navigation vers le fragment d'ajout de site
        }

        viewModel.sites.observe(viewLifecycleOwner) { sites ->
            (binding.recyclerViewSites.adapter as SiteListAdapter).submitList(sites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}