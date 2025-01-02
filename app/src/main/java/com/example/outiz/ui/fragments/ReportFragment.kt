package com.example.outiz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.outiz.databinding.FragmentReportBinding
import com.example.outiz.ui.adapter.ReportPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private var reportId: Long = 0L
    private var hasTimeTracking: Boolean = true
    private var hasPhotos: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reportId = arguments?.getLong(REPORT_ID_KEY) ?: 0L
        hasTimeTracking = arguments?.getBoolean(TIME_TRACKING_KEY, true) ?: true
        hasPhotos = arguments?.getBoolean(PHOTOS_KEY, true) ?: true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter = ReportPagerAdapter(
            requireActivity(), 
            reportId, 
            hasTimeTracking, 
            hasPhotos
        )
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Informations"
                1 -> if (hasTimeTracking) "Suivi du temps" else "Photos"
                2 -> if (hasTimeTracking && hasPhotos) "Photos" else null
                else -> null
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REPORT_ID_KEY = "reportId"
        private const val TIME_TRACKING_KEY = "hasTimeTracking"
        private const val PHOTOS_KEY = "hasPhotos"

        fun newInstance(
            reportId: Long, 
            hasTimeTracking: Boolean = true, 
            hasPhotos: Boolean = true
        ) = ReportFragment().apply {
            arguments = Bundle().apply {
                putLong(REPORT_ID_KEY, reportId)
                putBoolean(TIME_TRACKING_KEY, hasTimeTracking)
                putBoolean(PHOTOS_KEY, hasPhotos)
            }
        }
    }
}