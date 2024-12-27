package com.example.outiz.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.outiz.ui.fragments.InformationFragment
import com.example.outiz.ui.fragments.PhotosFragment
import com.example.outiz.ui.fragments.TimeTrackingFragment

class ReportPagerAdapter(fragment: Fragment, private val reportId: String) : 
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InformationFragment.newInstance(reportId)
            1 -> TimeTrackingFragment.newInstance(reportId)
            2 -> PhotosFragment.newInstance(reportId)
            else -> throw IllegalStateException("Position invalide $position")
        }
    }
}