package com.example.outiz.ui.reports

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.outiz.ui.reports.tabs.ReportInfoFragment
import com.example.outiz.ui.reports.tabs.ReportPhotosFragment
import com.example.outiz.ui.reports.tabs.ReportTimeFragment

class ReportPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ReportInfoFragment()
            1 -> ReportTimeFragment()
            2 -> ReportPhotosFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}