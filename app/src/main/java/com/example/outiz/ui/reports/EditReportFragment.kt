package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.outiz.databinding.FragmentEditReportBinding
import com.example.outiz.ui.adapter.ReportPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditReportFragment : Fragment() {
    private var _binding: FragmentEditReportBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: EditReportViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditReportBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load report if editing
        arguments?.getLong(ARG_REPORT_ID, -1L)?.let { reportId ->
            if (reportId != -1L) {
                viewModel.loadReport(reportId)
            }
        }
        
        setupPager()
        setupListeners()
        observeViewModel()
    }
    
    private fun setupPager() {
        viewModel.currentReport.observe(viewLifecycleOwner) { report ->
            val pagerAdapter = ReportPagerAdapter(
                requireActivity(),
                arguments?.getLong(ARG_REPORT_ID, -1L) ?: -1L,
                viewModel.hasTimeTracking.value ?: true,
                viewModel.hasPhotos.value ?: true
            )
            
            binding.viewPager.adapter = pagerAdapter
            
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Informations"
                    1 -> "Temps"
                    2 -> "Photos"
                    else -> ""
                }
            }.attach()
        }
    }
    
    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            viewModel.saveReport { success ->
                if (success) {
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.hasTimeTracking.observe(viewLifecycleOwner) { hasTracking ->
            setupPager()
        }

        viewModel.hasPhotos.observe(viewLifecycleOwner) { hasPhotos ->
            setupPager()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        const val ARG_REPORT_ID = "report_id"
    }
}