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
        binding.viewPager.isUserInputEnabled = true

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Général"
                1 -> "Temps"
                2 -> "Photos"
                else -> ""
            }
        }.attach()

        // Activer/désactiver les onglets en fonction des options
        binding.tabLayout.getTabAt(0)?.view?.isEnabled = true
        binding.tabLayout.getTabAt(1)?.view?.isEnabled = viewModel.hasTimeTracking
        binding.tabLayout.getTabAt(2)?.view?.isEnabled = viewModel.hasPhotos

        // Masquer les onglets désactivés
        binding.tabLayout.getTabAt(1)?.view?.visibility = if (viewModel.hasTimeTracking) View.VISIBLE else View.GONE
        binding.tabLayout.getTabAt(2)?.view?.visibility = if (viewModel.hasPhotos) View.VISIBLE else View.GONE
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