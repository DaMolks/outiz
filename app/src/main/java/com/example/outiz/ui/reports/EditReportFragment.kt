package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.outiz.databinding.FragmentEditReportBinding
import com.google.android.material.tabs.TabLayoutMediator

class EditReportFragment : Fragment() {
    private var _binding: FragmentEditReportBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReportViewModel by activityViewModels()

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
        setupViewPager()
        setupListeners()
    }

    private fun setupViewPager() {
        val pagerAdapter = EditReportPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Général"
                1 -> "Temps"
                2 -> "Photos"
                else -> ""
            }
        }.attach()

        binding.tabLayout.getTabAt(0)?.view?.isClickable = true
        binding.tabLayout.getTabAt(1)?.view?.isClickable = viewModel.hasTimeTracking
        binding.tabLayout.getTabAt(2)?.view?.isClickable = viewModel.hasPhotos
    }

    private fun setupListeners() {
        binding.saveButton.setOnClickListener {
            // TODO: Save report
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}