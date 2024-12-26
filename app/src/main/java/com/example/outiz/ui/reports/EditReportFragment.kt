package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.outiz.R
import com.example.outiz.databinding.FragmentEditReportBinding
import com.example.outiz.ui.reports.tabs.ReportInfoFragment
import com.example.outiz.ui.reports.tabs.ReportPhotosFragment
import com.example.outiz.ui.reports.tabs.ReportTimeFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

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

        arguments?.getString("reportId")?.let { reportId ->
            viewModel.loadReport(reportId)
        }

        setupViewPager()
        setupObservers()
        setupListeners()
    }

    private fun setupViewPager() {
        val adapter = ReportPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.info)
                1 -> getString(R.string.time)
                2 -> getString(R.string.photos)
                else -> null
            }
        }.attach()
    }

    private fun setupObservers() {
        viewModel.saved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                findNavController().popBackStack()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    private fun setupListeners() {
        binding.saveReportFab.setOnClickListener {
            collectDataAndSave()
        }
    }

    private fun collectDataAndSave() {
        val infoFragment = childFragmentManager.fragments.filterIsInstance<ReportInfoFragment>().firstOrNull()
        infoFragment?.let { fragment ->
            fragment.validateAndCollectData()?.let { data ->
                viewModel.saveReport(
                    siteId = data.siteId,
                    callDate = data.callDate,
                    callReason = data.callReason,
                    caller = data.caller,
                    isTimeTrackingEnabled = data.isTimeTrackingEnabled,
                    isPhotosEnabled = data.isPhotosEnabled
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}