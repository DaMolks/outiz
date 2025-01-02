package com.example.outiz.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.outiz.ui.reports.tabs.ReportInfoFragment
import com.example.outiz.ui.reports.tabs.ReportPhotosFragment
import com.example.outiz.ui.reports.tabs.ReportTimeFragment

class ReportPagerAdapter(
    activity: FragmentActivity,
    private val reportId: Long,
    private val hasTimeTracking: Boolean = true,
    private val hasPhotos: Boolean = true
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        var count = 1 // Info tab is always present
        if (hasTimeTracking) count++
        if (hasPhotos) count++
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ReportInfoFragment.newInstance(reportId)
            1 -> if (hasTimeTracking) ReportTimeFragment.newInstance(reportId)
                else if (hasPhotos) ReportPhotosFragment.newInstance(reportId)
                else throw IllegalStateException("Invalid position $position")
            2 -> if (hasPhotos && hasTimeTracking) ReportPhotosFragment.newInstance(reportId)
                else throw IllegalStateException("Invalid position $position")
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}