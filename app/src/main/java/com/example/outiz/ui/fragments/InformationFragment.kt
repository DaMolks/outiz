package com.example.outiz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.outiz.databinding.FragmentInformationBinding

class InformationFragment : Fragment() {
    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!
    private var reportId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reportId = arguments?.getLong(REPORT_ID_KEY) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REPORT_ID_KEY = "reportId"

        fun newInstance(reportId: Long) = InformationFragment().apply {
            arguments = Bundle().apply {
                putLong(REPORT_ID_KEY, reportId)
            }
        }
    }
}