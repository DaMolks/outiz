package com.example.outiz.ui.reports.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.outiz.databinding.FragmentReportPhotosBinding
import com.example.outiz.ui.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportPhotosFragment : Fragment() {
    private var _binding: FragmentReportPhotosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels()
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()

        val reportId = arguments?.getLong(ARG_REPORT_ID) ?: -1L
        if (reportId != -1L) {
            viewModel.loadReport(reportId)
        }
    }

    private fun setupRecyclerView() {
        photoAdapter = PhotoAdapter { photoPath ->
            viewModel.removePhoto(photoPath)
        }
        binding.rvPhotos.adapter = photoAdapter
    }

    private fun setupObservers() {
        viewModel.photosPaths.observe(viewLifecycleOwner) { paths ->
            photoAdapter.submitList(paths)
        }
    }

    private fun setupListeners() {
        binding.btnTakePhoto.setOnClickListener {
            viewModel.addPhoto("path/to/photo")
        }

        binding.btnChoosePhoto.setOnClickListener {
            viewModel.addPhoto("path/to/selected/photo")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_REPORT_ID = "report_id"

        fun newInstance(reportId: Long) = ReportPhotosFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_REPORT_ID, reportId)
            }
        }
    }
}